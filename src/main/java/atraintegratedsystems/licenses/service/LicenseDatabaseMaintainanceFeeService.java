package atraintegratedsystems.licenses.service;

import atraintegratedsystems.licenses.dto.LicenseDatabaseFeesExtensionDTO;
import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.model.LicenseDatabaseFeesExtension;
import atraintegratedsystems.licenses.repository.LicenseApprovalRepository;
import atraintegratedsystems.licenses.repository.LicenseDatabaseFeesExtensionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LicenseDatabaseMaintainanceFeeService {

    @Autowired
    private LicenseDatabaseFeesExtensionRepository licenseDatabaseFeesExtensionRepository;
    @Autowired
    private LicenseApprovalRepository licenseApprovalRepository;


    @Transactional
    public List<LicenseApproval> getAllApprovalApplicantsNotPaidDatabaseFees() {
        return licenseApprovalRepository.findUnpaidDatabaseFees();
    }


    @Transactional
    public LicenseApproval findById(Long id) {
        return licenseApprovalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LicenseApproval not found with id: " + id));
    }

    @Transactional
    public void save(LicenseApproval licenseApproval) {
        licenseApprovalRepository.save(licenseApproval);
    }


}
