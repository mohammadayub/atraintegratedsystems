package atraintegratedsystems.licenses.controller;

import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.service.FinanceReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class FinanceDatabaseFeesPaidReportController {
    @Autowired
    private FinanceReportService financeReportService;

    @GetMapping("/licenses/license/report/finance/database_fees_paid_report")
    public String showApplicationFeeFinanceReport(Model model) {
        List<LicenseApproval> approvals = financeReportService.getDatabaseFeesPaidReport();
        model.addAttribute("approvals", approvals);
        return "licenses/license/report/finance/database_fees_paid_report";
    }
}
