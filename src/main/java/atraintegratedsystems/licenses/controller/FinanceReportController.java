package atraintegratedsystems.licenses.controller;

import atraintegratedsystems.licenses.model.LicenseApplicant;
import atraintegratedsystems.licenses.service.FinanceReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class FinanceReportController {
    @Autowired
    private FinanceReportService financeReportService;

    @GetMapping("/licenses/license/report/finance/license_application_fees_report")
    public String showApplicationFeeFinanceReport(Model model) {
        List<LicenseApplicant> applicants = financeReportService.getAllPaidApplicants();
        model.addAttribute("applicants", applicants);
        return "licenses/license/report/finance/license_application_fees_report";
    }

}
