package atraintegratedsystems.licenses.service;


import atraintegratedsystems.licenses.dto.LicenseApplicantApprovalDTO;
import atraintegratedsystems.licenses.dto.LicenseApplicantDTO;
import atraintegratedsystems.licenses.model.LicenseApplicant;
import atraintegratedsystems.licenses.model.LicenseType;
import atraintegratedsystems.licenses.repository.LicenseApplicantRepository;
import atraintegratedsystems.licenses.repository.LicenseTypeRepository;
import atraintegratedsystems.utils.DateConverter;
import atraintegratedsystems.utils.PersianCalendarUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class LicenseNewApplicantService {
    @Autowired
    private LicenseApplicantRepository repository;
    @Autowired
    private LicenseTypeRepository licenseTypeRepository;
    private static final List<String> VALID_FILE_TYPES = Arrays.asList("application/pdf", "image/jpeg", "image/png");

    @Value("${lms.attachments.path}")
    private String attachmentsFolder;



    // Utility to save file and return path
    private String saveFile(MultipartFile file, String subfolder) throws IOException {
        if (file == null || file.isEmpty()) return null;

        Path dirPath = Paths.get(attachmentsFolder, subfolder);
        Files.createDirectories(dirPath);

        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = dirPath.resolve(filename);

        file.transferTo(filePath.toFile());
        return filePath.toString(); // return saved path
    }

    @Transactional
    public List<LicenseApplicant> getAllApplicants() {
      return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<LicenseApplicant> getApplicantById(Long id) {
        return repository.findById(id);
    }

    public List<LicenseApplicantApprovalDTO> getAllLicenseApplicantApprovalDetails() {
        return repository.findAllLicenseApplicantApprovalDetails();
    }

    @Transactional
    public LicenseApplicant saveProfile(LicenseApplicantDTO dto) throws Exception {

        LicenseApplicant profile = new LicenseApplicant();

        profile.setReqId(generateRequestId());

        // =====================================================
        // Convert Jalali Request Date to Gregorian
        // =====================================================

        if (dto.getReqDateJalali() != null && !dto.getReqDateJalali().isEmpty()) {

            String[] parts = dto.getReqDateJalali().split("-");

            int jYear = Integer.parseInt(parts[0]);
            int jMonth = Integer.parseInt(parts[1]);
            int jDay = Integer.parseInt(parts[2]);

            PersianCalendarUtils converter = new PersianCalendarUtils();

            LocalDate reqDate = converter.jalaliToGregorian(jYear, jMonth, jDay);

            profile.setReqDate(reqDate);
        }

        // =====================================================
        // License Type
        // =====================================================

        if (dto.getLicenseTypeId() != null) {

            LicenseType licenseType = licenseTypeRepository
                    .findById(dto.getLicenseTypeId())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Invalid license type ID: " + dto.getLicenseTypeId()));

            profile.setLicenseType(licenseType);

        } else {
            throw new IllegalArgumentException("License type is required");
        }

        // =====================================================
        // Basic Fields
        // =====================================================

        profile.setCurrencyType(dto.getCurrencyType());
        profile.setFinanceType(dto.getFinanceType());
        profile.setCompanyLicenseName(dto.getCompanyLicenseName());
        profile.setApplicationFees(dto.getApplicationFees());
        profile.setValidity(dto.getValidity());

        // =====================================================
        // Application Upload (Save as File Path)
        // =====================================================

        MultipartFile applicationUpload = dto.getApplicationUpload();

        if (applicationUpload != null && !applicationUpload.isEmpty()) {

            String contentType = applicationUpload.getContentType();

            if (!VALID_FILE_TYPES.contains(contentType)) {
                throw new IllegalArgumentException("Invalid File Type: " + contentType);
            }

            String applicationPath = saveFile(applicationUpload, "application");

            profile.setApplicationUpload(applicationPath);
        }

        // =====================================================
        // Logged In User
        // =====================================================

        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof UserDetails) {

            String username = ((UserDetails) principal).getUsername();

            profile.setProfileEnteredBy(username);

        } else {

            profile.setProfileEnteredBy(principal.toString());
        }

        profile.setProfileEnteredCreatedDate(LocalDateTime.now());

        // =====================================================
        // Save
        // =====================================================

        return repository.save(profile);
    }


    private String generateRequestId() {
        Long maxId = repository.findMaxId();
        String prefix = "ATRA-REQ-";
        if (maxId == null) {
            return prefix + "0001";
        }
        Long nextId = maxId + 1;
        return prefix + String.format("%04d", nextId);
    }


    private void validateFile(MultipartFile file) {

        String contentType = file.getContentType();

        if (!VALID_FILE_TYPES.contains(contentType)) {
            throw new IllegalArgumentException("Invalid File Type: " + contentType);
        }
    }

    @Transactional
    public LicenseApplicant updateCompleteProfile(Long id, LicenseApplicantDTO dto) throws IOException {

        LicenseApplicant profile = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found with ID: " + id));

        profile.setLicenseNo(dto.getLicenseNo());

        // =====================================================
        // ENID Upload
        // =====================================================

        MultipartFile enidUpload = dto.getEnidUpload();
        if (enidUpload != null && !enidUpload.isEmpty()) {

            validateFile(enidUpload);

            String filePath = saveFile(enidUpload, "enid");

            profile.setEnidUpload(filePath);
        }

        // =====================================================
        // Article Of Association Upload
        // =====================================================

        MultipartFile articleUpload = dto.getArticleOfAssociationUpload();
        if (articleUpload != null && !articleUpload.isEmpty()) {

            validateFile(articleUpload);

            String filePath = saveFile(articleUpload, "article");

            profile.setArticleOfAssociationUpload(filePath);
        }

        // =====================================================
        // Business Plan Upload
        // =====================================================

        MultipartFile businessUpload = dto.getBusinessPlanUpload();
        if (businessUpload != null && !businessUpload.isEmpty()) {

            validateFile(businessUpload);

            String filePath = saveFile(businessUpload, "business");

            profile.setBusinessPlanUpload(filePath);
        }

        // =====================================================
        // License Upload
        // =====================================================

        MultipartFile licenseUpload = dto.getLicenseUpload();
        if (licenseUpload != null && !licenseUpload.isEmpty()) {

            validateFile(licenseUpload);

            String filePath = saveFile(licenseUpload, "license");

            profile.setLicenseUpload(filePath);
        }

        profile.setTinNo(dto.getTinNo());

        // =====================================================
        // Identity Form Upload
        // =====================================================

        MultipartFile identityUpload = dto.getIdentityFormUpload();
        if (identityUpload != null && !identityUpload.isEmpty()) {

            validateFile(identityUpload);

            String filePath = saveFile(identityUpload, "identity");

            profile.setIdentityFormUpload(filePath);
        }

        // =====================================================
        // Year Of Establishment (Jalali → Gregorian)
        // =====================================================

        if (dto.getJalaliYearOfEstablishment() != null) {

            String[] parts = dto.getJalaliYearOfEstablishment().split("-");

            int jYear = Integer.parseInt(parts[0]);
            int jMonth = Integer.parseInt(parts[1]);
            int jDay = Integer.parseInt(parts[2]);

            PersianCalendarUtils converter = new PersianCalendarUtils();

            LocalDate establishmentDate = converter.jalaliToGregorian(jYear, jMonth, jDay);

            profile.setYearOfEstablishment(establishmentDate);
        }

        // =====================================================
        // Expiry Date
        // =====================================================

        if (dto.getJalaliExpiryDate() != null) {

            String[] parts = dto.getJalaliExpiryDate().split("-");

            int jYear = Integer.parseInt(parts[0]);
            int jMonth = Integer.parseInt(parts[1]);
            int jDay = Integer.parseInt(parts[2]);

            PersianCalendarUtils converter = new PersianCalendarUtils();

            LocalDate expiryDate = converter.jalaliToGregorian(jYear, jMonth, jDay);

            profile.setExpiryDate(expiryDate);
        }

        // =====================================================
        // Business Information
        // =====================================================

        profile.setPlannedActivitiesAndServices(dto.getPlannedActivitiesAndServices());
        profile.setTotalNationalEmployees(dto.getTotalNationalEmployees());
        profile.setTotalInternationalEmployees(dto.getTotalInternationalEmployees());
        profile.setExpectedInvestment(dto.getExpectedInvestment());
        profile.setCash(dto.getCash());

        // =====================================================
        // Bank Statement Upload
        // =====================================================

        MultipartFile bankUpload = dto.getBankStatementUpload();
        if (bankUpload != null && !bankUpload.isEmpty()) {

            validateFile(bankUpload);

            String filePath = saveFile(bankUpload, "bank");

            profile.setBankStatementUpload(filePath);
        }

        profile.setOtherLicenseTaken(dto.getOtherLicenseTaken());
        profile.setCompanyAddress(dto.getCompanyAddress());
        profile.setContactNo(dto.getContactNo());
        profile.setEmail(dto.getEmail());
        profile.setWebsite(dto.getWebsite());
        profile.setPostAddress(dto.getPostAddress());

        // =====================================================
        // Logged In User
        // =====================================================

        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof UserDetails) {

            String username = ((UserDetails) principal).getUsername();

            profile.setCompleteProfileEnteredBy(username);

        } else {

            profile.setCompleteProfileEnteredBy(principal.toString());
        }

        profile.setCompletedProfileCreatedDate(LocalDateTime.now());

        return repository.save(profile);
    }

}
