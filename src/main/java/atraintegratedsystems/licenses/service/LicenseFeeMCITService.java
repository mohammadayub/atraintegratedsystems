package atraintegratedsystems.licenses.service;

import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.repository.LicenseApprovalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LicenseFeeMCITService {

    @Autowired
    private LicenseApprovalRepository licenseApprovalRepository;

    @Transactional
    public List<LicenseApproval> getAllApprovalApplicants()
    {
        return licenseApprovalRepository.findUnpaidApprovedLicenses();
    }


}
