package atraintegratedsystems.licenses.service;

import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.repository.LicenseApprovalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LicenseCertificateService {

    @Autowired
    private LicenseApprovalRepository licenseApprovalRepository;
    public List<LicenseApproval> getAllPaid()
    {
      return licenseApprovalRepository.findApprovedAndPaidLicenses();
    }

}