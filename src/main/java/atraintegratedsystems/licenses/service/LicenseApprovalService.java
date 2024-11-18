package atraintegratedsystems.licenses.service;

import atraintegratedsystems.licenses.model.LicenseApplicant;
import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.repository.LicenseApplicantRepository;
import atraintegratedsystems.licenses.repository.LicenseApprovalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LicenseApprovalService {


    @Autowired
    private LicenseApprovalRepository licenseApprovalRepository;

    @Autowired
    private LicenseApplicantRepository licenseApplicantRepository;

    @Transactional
    public List<LicenseApplicant> getAllApplicants(){
        return licenseApplicantRepository.findAll();
    }


}
