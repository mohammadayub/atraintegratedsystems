package atraintegratedsystems.licenses.service;

import atraintegratedsystems.licenses.model.LicenseApplicant;
import atraintegratedsystems.licenses.repository.LicenseApplicantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LicenseApplicantFinanceService {

    @Autowired
    private LicenseApplicantRepository licenseApplicantRepository;
    @Transactional
    public List<LicenseApplicant> getAllUnpaid(){
        return licenseApplicantRepository.findAllApplicantsWithNullPaymentStatus();
    }

    @Transactional
    public Optional<LicenseApplicant> getApplicantId(Long id){
        return licenseApplicantRepository.findById(id);
    }

    @Transactional
    public void PaymentSave(LicenseApplicant licenseApplicant){
        licenseApplicantRepository.save(licenseApplicant);
    }

    @Transactional
    public Optional<LicenseApplicant> getApplicantByReqId(String reqId){
        return licenseApplicantRepository.findByReqId(reqId);
    }

}
