package atraintegratedsystems.licenses.service;

import atraintegratedsystems.licenses.model.LicenseDatabaseFeesExtension;
import atraintegratedsystems.licenses.repository.LicenseDatabaseFeesExtensionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class LicenseDatabaseFeesExtensionFinanceService {
    @Autowired
    private LicenseDatabaseFeesExtensionRepository licenseDatabaseFeesExtensionRepository;

    @Transactional
    public List<LicenseDatabaseFeesExtension> getAllDatabaseFeesExtension(){
        return licenseDatabaseFeesExtensionRepository.findExtensionsWithStatusYes();
    }

    @Transactional
    public LicenseDatabaseFeesExtension findById(Long id) {
        return licenseDatabaseFeesExtensionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LicenseApproval not found with id: " + id));
    }

    @Transactional
    public void save(LicenseDatabaseFeesExtension licenseAdminFeesExtension) {
        licenseDatabaseFeesExtensionRepository.save(licenseAdminFeesExtension);
    }
}
