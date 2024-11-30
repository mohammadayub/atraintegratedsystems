package atraintegratedsystems.licenses.controller;


import atraintegratedsystems.licenses.model.LicenseApplicant;
import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.service.LicenseFeeMCITService;
import atraintegratedsystems.licenses.service.LicenseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LicenseFeeMCITController {


    @Autowired
    private LicenseTypeService licenseTypeService;

    @Autowired
    private LicenseFeeMCITService licenseFeeMCITService;

    @GetMapping("/licenses/finance/mcit/license_fee_list")
    public String showApplicationProfile(Model model) {
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        List<LicenseApproval> profiles = licenseFeeMCITService.getAllApprovalApplicants();
        model.addAttribute("profiles", profiles);
        return "licenses/finance/mcit/license_fee_list";
    }
}
