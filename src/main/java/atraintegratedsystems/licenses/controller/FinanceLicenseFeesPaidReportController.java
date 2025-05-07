package atraintegratedsystems.licenses.controller;

import atraintegratedsystems.licenses.model.LicenseApplicant;
import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.service.FinanceReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@Controller
public class FinanceLicenseFeesPaidReportController {

    @Autowired
    private FinanceReportService financeReportService;

    @GetMapping("/licenses/license/report/finance/license_fees_paid_report")
    public String showApplicationFeeFinanceReport(Model model) {
        List<LicenseApproval> approvals = financeReportService.getLicenseFeesPaidReport();
        model.addAttribute("approvals", approvals);
        return "licenses/license/report/finance/license_fees_paid_report";
    }
}
