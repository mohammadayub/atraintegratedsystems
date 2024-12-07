package atraintegratedsystems.licenses.service;
import atraintegratedsystems.licenses.dto.LicenseApprovalDTO;
import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.repository.LicenseApprovalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class LicenseFeeMCITService {

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
