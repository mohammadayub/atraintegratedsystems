package atraintegratedsystems.licenses.service;

import atraintegratedsystems.licenses.model.LicenseType;
import atraintegratedsystems.licenses.repository.LicenseTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LicenseTypeService {

    @Autowired
    private LicenseTypeRepository repository;

    public List<LicenseType> findAll() {
        return repository.findAll();
    }

    public Optional<LicenseType> findById(Long id) {
        return repository.findById(id);
    }

    public LicenseType save(LicenseType licenseType) {
        return repository.save(licenseType);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
