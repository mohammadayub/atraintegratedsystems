package atraintegratedsystems.licenses.controller;

import atraintegratedsystems.licenses.model.LicenseApplicant;
import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.service.LicenseGeneralReportService;
import atraintegratedsystems.licenses.service.LicenseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LicenseGeneralReportController {

    @Autowired
    private LicenseTypeService licenseTypeService;
    @Autowired
    private LicenseGeneralReportService licenseGeneralReportService;

    @GetMapping("/licenses/license/report/license_general_report")
    public String showApplicationProfile(Model model) {
        List<LicenseApproval> profiles = licenseGeneralReportService.getAllApprovals();
        model.addAttribute("profiles", profiles);
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        return "licenses/license/report/license_general_report";
    }
}