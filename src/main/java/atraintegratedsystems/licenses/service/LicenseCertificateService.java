package atraintegratedsystems.licenses.service;

import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.repository.LicenseApprovalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LicenseCertificateService {

    @Autowired
    private LicenseApprovalRepository licenseApprovalRepository;
    @Transactional
    public List<LicenseApproval> getAllPaid()
    {
      return licenseApprovalRepository.findApprovedAndPaidLicenses();
    }

    @Transactional
    public Optional<LicenseApproval> getApprovalByApplicantId(Long applicantId) {
        return licenseApprovalRepository.findById(applicantId);
    }

}