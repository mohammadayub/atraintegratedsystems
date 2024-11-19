package atraintegratedsystems.licenses.service;
import atraintegratedsystems.licenses.dto.LicenseApplicantDTO;
import atraintegratedsystems.licenses.dto.LicenseApprovalDTO;
import atraintegratedsystems.licenses.model.LicenseApplicant;
import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.model.LicenseType;
import atraintegratedsystems.licenses.repository.LicenseApplicantRepository;
import atraintegratedsystems.licenses.repository.LicenseApprovalRepository;
import atraintegratedsystems.licenses.repository.LicenseTypeRepository;
import atraintegratedsystems.utils.DateConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class LicenseApprovalService {


    @Autowired
    private LicenseApplicantRepository licenseApplicantRepository;
    @Autowired
    private LicenseApprovalRepository licenseApprovalRepository;

    @Autowired
    private LicenseTypeRepository licenseTypeRepository;
    @Transactional
    public List<LicenseApplicant> getAllpaid(){
        return licenseApplicantRepository.findAllApplicantsWithPaid();
    }

    public Optional<LicenseApproval> getByApplicantId(Long applicantId) {
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
            log.info("Approval Date (Gregorian): {}", approvalDate);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid approval date provided: " + dto.getApprovalDate(), e);
        }

        // Set Approval Status
        profile.setApprovalStatus(dto.getApprovalStatus());
        log.info("Approval Status: {}", dto.getApprovalStatus());

        if (dto.getLicenseTypeId() != null) {
            LicenseType licenseType = licenseTypeRepository.findById(dto.getLicenseTypeId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid License Type ID: " + dto.getLicenseTypeId()));
            profile.setLicenseType(licenseType);
            log.info("License Type: {}", licenseType.getName());
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

        log.info("Financial details set for profile.");

        // Set License Applicant
        if (dto.getLicenseApplicantId() != null) {
            LicenseApplicant licenseApplicant = licenseApplicantRepository.findById(dto.getLicenseApplicantId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid License Applicant ID: " + dto.getLicenseApplicantId()));
            profile.setLicenseApplicant(licenseApplicant);
            log.info("License Applicant: {}", licenseApplicant.getId());
        } else {
            throw new IllegalArgumentException("License Applicant ID is required.");
        }

        // Save to Database
        LicenseApproval savedProfile = licenseApprovalRepository.save(profile);
        log.info("License Approval saved with ID: {}", savedProfile.getId());

        return savedProfile;
    }


    private String generateApprovalId() {
        return "APP-" + UUID.randomUUID().toString();
    }



}
