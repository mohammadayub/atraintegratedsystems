package atraintegratedsystems.licenses.controller;
import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.service.LicenseGeneralReportService;
import atraintegratedsystems.licenses.service.LicenseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LicenseProcessFollowUpReportController {

    @Autowired
    private LicenseTypeService licenseTypeService;
    @Autowired
    private LicenseGeneralReportService licenseGeneralReportService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_ADMIN') or hasRole('ROLE_LICENSE')")
    @GetMapping("/licenses/license/report/license_process_followup_report")
    public String showApplicationProfile(Model model) {
        List<LicenseApproval> profiles = licenseGeneralReportService.getAllLicenses();
        model.addAttribute("profiles", profiles);
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        return "licenses/license/report/license_process_followup_report";
    }
}