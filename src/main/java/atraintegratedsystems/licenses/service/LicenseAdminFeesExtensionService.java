package atraintegratedsystems.licenses.service;
import atraintegratedsystems.licenses.dto.LicenseAdminFeesExtensionDTO;
import atraintegratedsystems.licenses.model.LicenseAdminFeesExtension;
import atraintegratedsystems.licenses.repository.LicenseAdminFeesExtensionRepository;
import atraintegratedsystems.licenses.repository.LicenseApprovalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

        // Set Admin ExtendStatusEnteredBy to the logged-in user
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String enteredBy = (principal instanceof UserDetails) ? ((UserDetails) principal).getUsername() : "Unknown";
        entity.setExtendStatusCreatedBy(enteredBy);

        // If extendStatusCreatedDate is null, set it to the current time
        if (entity.getExtendStatusCreatedDate() == null) {
            entity.setExtendStatusCreatedDate(LocalDateTime.now());
        }
        licenseAdminFeesExtensionRepository.save(entity);
    }
}
