package atraintegratedsystems.licenses.service;

import atraintegratedsystems.licenses.dto.LicenseDatabaseFeesExtensionDTO;
import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.model.LicenseDatabaseFeesExtension;
import atraintegratedsystems.licenses.repository.LicenseApprovalRepository;
import atraintegratedsystems.licenses.repository.LicenseDatabaseFeesExtensionRepository;
import atraintegratedsystems.utils.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        return licenseDatabaseFeesExtensionRepository.findExtensionsWithStatusNotYes().stream().map(entity -> {
            LicenseDatabaseFeesExtensionDTO dto = new LicenseDatabaseFeesExtensionDTO();
            // Map relevant fields to DTO
            dto.setId(entity.getId());
            dto.setLicenseApprovalId(entity.getLicenseApproval().getId());
            dto.setLicenseCompanyName(entity.getLicenseApproval().getLicenseApplicant().getCompanyLicenseName());
            dto.setExtensionStartDate(entity.getExtensionStartDate());
            dto.setExtStartDate(entity.getExtentStartDate());
            dto.setExtensionExpireDate(entity.getExtensionStartDate().plusYears(1));
            dto.setExtExpireDate(entity.getExtentExpDate());
            dto.setExtensionDatabaseFees(entity.getExtensionDatabaseFees());
            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public void updateExtendStatus(Long id, String extendStatus) {
        LicenseDatabaseFeesExtension entity = licenseDatabaseFeesExtensionRepository.findById(id)
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
        licenseDatabaseFeesExtensionRepository.save(entity);
    }

}
