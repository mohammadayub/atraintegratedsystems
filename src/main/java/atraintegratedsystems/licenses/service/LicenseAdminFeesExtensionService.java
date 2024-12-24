package atraintegratedsystems.licenses.service;

import atraintegratedsystems.licenses.dto.LicenseAdminFeesExtensionDTO;
import atraintegratedsystems.licenses.dto.LicenseDatabaseFeesExtensionDTO;
import atraintegratedsystems.licenses.model.LicenseAdminFeesExtension;
import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.model.LicenseDatabaseFeesExtension;
import atraintegratedsystems.licenses.repository.LicenseAdminFeesExtensionRepository;
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
public class LicenseAdminFeesExtensionService {

    @Autowired
    private LicenseAdminFeesExtensionRepository licenseAdminFeesExtensionRepository;
    @Autowired
    private LicenseApprovalRepository licenseApprovalRepository;
    @Transactional
    public List<LicenseAdminFeesExtensionDTO> getAllExtensions() {
        return licenseAdminFeesExtensionRepository.findExtensionsWithStatusNotYes().stream().map(entity -> {
            LicenseAdminFeesExtensionDTO dto = new LicenseAdminFeesExtensionDTO();
            // Map relevant fields to DTO
            dto.setId(entity.getId());
            dto.setLicenseApprovalId(entity.getLicenseApproval().getId());
            dto.setLicenseCompanyName(entity.getLicenseApproval().getLicenseApplicant().getCompanyLicenseName());
            dto.setExtensionStartDate(entity.getExtensionStartDate());
            dto.setExtStartDate(entity.getExtentStartDate());
            dto.setExtensionExpireDate(entity.getExtensionExpireDate());
            dto.setExtExpireDate(entity.getExtentExpDate());
            dto.setExtensionAdministrationFees(entity.getExtensionAdministrationFees());
            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public void updateExtendStatus(Long id, String extendStatus) {
        LicenseAdminFeesExtension entity = licenseAdminFeesExtensionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ID: " + id));
        entity.setExtendStatus(extendStatus);
        licenseAdminFeesExtensionRepository.save(entity);
    }
}
