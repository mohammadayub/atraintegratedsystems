package atraintegratedsystems.licenses.service;

import atraintegratedsystems.licenses.model.LicenseAdminFeesExtension;
import atraintegratedsystems.licenses.repository.LicenseAdminFeesExtensionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class LicenseAdminFeesExtensionFinanceService {

    @Autowired
    private LicenseAdminFeesExtensionRepository licenseAdminFeesExtensionRepository;

    @Transactional
    public List<LicenseAdminFeesExtension> getAllAdminFeesExtension(){
        return licenseAdminFeesExtensionRepository.findExtensionsWithStatusYes();
    }

    @Transactional
    public LicenseAdminFeesExtension findById(Long id) {
        return licenseAdminFeesExtensionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LicenseApproval not found with id: " + id));
    }

    @Transactional
    public void save(LicenseAdminFeesExtension licenseAdminFeesExtension) {
        licenseAdminFeesExtensionRepository.save(licenseAdminFeesExtension);
    }

}
