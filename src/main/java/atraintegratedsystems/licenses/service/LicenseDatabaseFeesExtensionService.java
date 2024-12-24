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
        return licenseDatabaseFeesExtensionRepository.findExtensionsWithStatusNotYes().stream().map(entity -> {
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

    @Transactional
    public void updateExtendStatus(Long id, String extendStatus) {
        LicenseDatabaseFeesExtension entity = licenseDatabaseFeesExtensionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ID: " + id));
        entity.setExtendStatus(extendStatus);
        licenseDatabaseFeesExtensionRepository.save(entity);
    }

}
