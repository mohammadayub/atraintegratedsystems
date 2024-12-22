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

            DateConverter dateConverter= new DateConverter();
            LocalDate extensionStartDate = dateConverter.jalaliToGregorian(entity.getExtensionStartDate().getYear(),entity.getExtensionStartDate().getMonthValue(),entity.getExtensionStartDate().getDayOfMonth());
            dto.setExtensionStartDate(extensionStartDate);

            dto.setExtensionExpireDate(extensionStartDate.plusYears(1));
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
        entity.setExtensionStartDate(dto.getExtensionStartDate());
        entity.setExtensionExpireDate(dto.getExtensionExpireDate());
        entity.setExtensionDatabaseFees(dto.getExtensionDatabaseFees());
//        entity.setExtensionDatabaseFeeBankVoucherNo(dto.getExtensionDatabaseFeeBankVoucherNo());
//        entity.setExtensionDatabaseFeeBankVoucherDate(dto.getExtensionDatabaseFeeBankVoucherDate());
//        entity.setExtensionDatabaseFeeBankVoucherSubmissionDate(dto.getExtensionDatabaseFeeBankVoucherSubmissionDate());
        // Save the entity
        licenseDatabaseFeesExtensionRepository.save(entity);
    }

}
