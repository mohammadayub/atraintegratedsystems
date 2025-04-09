package atraintegratedsystems.licenses.controller;

import atraintegratedsystems.licenses.model.LicenseApplicant;
import atraintegratedsystems.licenses.service.ApplicationFeeFinanceReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class applicationFeeFinanceController {
    @Autowired
    private ApplicationFeeFinanceReport applicationFeeFinanceReport;

    @GetMapping("/finance/applicationFeesReport")
    public String showApplicationFeeFinanceReport(Model model) {
        List<LicenseApplicant> applicants = applicationFeeFinanceReport.getAllPaidApplicants();
        model.addAttribute("applicants", applicants);
        return "licenses/license/report/finance/application_report";
    }


//    Bellow is RestAPI Way
//    @GetMapping("/getApplicationFees")
//    @ResponseBody
//    public List<LicenseApplicant> applicationFeeFinanceReport()
//    {
//        return applicationFeeFinanceReport.getAllPaidApplicants();
//
//    }
}
