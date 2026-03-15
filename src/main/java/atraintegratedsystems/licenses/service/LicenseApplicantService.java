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
public class LicenseApplicantService {
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

        DateConverter dateConverter = new DateConverter();

        // Convert Jalali date to Gregorian
        LocalDate requestDate = dateConverter.jalaliToGregorian(
                dto.getReqDate().getYear(),
                dto.getReqDate().getMonthValue(),
                dto.getReqDate().getDayOfMonth()
        );

        profile.setReqDate(requestDate);

        // License Type
        if (dto.getLicenseTypeId() != null) {
            LicenseType licenseType = licenseTypeRepository.findById(dto.getLicenseTypeId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid license type ID"));
            profile.setLicenseType(licenseType);
        } else {
            throw new IllegalArgumentException("License type is required");
        }

        profile.setCurrencyType(dto.getCurrencyType());
        profile.setFinanceType(dto.getFinanceType());
        profile.setCompanyLicenseName(dto.getCompanyLicenseName());

        // APPLICATION FILE
        String applicationPath = saveFile(dto.getApplicationUpload(), "application");
        profile.setApplicationUpload(applicationPath);

        // ENID FILE
        String enidPath = saveFile(dto.getEnidUpload(), "enid");
        profile.setEnidUpload(enidPath);

        // ARTICLE OF ASSOCIATION
        String articlePath = saveFile(dto.getArticleOfAssociationUpload(), "article");
        profile.setArticleOfAssociationUpload(articlePath);

        // BUSINESS PLAN
        String businessPlanPath = saveFile(dto.getBusinessPlanUpload(), "businessplan");
        profile.setBusinessPlanUpload(businessPlanPath);

        profile.setLicenseNo(dto.getLicenseNo());

        // LICENSE FILE
        String licensePath = saveFile(dto.getLicenseUpload(), "license");
        profile.setLicenseUpload(licensePath);

        profile.setTinNo(dto.getTinNo());

        // IDENTITY FORM
        String identityPath = saveFile(dto.getIdentityFormUpload(), "identity");
        profile.setIdentityFormUpload(identityPath);

        // Bank Statement
        String bankStatementPath = saveFile(dto.getBankStatementUpload(), "bankstatement");
        profile.setBankStatementUpload(bankStatementPath);

        // Proposal
        String proposalPath = saveFile(dto.getProposalUpload(), "proposal");
        profile.setProposalUpload(proposalPath);

        // Other fields
        profile.setApplicationFees(dto.getApplicationFees());
        profile.setValidity(dto.getValidity());
        profile.setPlannedActivitiesAndServices(dto.getPlannedActivitiesAndServices());
        profile.setTotalNationalEmployees(dto.getTotalNationalEmployees());
        profile.setTotalInternationalEmployees(dto.getTotalInternationalEmployees());
        profile.setExpectedInvestment(dto.getExpectedInvestment());
        profile.setCash(dto.getCash());
        profile.setOtherLicenseTaken(dto.getOtherLicenseTaken());
        profile.setCompanyAddress(dto.getCompanyAddress());
        profile.setContactNo(dto.getContactNo());
        profile.setEmail(dto.getEmail());
        profile.setWebsite(dto.getWebsite());
        profile.setPostAddress(dto.getPostAddress());

        profile.setBankVoucher(dto.getBankVoucher());
        profile.setPaymentStatus(dto.getPaymentStatus());

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
    @Transactional
    public LicenseApplicant SendToBoard(Long licenseId, LicenseApplicantDTO dto) throws IOException {

        LicenseApplicant profile = repository.findById(licenseId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found with ID: " + licenseId));

        // Convert Jalali date to Gregorian
        if (dto.getJalaliReferToBoardDate() != null && !dto.getJalaliReferToBoardDate().isEmpty()) {

            String[] parts = dto.getJalaliReferToBoardDate().split("-");
            int jYear = Integer.parseInt(parts[0]);
            int jMonth = Integer.parseInt(parts[1]);
            int jDay = Integer.parseInt(parts[2]);

            PersianCalendarUtils converter = new PersianCalendarUtils();
            LocalDate referToBoardDate = converter.jalaliToGregorian(jYear, jMonth, jDay);

            profile.setReferToBoardDate(referToBoardDate);
        }

        profile.setIsSend(dto.getIsSend());

        // Save proposal file as PATH
        MultipartFile proposalUpload = dto.getProposalUpload();

        if (proposalUpload != null && !proposalUpload.isEmpty()) {

            String contentType = proposalUpload.getContentType();

            if (!VALID_FILE_TYPES.contains(contentType)) {
                throw new IllegalArgumentException("Invalid File Type: " + contentType);
            }

            // Save file to folder and store path
            String proposalPath = saveFile(proposalUpload, "proposal");

            profile.setProposalUpload(proposalPath);
        }

        // Logged-in user
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String enteredBy = (principal instanceof UserDetails)
                ? ((UserDetails) principal).getUsername()
                : "Unknown";

        profile.setSendToBoardEnteredBy(enteredBy);

        // Set created date if null
        if (profile.getSendToBoardCreatedDate() == null) {
            profile.setSendToBoardCreatedDate(LocalDateTime.now());
        }

        return repository.save(profile);
    }


    @Transactional
    public LicenseApplicant updateCompleteProfile(Long id, LicenseApplicantDTO dto) throws IOException {

        LicenseApplicant profile = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found with ID: " + id));

        profile.setLicenseNo(dto.getLicenseNo());

        // ENID Upload
        MultipartFile enidUpload = dto.getEnidUpload();
        if (enidUpload != null && !enidUpload.isEmpty()) {

            String contentType = enidUpload.getContentType();
            if (!VALID_FILE_TYPES.contains(contentType)) {
                throw new IllegalArgumentException("Invalid File Type: " + contentType);
            }

            String enidPath = saveFile(enidUpload, "enid");
            profile.setEnidUpload(enidPath);
        }

        // Article of Association
        MultipartFile articleUpload = dto.getArticleOfAssociationUpload();
        if (articleUpload != null && !articleUpload.isEmpty()) {

            String contentType = articleUpload.getContentType();
            if (!VALID_FILE_TYPES.contains(contentType)) {
                throw new IllegalArgumentException("Invalid File Type: " + contentType);
            }

            String articlePath = saveFile(articleUpload, "article");
            profile.setArticleOfAssociationUpload(articlePath);
        }

        // Business Plan
        MultipartFile businessPlanUpload = dto.getBusinessPlanUpload();
        if (businessPlanUpload != null && !businessPlanUpload.isEmpty()) {

            String contentType = businessPlanUpload.getContentType();
            if (!VALID_FILE_TYPES.contains(contentType)) {
                throw new IllegalArgumentException("Invalid File Type: " + contentType);
            }

            String businessPlanPath = saveFile(businessPlanUpload, "businessplan");
            profile.setBusinessPlanUpload(businessPlanPath);
        }

        // License Upload
        MultipartFile licenseUpload = dto.getLicenseUpload();
        if (licenseUpload != null && !licenseUpload.isEmpty()) {

            String contentType = licenseUpload.getContentType();
            if (!VALID_FILE_TYPES.contains(contentType)) {
                throw new IllegalArgumentException("Invalid File Type: " + contentType);
            }

            String licensePath = saveFile(licenseUpload, "license");
            profile.setLicenseUpload(licensePath);
        }

        profile.setTinNo(dto.getTinNo());

        // Identity Form Upload
        MultipartFile identityUpload = dto.getIdentityFormUpload();
        if (identityUpload != null && !identityUpload.isEmpty()) {

            String contentType = identityUpload.getContentType();
            if (!VALID_FILE_TYPES.contains(contentType)) {
                throw new IllegalArgumentException("Invalid File Type: " + contentType);
            }

            String identityPath = saveFile(identityUpload, "identity");
            profile.setIdentityFormUpload(identityPath);
        }

        // Jalali Year Of Establishment
        if (dto.getJalaliYearOfEstablishment() != null) {

            String[] parts = dto.getJalaliYearOfEstablishment().split("-");

            int jYear = Integer.parseInt(parts[0]);
            int jMonth = Integer.parseInt(parts[1]);
            int jDay = Integer.parseInt(parts[2]);

            PersianCalendarUtils converter = new PersianCalendarUtils();
            LocalDate yearOfEstablishmentDate = converter.jalaliToGregorian(jYear, jMonth, jDay);

            profile.setYearOfEstablishment(yearOfEstablishmentDate);
        }

        // Jalali Expiry Date
        if (dto.getJalaliExpiryDate() != null) {

            String[] subParts = dto.getJalaliExpiryDate().split("-");

            int jYear = Integer.parseInt(subParts[0]);
            int jMonth = Integer.parseInt(subParts[1]);
            int jDay = Integer.parseInt(subParts[2]);

            PersianCalendarUtils converter = new PersianCalendarUtils();
            LocalDate expiryDate = converter.jalaliToGregorian(jYear, jMonth, jDay);

            profile.setExpiryDate(expiryDate);
        }

        profile.setApplicationFees(dto.getApplicationFees());
        profile.setValidity(dto.getValidity());
        profile.setPlannedActivitiesAndServices(dto.getPlannedActivitiesAndServices());
        profile.setTotalNationalEmployees(dto.getTotalNationalEmployees());
        profile.setTotalInternationalEmployees(dto.getTotalInternationalEmployees());
        profile.setExpectedInvestment(dto.getExpectedInvestment());
        profile.setCash(dto.getCash());

        // Bank Statement Upload
        MultipartFile bankStatementUpload = dto.getBankStatementUpload();
        if (bankStatementUpload != null && !bankStatementUpload.isEmpty()) {

            String contentType = bankStatementUpload.getContentType();
            if (!VALID_FILE_TYPES.contains(contentType)) {
                throw new IllegalArgumentException("Invalid File Type: " + contentType);
            }

            String bankStatementPath = saveFile(bankStatementUpload, "bankstatement");
            profile.setBankStatementUpload(bankStatementPath);
        }

        profile.setOtherLicenseTaken(dto.getOtherLicenseTaken());
        profile.setCompanyAddress(dto.getCompanyAddress());
        profile.setContactNo(dto.getContactNo());
        profile.setEmail(dto.getEmail());
        profile.setWebsite(dto.getWebsite());
        profile.setPostAddress(dto.getPostAddress());

        return repository.save(profile);
    }





}
