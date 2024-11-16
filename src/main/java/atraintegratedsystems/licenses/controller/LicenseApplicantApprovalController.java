package atraintegratedsystems.licenses.controller;

import atraintegratedsystems.licenses.model.LicenseApplicant;
import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.service.LicenseApprovalService;
import atraintegratedsystems.licenses.service.LicenseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LicenseApplicantApprovalController {

    @Autowired
    private LicenseApprovalService licenseApprovalService;

    @Autowired
    private LicenseTypeService licenseTypeService;

    @GetMapping("/licenses/license/approval/license_applicants_approval_list")
    public String showApprovalProfile(Model model) {
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        List<LicenseApproval> profiles = licenseApprovalService.getAllApplicantsforApproval();
        model.addAttribute("profiles", profiles);
        return "licenses/license/approval/license_applicants_approval_list";
    }


}
