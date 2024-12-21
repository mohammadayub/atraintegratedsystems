package atraintegratedsystems.licenses.service;

import atraintegratedsystems.licenses.dto.LicenseDatabaseFeesExtensionDTO;
import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.model.LicenseDatabaseFeesExtension;
import atraintegratedsystems.licenses.repository.LicenseApprovalRepository;
import atraintegratedsystems.licenses.repository.LicenseDatabaseFeesExtensionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            dto.setExtensionExpireDate(entity.getExtensionExpireDate());
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
//        entity.setExtensionDatabaseFees(dto.getExtensionDatabaseFees());
//        entity.setExtensionDatabaseFeeBankVoucherNo(dto.getExtensionDatabaseFeeBankVoucherNo());
//        entity.setExtensionDatabaseFeeBankVoucherDate(dto.getExtensionDatabaseFeeBankVoucherDate());
//        entity.setExtensionDatabaseFeeBankVoucherSubmissionDate(dto.getExtensionDatabaseFeeBankVoucherSubmissionDate());

        // Save the entity
        licenseDatabaseFeesExtensionRepository.save(entity);
    }

}
