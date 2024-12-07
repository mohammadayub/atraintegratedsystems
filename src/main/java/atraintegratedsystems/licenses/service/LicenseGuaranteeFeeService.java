package atraintegratedsystems.licenses.service;

import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.repository.LicenseApprovalRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public class LicenseGuaranteeFeeService {

    @Autowired
    private LicenseApprovalRepository licenseApprovalRepository;

    public List<LicenseApproval> getAllApprovalApplicants() {
        return licenseApprovalRepository.findAll();
    }

    public LicenseApproval findById(Long id) {
        return licenseApprovalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LicenseApproval not found with id: " + id));
    }

    public void save(LicenseApproval licenseApproval) {
        licenseApprovalRepository.save(licenseApproval);
    }

}
