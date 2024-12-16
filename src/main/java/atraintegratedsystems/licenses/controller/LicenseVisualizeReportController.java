package atraintegratedsystems.licenses.controller;

import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.service.LicenseGeneralReportService;
import atraintegratedsystems.licenses.service.LicenseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class LicenseVisualizeReportController {

    @Autowired
    private LicenseTypeService licenseTypeService;
    @Autowired
    private LicenseGeneralReportService licenseGeneralReportService;

    // Backend: Controller
    @GetMapping("/licenses/license/report/license_visualize_report")
    public String showApplicationProfile(Model model) {
        List<LicenseApproval> profiles = licenseGeneralReportService.getAllApprovals();

        // Format dates to yyyy-MM-dd for consistency
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        profiles.forEach(profile -> {
            if (profile.getApprovalDate() != null) {
                profile.setFormattedApprovalDate(profile.getApprovalDate().format(formatter));
            }
            if (profile.getLicenseFeeExpiryDate() != null) {
                profile.setFormattedLicenseFeeExpiryDate(profile.getLicenseFeeExpiryDate().format(formatter));
            }
        });

        model.addAttribute("profiles", profiles);
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        return "licenses/license/report/license_visualize_report";
    }

}
