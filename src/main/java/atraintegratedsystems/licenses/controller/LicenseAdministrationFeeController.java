package atraintegratedsystems.licenses.controller;


import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.service.LicenseAdministrationFeeService;
import atraintegratedsystems.licenses.service.LicenseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LicenseAdministrationFeeController {

    @Autowired
    private LicenseTypeService licenseTypeService;

    @Autowired
    private LicenseAdministrationFeeService licenseAdministrationFeeService;


    @GetMapping("/licenses/finance/license_finance/administration_fees/license_administration_fee_list")
    public String showApplicationProfile(Model model) {
        List<LicenseApproval> profiles = licenseAdministrationFeeService.getAllApprovalApplicants();
        model.addAttribute("profiles", profiles);
        return "licenses/finance/license_finance/administration_fees/license_administration_fee_list";
    }
}
