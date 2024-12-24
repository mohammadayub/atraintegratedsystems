package atraintegratedsystems.licenses.service;
import atraintegratedsystems.licenses.dto.LicenseApplicantDTO;
import atraintegratedsystems.licenses.dto.LicenseApprovalDTO;
import atraintegratedsystems.licenses.model.*;
import atraintegratedsystems.licenses.repository.*;
import atraintegratedsystems.utils.DateConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LicenseApprovalService {


    @Autowired
    private LicenseApplicantRepository licenseApplicantRepository;
    @Autowired
    private LicenseApprovalRepository licenseApprovalRepository;

    @Autowired
    private LicenseTypeRepository licenseTypeRepository;

    @Autowired
    private LicenseDatabaseFeesExtensionRepository licenseDatabaseFeesExtensionRepository;

    @Autowired
    private LicenseAdminFeesExtensionRepository licenseAdminFeesExtensionRepository;

    @Transactional
    public List<LicenseApprovalDTO> getAllForDatabaseFeesExtension() {
        return licenseApprovalRepository.findAll().stream().map(entity -> {
            LicenseApprovalDTO dto = new LicenseApprovalDTO();
            // Map relevant fields to DTO
            dto.setId(entity.getId());
            dto.setApprovalId(entity.getApprovalId());
            dto.setApplicantLicenseCompanyName(entity.getLicenseApplicant().getCompanyLicenseName());
            dto.setLicenseValidity(entity.getLicenseApplicant().getValidity());
            dto.setDatabaseYearlyMaintainanceFees(entity.getDatabaseYearlyMaintainanceFees());
            dto.setApprovalDate(entity.getApprovalDate());
            dto.setAppDate(entity.getAppDate());
            dto.setDatabaseMaintianenceFeeExpiryDate(entity.getDatabaseMaintianenceFeeExpiryDate());
            dto.setDbFeesExpireDate(entity.getdatabaseFeesExpiryDate());
            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public List<LicenseApprovalDTO> getAllForAdminFeesExtension() {
        return licenseApprovalRepository.findAll().stream().map(entity -> {
            LicenseApprovalDTO dto = new LicenseApprovalDTO();
            // Map relevant fields to DTO
            dto.setId(entity.getId());
            dto.setApprovalId(entity.getApprovalId());
            dto.setApplicantLicenseCompanyName(entity.getLicenseApplicant().getCompanyLicenseName());
            dto.setLicenseValidity(entity.getLicenseApplicant().getValidity());
            dto.setAdministrativeYearlyFees(entity.getAdministrativeYearlyFees());
            dto.setApprovalDate(entity.getApprovalDate());
            dto.setAppDate(entity.getAppDate());
            dto.setAdministrationFeeExpiryDate(entity.getAdministrationFeeExpiryDate());
            dto.setAdFeesExpireDate(entity.getadminFeeExpiryDate());
            return dto;
        }).collect(Collectors.toList());
    }


    @Transactional
    public List<LicenseApplicant> getAllpaid(){
        return licenseApplicantRepository.findAllApplicantsWithPaid();
    }

    public Optional<LicenseApproval> getApprovalByApplicantId(Long applicantId) {
        return licenseApprovalRepository.findByLicenseApplicantId(applicantId);
    }

    @Transactional
    public Optional<LicenseApplicant> getApplicantId(Long id){
        return licenseApplicantRepository.findById(id);
    }



    @Transactional
    public LicenseApproval saveApproval(LicenseApprovalDTO dto) throws Exception {
        log.info("Starting saveApproval for DTO: {}", dto);

        // Create and populate LicenseApproval entity
        LicenseApproval profile = new LicenseApproval();

        // Generate and set Approval ID
        String approvalId = generateApprovalId();
        profile.setApprovalId(approvalId);
        log.info("Generated Approval ID: {}", approvalId);

        // Convert Jalali date to Gregorian
        try {
            DateConverter dateConverter = new DateConverter();
            LocalDate approvalDate = dateConverter.jalaliToGregorian(
                    dto.getApprovalDate().getYear(),
                    dto.getApprovalDate().getMonthValue(),
                    dto.getApprovalDate().getDayOfMonth()
            );
            profile.setApprovalDate(approvalDate);

            // Fetch the LicenseApplicant
            LicenseApplicant licenseApplicant = licenseApplicantRepository.findById(dto.getId())
                    .orElseThrow(() -> new IllegalArgumentException("LicenseApplicant not found with ID: " + dto.getId()));

            int licenseValidityYear = licenseApplicant.getValidity();

            profile.setDatabaseMaintianenceFeeExpiryDate(approvalDate.plusYears(1));
            profile.setAdministrationFeeExpiryDate(approvalDate.plusYears(1));
            profile.setLicenseFeeExpiryDate(approvalDate.plusYears(licenseValidityYear));
            profile.setGuaranteeFeeExpiryDate(approvalDate.plusYears(licenseValidityYear));

            log.info("Approval Date (Gregorian): {}", approvalDate);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid approval date provided: " + dto.getApprovalDate(), e);
        }

        // Set Approval Status
        profile.setApprovalStatus(dto.getApprovalStatus());
        profile.setBoardDecisionNumber(dto.getBoardDecisionNumber());
        profile.setBoardDecisions(dto.getBoardDecisions());

        // Set License Type
        if (dto.getLicenseTypeId() != null) {
            LicenseType licenseType = licenseTypeRepository.findById(dto.getLicenseTypeId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid License Type ID: " + dto.getLicenseTypeId()));
            profile.setLicenseType(licenseType);
        } else {
            throw new IllegalArgumentException("License Type is required.");
        }

        // Set Financial and Payment Details
        profile.setCurrencyType(dto.getCurrencyType());
        profile.setLicenseFees(dto.getLicenseFees() != null ? dto.getLicenseFees() : BigDecimal.ZERO);
        profile.setLicensePaymentOffice(dto.getLicensePaymentOffice());
        profile.setAdministrativeYearlyFees(dto.getAdministrativeYearlyFees() != null ? dto.getAdministrativeYearlyFees() : BigDecimal.ZERO);
        profile.setAdminstrivateYearlyFeesPaymentOffice(dto.getAdminstrivateYearlyFeesPaymentOffice());
        profile.setGuaranteeFees(dto.getGuaranteeFees() != null ? dto.getGuaranteeFees() : BigDecimal.ZERO);
        profile.setGuaranteeFeesPaymentOffice(dto.getGuaranteeFeesPaymentOffice());
        profile.setDatabaseYearlyMaintainanceFees(dto.getDatabaseYearlyMaintainanceFees() != null ? dto.getDatabaseYearlyMaintainanceFees() : BigDecimal.ZERO);
        profile.setDatabaseYearlyMaintainanceFeesPaymentOffice(dto.getDatabaseYearlyMaintainanceFeesPaymentOffice());

        // Set License Applicant
        if (dto.getLicenseApplicantId() != null) {
            LicenseApplicant licenseApplicant = licenseApplicantRepository.findById(dto.getLicenseApplicantId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid License Applicant ID: " + dto.getLicenseApplicantId()));
            profile.setLicenseApplicant(licenseApplicant);
        } else {
            throw new IllegalArgumentException("License Applicant ID is required.");
        }

        // Save LicenseApproval entity
        LicenseApproval savedProfile = licenseApprovalRepository.save(profile);
        log.info("License Approval saved with ID: {}", savedProfile.getId());

        // Add entries to LicenseDatabaseFeesExtension for validity period
        int validityYears = savedProfile.getLicenseApplicant().getValidity();
        LocalDate extensionStartDate = savedProfile.getApprovalDate().plusYears(1);
        for (int i = 1; i < validityYears; i++) {
            LocalDate extensionEndDate = extensionStartDate.plusYears(1);
            LicenseDatabaseFeesExtension extension = new LicenseDatabaseFeesExtension();
            extension.setLicenseApproval(savedProfile);
            extension.setExtensionStartDate(extensionStartDate);
            extension.setExtensionExpireDate(extensionEndDate);
            extension.setExtensionDatabaseFees(savedProfile.getDatabaseYearlyMaintainanceFees());
            extension.setExtendStatus("No");
            licenseDatabaseFeesExtensionRepository.save(extension);
            log.info("Added LicenseDatabaseFeesExtension record for year: {}", i);

            extensionStartDate = extensionEndDate;
        }

        // Add entries to LicenseAdminFeesExtension for validity period
        int adminValidityYears = savedProfile.getLicenseApplicant().getValidity();
        LocalDate adminExtensionStartDate = savedProfile.getApprovalDate().plusYears(1);

        for (int i = 1; i < adminValidityYears; i++) {
            LocalDate adminExtensionEndDate = adminExtensionStartDate.plusYears(1);
            LicenseAdminFeesExtension adminExtension = new LicenseAdminFeesExtension();

            // Set fields for the LicenseAdminFeesExtension
            adminExtension.setLicenseApproval(savedProfile);
            adminExtension.setExtensionStartDate(adminExtensionStartDate);
            adminExtension.setExtensionExpireDate(adminExtensionEndDate);
            adminExtension.setExtensionAdministrationFees(savedProfile.getAdministrativeYearlyFees());
            adminExtension.setExtendStatus("No");

            // Save the admin extension record
            licenseAdminFeesExtensionRepository.save(adminExtension);
            log.info("Added LicenseAdminFeesExtension record for year: {}", i);

            // Update the start date for the next iteration
            adminExtensionStartDate = adminExtensionEndDate;
        }


        return savedProfile;
    }




//    @Transactional
//    public LicenseApproval saveApproval(LicenseApprovalDTO dto) throws Exception {
//        log.info("Starting saveApproval for DTO: {}", dto);
//
//        // Create and populate LicenseApproval entity
//        LicenseApproval profile = new LicenseApproval();
//
//        // Generate and set Approval ID
//        String approvalId = generateApprovalId();
//        profile.setApprovalId(approvalId);
//        log.info("Generated Approval ID: {}", approvalId);
//
//        // Convert Jalali date to Gregorian
//        try {
//            DateConverter dateConverter = new DateConverter();
//            LocalDate approvalDate = dateConverter.jalaliToGregorian(
//                    dto.getApprovalDate().getYear(),
//                    dto.getApprovalDate().getMonthValue(),
//                    dto.getApprovalDate().getDayOfMonth()
//            );
//            profile.setApprovalDate(approvalDate);
//
//            // Expiry Date
//
//            // Fetch the LicenseApplicant from the repository
//            LicenseApplicant licenseApplicant = licenseApplicantRepository.findById(dto.getId())
//                    .orElseThrow(() -> new IllegalArgumentException("LicenseApplicant not found with ID: " + dto.getId()));
//
//            int licenseValidityYear=licenseApplicant.getValidity();
//
//            profile.setDatabaseMaintianenceFeeExpiryDate(approvalDate.plusYears(1));
//            profile.setAdministrationFeeExpiryDate(approvalDate.plusYears(1));
//            profile.setLicenseFeeExpiryDate(approvalDate.plusYears(licenseValidityYear));
//            profile.setGuaranteeFeeExpiryDate(approvalDate.plusYears(licenseValidityYear));
//
//            log.info("Approval Date (Gregorian): {}", approvalDate);
//        } catch (Exception e) {
//            throw new IllegalArgumentException("Invalid approval date provided: " + dto.getApprovalDate(), e);
//        }
//
//        // Set Approval Status
//        profile.setApprovalStatus(dto.getApprovalStatus());
//        log.info("Approval Status: {}", dto.getApprovalStatus());
//
//        profile.setBoardDecisionNumber(dto.getBoardDecisionNumber());
//        log.info("Board Decision Number: {}",dto.getBoardDecisionNumber());
//        profile.setBoardDecisions(dto.getBoardDecisions());
//        log.info("Approval Status: {}", dto.getBoardDecisions());
//
//
//        if (dto.getLicenseTypeId() != null) {
//            LicenseType licenseType = licenseTypeRepository.findById(dto.getLicenseTypeId())
//                    .orElseThrow(() -> new IllegalArgumentException("Invalid License Type ID: " + dto.getLicenseTypeId()));
//            profile.setLicenseType(licenseType);
//            log.info("License Type: {}", licenseType.getName());
//        } else {
//            throw new IllegalArgumentException("License Type is required.");
//        }
//
//        // Set Financial and Payment Details
//        profile.setCurrencyType(dto.getCurrencyType());
//        profile.setLicenseFees(dto.getLicenseFees() != null ? dto.getLicenseFees() : BigDecimal.ZERO);
//        profile.setLicensePaymentOffice(dto.getLicensePaymentOffice());
//        profile.setAdministrativeYearlyFees(dto.getAdministrativeYearlyFees() != null ? dto.getAdministrativeYearlyFees() : BigDecimal.ZERO);
//        profile.setAdminstrivateYearlyFeesPaymentOffice(dto.getAdminstrivateYearlyFeesPaymentOffice());
//        profile.setGuaranteeFees(dto.getGuaranteeFees() != null ? dto.getGuaranteeFees() : BigDecimal.ZERO);
//        profile.setGuaranteeFeesPaymentOffice(dto.getGuaranteeFeesPaymentOffice());
//        profile.setDatabaseYearlyMaintainanceFees(dto.getDatabaseYearlyMaintainanceFees() != null ? dto.getDatabaseYearlyMaintainanceFees() : BigDecimal.ZERO);
//        profile.setDatabaseYearlyMaintainanceFeesPaymentOffice(dto.getDatabaseYearlyMaintainanceFeesPaymentOffice());
//
//
//
//
//
//
//
//
//        log.info("Financial details set for profile.");
//
//        // Set License Applicant
//        if (dto.getLicenseApplicantId() != null) {
//            LicenseApplicant licenseApplicant = licenseApplicantRepository.findById(dto.getLicenseApplicantId())
//                    .orElseThrow(() -> new IllegalArgumentException("Invalid License Applicant ID: " + dto.getLicenseApplicantId()));
//            profile.setLicenseApplicant(licenseApplicant);
//            log.info("License Applicant: {}", licenseApplicant.getId());
//        } else {
//            throw new IllegalArgumentException("License Applicant ID is required.");
//        }
//
//        // Save to Database
//        LicenseApproval savedProfile = licenseApprovalRepository.save(profile);
//        log.info("License Approval saved with ID: {}", savedProfile.getId());
//        return savedProfile;
//    }

    private String generateApprovalId() {
        Long maxId = licenseApprovalRepository.findMaxId();
        String prefix = "ATRA-APP-";
        if (maxId == null) {
            return prefix + "0001";
        }
        Long nextId = maxId + 1;
        return prefix + String.format("%04d", nextId);
    }


}
