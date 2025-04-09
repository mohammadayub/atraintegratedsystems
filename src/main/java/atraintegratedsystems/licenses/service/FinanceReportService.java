package atraintegratedsystems.licenses.service;

import atraintegratedsystems.licenses.model.LicenseApplicant;
import atraintegratedsystems.licenses.repository.LicenseApplicantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FinanceReportService {
    @Autowired
    private LicenseApplicantRepository licenseApplicantRepository;

    @Transactional
    public List<LicenseApplicant> getAllPaidApplicants() {
        return licenseApplicantRepository.listAllApplicationFeesPaid();
    }
}
