package atraintegratedsystems.licenses.service;


import atraintegratedsystems.licenses.dto.LicenseApplicantApprovalDTO;
import atraintegratedsystems.licenses.dto.LicenseApplicantDTO;
import atraintegratedsystems.licenses.model.LicenseApplicant;
import atraintegratedsystems.licenses.model.LicenseType;
import atraintegratedsystems.licenses.repository.LicenseApplicantRepository;
import atraintegratedsystems.licenses.repository.LicenseTypeRepository;
import atraintegratedsystems.utils.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDate;
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
        LocalDate requestDate = dateConverter.jalaliToGregorian(dto.getReqDate().getYear(), dto.getReqDate().getMonthValue(), dto.getReqDate().getDayOfMonth());
        profile.setReqDate(requestDate);

        // Set the LicenseType based on the provided licenseTypeId
        if (dto.getLicenseTypeId() != null) {
            LicenseType licenseType = licenseTypeRepository.findById(dto.getLicenseTypeId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid license type ID: " + dto.getLicenseTypeId()));
            profile.setLicenseType(licenseType);
        } else {
            throw new IllegalArgumentException("License type is required");
        }

        profile.setCurrencyType(dto.getCurrencyType());
        profile.setFinanceType(dto.getFinanceType());
        profile.setCompanyLicenseName(dto.getCompanyLicenseName());

        MultipartFile applicationUpload = dto.getApplicationUpload();

        if (applicationUpload != null && !applicationUpload.isEmpty()) {
            String contentType = applicationUpload.getContentType();

            if (!VALID_FILE_TYPES.contains(contentType)) {
                throw new IllegalArgumentException("Invalid File Type: " + contentType);
            }

            profile.setApplicationUpload(applicationUpload.getBytes());
        }


        profile.setLicenseNo(dto.getLicenseNo());

        MultipartFile licenseUpload = dto.getLicenseUpload();

        if (licenseUpload != null && !licenseUpload.isEmpty()) {
            String contentType = licenseUpload.getContentType();

            if (!VALID_FILE_TYPES.contains(contentType)) {
                throw new IllegalArgumentException("Invalid File Type: " + contentType);
            }

            profile.setLicenseUpload(licenseUpload.getBytes());
        }

        profile.setTinNo(dto.getTinNo());

        MultipartFile identityFormUpload=dto.getIdentityFormUpload();
        if(identityFormUpload != null && !identityFormUpload.isEmpty()){
            String contentType=identityFormUpload.getContentType();
            if(!VALID_FILE_TYPES.contains(contentType)){
                throw new IllegalArgumentException("Invalid File Type" +contentType);
            }
            profile.setIdentityFormUpload(identityFormUpload.getBytes());
        }
        LocalDate yearEstablishmentDate = dateConverter.jalaliToGregorian(dto.getYearOfEstablishment().getYear(), dto.getYearOfEstablishment().getMonthValue(), dto.getYearOfEstablishment().getDayOfMonth());
        profile.setYearOfEstablishment(yearEstablishmentDate);
        LocalDate expiryDate = dateConverter.jalaliToGregorian(dto.getExpiryDate().getYear(), dto.getExpiryDate().getMonthValue(), dto.getExpiryDate().getDayOfMonth());
        profile.setExpiryDate(expiryDate);
        profile.setApplicationFees(dto.getApplicationFees());
        profile.setValidity(dto.getValidity());
        profile.setPlannedActivitiesAndServices(dto.getPlannedActivitiesAndServices());
        profile.setTotalNationalEmployees(dto.getTotalNationalEmployees());
        profile.setTotalInternationalEmployees(dto.getTotalInternationalEmployees());
        profile.setExpectedInvestment(dto.getExpectedInvestment());
        profile.setCash(dto.getCash());
        MultipartFile bankStatementUpload=dto.getBankStatementUpload();
        if(bankStatementUpload != null && !bankStatementUpload.isEmpty()){
            String contentType=bankStatementUpload.getContentType();
            if(!VALID_FILE_TYPES.contains(contentType)){
                throw new IllegalArgumentException("Invalid File Type" +contentType);
            }
            profile.setBankStatementUpload(bankStatementUpload.getBytes());
        }
        profile.setOtherLicenseTaken(dto.getOtherLicenseTaken());
        profile.setCompanyAddress(dto.getCompanyAddress());
        profile.setContactNo(dto.getContactNo());
        profile.setEmail(dto.getEmail());
        profile.setWebsite(dto.getWebsite());
        profile.setPostAddress(dto.getPostAddress());

        LocalDate entryVoucherDate = null;
        if (dto.getEntryApplicationFeeVoucherDate() != null) {
            entryVoucherDate = dateConverter.jalaliToGregorian(
                    dto.getEntryApplicationFeeVoucherDate().getYear(),
                    dto.getEntryApplicationFeeVoucherDate().getMonthValue(),
                    dto.getEntryApplicationFeeVoucherDate().getDayOfMonth()
            );
        }
        profile.setEntryApplicationFeeVoucherDate(entryVoucherDate);
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
    public LicenseApplicant updateProfile(Long licenseId, LicenseApplicantDTO dto) {
        LicenseApplicant profile = repository.findById(licenseId)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found with ID: " + licenseId));
        DateConverter dateConverter = new DateConverter();
        // Convert Jalali date to Gregorian
        LocalDate referToBoardDate = dateConverter.jalaliToGregorian(dto.getReferToBoardDate().getYear(), dto.getReferToBoardDate().getMonthValue(), dto.getReferToBoardDate().getDayOfMonth());
        profile.setReferToBoardDate(referToBoardDate);
        profile.setIsSend(dto.getIsSend());
        return repository.save(profile);
    }

    @Transactional
    public LicenseApplicant updateCompleteProfile(Long id, LicenseApplicantDTO dto) throws IOException {
        LicenseApplicant profile = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found with ID: " + id));
            DateConverter dateConverter = new DateConverter();
            profile.setLicenseNo(dto.getLicenseNo());

        MultipartFile enidUpload = dto.getEnidUpload();
        if (enidUpload != null && !enidUpload.isEmpty()) {
            String contentType = enidUpload.getContentType();
            if (!VALID_FILE_TYPES.contains(contentType)) {
                throw new IllegalArgumentException("Invalid File Type: " + contentType);
            }
            profile.setEnidUpload(enidUpload.getBytes());
        }

        MultipartFile articleOfAssociationUpload = dto.getArticleOfAssociationUpload();
        if (articleOfAssociationUpload != null && !articleOfAssociationUpload.isEmpty()) {
            String contentType = articleOfAssociationUpload.getContentType();
            if (!VALID_FILE_TYPES.contains(contentType)) {
                throw new IllegalArgumentException("Invalid File Type: " + contentType);
            }
            profile.setArticleOfAssociationUpload(articleOfAssociationUpload.getBytes());
        }

        MultipartFile businessPlanUpload = dto.getBusinessPlanUpload();
        if (businessPlanUpload != null && !businessPlanUpload.isEmpty()) {
            String contentType = businessPlanUpload.getContentType();
            if (!VALID_FILE_TYPES.contains(contentType)) {
                throw new IllegalArgumentException("Invalid File Type: " + contentType);
            }
            profile.setBusinessPlanUpload(businessPlanUpload.getBytes());
        }



            MultipartFile licenseUpload = dto.getLicenseUpload();
            if (licenseUpload != null && !licenseUpload.isEmpty()) {
                String contentType = licenseUpload.getContentType();
                if (!VALID_FILE_TYPES.contains(contentType)) {
                    throw new IllegalArgumentException("Invalid File Type: " + contentType);
                }
                profile.setLicenseUpload(licenseUpload.getBytes());
            }

            profile.setTinNo(dto.getTinNo());
            MultipartFile identityFormUpload=dto.getIdentityFormUpload();
            if(identityFormUpload != null && !identityFormUpload.isEmpty()){
                String contentType=identityFormUpload.getContentType();
                if(!VALID_FILE_TYPES.contains(contentType)){
                    throw new IllegalArgumentException("Invalid File Type" +contentType);
                }
                profile.setIdentityFormUpload(identityFormUpload.getBytes());
            }
            LocalDate yearEstablishmentDate = dateConverter.jalaliToGregorian(dto.getYearOfEstablishment().getYear(), dto.getYearOfEstablishment().getMonthValue(), dto.getYearOfEstablishment().getDayOfMonth());
            profile.setYearOfEstablishment(yearEstablishmentDate);
            LocalDate expiryDate = dateConverter.jalaliToGregorian(dto.getExpiryDate().getYear(), dto.getExpiryDate().getMonthValue(), dto.getExpiryDate().getDayOfMonth());
            profile.setExpiryDate(expiryDate);
            profile.setApplicationFees(dto.getApplicationFees());
            profile.setValidity(dto.getValidity());
            profile.setPlannedActivitiesAndServices(dto.getPlannedActivitiesAndServices());
            profile.setTotalNationalEmployees(dto.getTotalNationalEmployees());
            profile.setTotalInternationalEmployees(dto.getTotalInternationalEmployees());
            profile.setExpectedInvestment(dto.getExpectedInvestment());
            profile.setCash(dto.getCash());
            MultipartFile bankStatementUpload=dto.getBankStatementUpload();
            if(bankStatementUpload != null && !bankStatementUpload.isEmpty()){
                String contentType=bankStatementUpload.getContentType();
                if(!VALID_FILE_TYPES.contains(contentType)){
                    throw new IllegalArgumentException("Invalid File Type" +contentType);
                }
                profile.setBankStatementUpload(bankStatementUpload.getBytes());
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
