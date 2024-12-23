package atraintegratedsystems.licenses.service;

import atraintegratedsystems.licenses.dto.LicenseDatabaseFeesExtensionDTO;
import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.model.LicenseDatabaseFeesExtension;
import atraintegratedsystems.licenses.repository.LicenseApprovalRepository;
import atraintegratedsystems.licenses.repository.LicenseDatabaseFeesExtensionRepository;
import atraintegratedsystems.utils.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LicenseDatabaseFeesExtensionService {

    @Autowired
    private LicenseDatabaseFeesExtensionRepository licenseDatabaseFeesExtensionRepository;
    @Autowired
    private LicenseApprovalRepository licenseApprovalRepository;
    @Transactional
    public List<LicenseDatabaseFeesExtensionDTO> getAllExtensions() {
        return licenseDatabaseFeesExtensionRepository.findAll().stream().map(entity -> {
            LicenseDatabaseFeesExtensionDTO dto = new LicenseDatabaseFeesExtensionDTO();
            // Map relevant fields to DTO
            dto.setId(entity.getId());
            dto.setLicenseApprovalId(entity.getLicenseApproval().getId());
            dto.setExtensionStartDate(entity.getExtensionStartDate());
            dto.setExtStartDate(entity.getExtStartDate());
            dto.setExtensionExpireDate(entity.getExtensionStartDate().plusYears(1));
            dto.setExtExpireDate(entity.getExtExpDate());
            dto.setExtensionDatabaseFees(entity.getExtensionDatabaseFees());
            return dto;
        }).collect(Collectors.toList());
    }

    public void saveExtension(LicenseDatabaseFeesExtensionDTO dto) {
        // Retrieve LicenseApproval entity by ID
        LicenseApproval licenseApproval = licenseApprovalRepository.findById(dto.getLicenseApprovalId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid License Approval ID: " + dto.getLicenseApprovalId()));

        // Map DTO to entity
        LicenseDatabaseFeesExtension entity = new LicenseDatabaseFeesExtension();
        entity.setLicenseApproval(licenseApproval); // Set the LicenseApproval relationship

        DateConverter dateConverter = new DateConverter();
        LocalDate extensionStartDate = dateConverter.jalaliToGregorian(dto.getExtensionStartDate().getYear(), dto.getExtensionStartDate().getMonthValue(),dto.getExtensionStartDate().getDayOfMonth());
        entity.setExtensionStartDate(extensionStartDate);
        entity.setExtensionExpireDate(extensionStartDate.plusYears(1));
        entity.setExtensionDatabaseFees(dto.getExtensionDatabaseFees());
//        entity.setExtensionDatabaseFeeBankVoucherNo(dto.getExtensionDatabaseFeeBankVoucherNo());
//        entity.setExtensionDatabaseFeeBankVoucherDate(dto.getExtensionDatabaseFeeBankVoucherDate());
//        entity.setExtensionDatabaseFeeBankVoucherSubmissionDate(dto.getExtensionDatabaseFeeBankVoucherSubmissionDate());
        // Save the entity
        licenseDatabaseFeesExtensionRepository.save(entity);
    }

}
