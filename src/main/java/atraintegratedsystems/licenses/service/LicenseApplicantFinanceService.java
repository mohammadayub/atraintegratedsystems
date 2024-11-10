package atraintegratedsystems.licenses.service;

import atraintegratedsystems.licenses.model.LicenseApplicant;
import atraintegratedsystems.licenses.repository.LicenseApplicantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LicenseApplicantFinanceService {

    @Autowired
    private LicenseApplicantRepository licenseApplicantRepository;

    public List<LicenseApplicant> getAllUnpaid(){
        return licenseApplicantRepository.findByPaymentStatusIsNull();
    }
}
