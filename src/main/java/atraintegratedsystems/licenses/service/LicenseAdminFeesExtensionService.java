package atraintegratedsystems.licenses.service;

import atraintegratedsystems.licenses.dto.LicenseAdminFeesExtensionDTO;
import atraintegratedsystems.licenses.dto.LicenseDatabaseFeesExtensionDTO;
import atraintegratedsystems.licenses.model.LicenseAdminFeesExtension;
import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.model.LicenseDatabaseFeesExtension;
import atraintegratedsystems.licenses.repository.LicenseAdminFeesExtensionRepository;
import atraintegratedsystems.licenses.repository.LicenseApprovalRepository;
import atraintegratedsystems.licenses.repository.LicenseDatabaseFeesExtensionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LicenseAdminFeesExtensionService {

    @Autowired
    private LicenseAdminFeesExtensionRepository licenseAdminFeesExtensionRepository;
    @Autowired
    private LicenseApprovalRepository licenseApprovalRepository;
    @Transactional
    public List<LicenseAdminFeesExtensionDTO> getAllExtensions() {
        return licenseAdminFeesExtensionRepository.findAll().stream().map(entity -> {
            LicenseAdminFeesExtensionDTO dto = new LicenseAdminFeesExtensionDTO();
            // Map relevant fields to DTO
            dto.setId(entity.getId());
            dto.setLicenseApprovalId(entity.getLicenseApproval().getId());
            dto.setExtensionStartDate(entity.getExtensionStartDate());
            dto.setExtensionExpireDate(entity.getExtensionExpireDate());
            dto.setExtensionAdministrationFees(entity.getExtensionAdministrationFees());
            return dto;
        }).collect(Collectors.toList());
    }

    public void saveExtension(LicenseAdminFeesExtensionDTO dto) {
        // Retrieve LicenseApproval entity by ID
        LicenseApproval licenseApproval = licenseApprovalRepository.findById(dto.getLicenseApprovalId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid License Approval ID: " + dto.getLicenseApprovalId()));

        // Map DTO to entity
        LicenseAdminFeesExtension entity = new LicenseAdminFeesExtension();
        entity.setLicenseApproval(licenseApproval); // Set the LicenseApproval relationship
        entity.setExtensionStartDate(dto.getExtensionStartDate());
        entity.setExtensionExpireDate(dto.getExtensionExpireDate());
        entity.setExtensionAdministrationFees(dto.getExtensionAdministrationFees());
//        entity.setExtensionDatabaseFeeBankVoucherNo(dto.getExtensionDatabaseFeeBankVoucherNo());
//        entity.setExtensionDatabaseFeeBankVoucherDate(dto.getExtensionDatabaseFeeBankVoucherDate());
//        entity.setExtensionDatabaseFeeBankVoucherSubmissionDate(dto.getExtensionDatabaseFeeBankVoucherSubmissionDate());
        // Save the entity
        licenseAdminFeesExtensionRepository.save(entity);
    }
}
