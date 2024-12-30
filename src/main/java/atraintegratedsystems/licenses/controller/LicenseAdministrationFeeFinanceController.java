package atraintegratedsystems.licenses.controller;

import atraintegratedsystems.licenses.model.LicenseAdminFeesExtension;
import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.service.LicenseAdminFeesFinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LicenseAdministrationFeeFinanceController {
    @Autowired
    private LicenseAdminFeesFinanceService licenseAdminFeesFinanceService;

    @GetMapping("/licenses/finance/extension/admin_fees_extension/license_admin_fees")
    public String showApplicationProfile(Model model) {
        List<LicenseAdminFeesExtension> profiles = licenseAdminFeesFinanceService.getAllAdminFeesExtension() ;
        model.addAttribute("profiles", profiles);
        return "licenses/finance/extension/admin_fees_extension/license_admin_fees";
    }
}
