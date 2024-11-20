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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        MultipartFile tinUpload=dto.getTinUpload();
        if(tinUpload != null && !tinUpload.isEmpty()){
            String contentType=tinUpload.getContentType();
            if(!VALID_FILE_TYPES.contains(contentType)){
                throw new IllegalArgumentException("Invalid File Type" +contentType);
            }
            profile.setTinUpload(tinUpload.getBytes());
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
        if (dto.getEntryVoucherDate() != null) {
            entryVoucherDate = dateConverter.jalaliToGregorian(
                    dto.getEntryVoucherDate().getYear(),
                    dto.getEntryVoucherDate().getMonthValue(),
                    dto.getEntryVoucherDate().getDayOfMonth()
            );
        }
        profile.setEntryVoucherDate(entryVoucherDate);
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


}
