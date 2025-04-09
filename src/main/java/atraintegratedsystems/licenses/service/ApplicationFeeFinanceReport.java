package atraintegratedsystems.licenses.service;

import atraintegratedsystems.licenses.model.LicenseApplicant;
import atraintegratedsystems.licenses.repository.Finanace_Report;
import atraintegratedsystems.licenses.repository.LicenseApplicantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ApplicationFeeFinanceReport {
    @Autowired
    private Finanace_Report financeReport;

    @Transactional
    public List<LicenseApplicant> getAllPaidApplicants() {
        return financeReport.applicationFeeFinanaceReport();
    }
}
