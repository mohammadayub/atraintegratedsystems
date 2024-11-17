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
        // Add all license types to the model
        model.addAttribute("licenseTypes", licenseTypeService.findAll());

        // Fetch applicant details using the native query and add them to the model
        List<Object[]> profiles = licenseApprovalService.getAllApplicantDetails();
        model.addAttribute("profiles", profiles);

        // Return the view name
        return "licenses/license/approval/license_applicants_approval_list";
    }


}
