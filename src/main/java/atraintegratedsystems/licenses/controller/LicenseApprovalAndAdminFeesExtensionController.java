package atraintegratedsystems.licenses.controller;


import atraintegratedsystems.licenses.dto.LicenseAdminFeesExtensionDTO;
import atraintegratedsystems.licenses.dto.LicenseApprovalDTO;
import atraintegratedsystems.licenses.dto.LicenseDatabaseFeesExtensionDTO;
import atraintegratedsystems.licenses.service.LicenseAdminFeesExtensionService;
import atraintegratedsystems.licenses.service.LicenseApprovalService;
import atraintegratedsystems.licenses.service.LicenseDatabaseFeesExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class LicenseApprovalAndAdminFeesExtensionController {



    @Autowired
    private LicenseApprovalService approvalService;

    @Autowired
    private LicenseAdminFeesExtensionService extensionService;

    @GetMapping("/licenses/license/extension/license_admin_fees_profile")
    public String getLicenseDetails(Model model) {
        List<LicenseApprovalDTO> approvals = approvalService.getAllForAdminFeesExtension();
        List<LicenseAdminFeesExtensionDTO> extensions = extensionService.getAllExtensions();
        model.addAttribute("approvals", approvals);
        model.addAttribute("extensions", extensions);
        return "licenses/license/extension/license_admin_fees_profile"; // Thymeleaf template
    }

    @PostMapping("/licenses/license/extension/license_admin_fees_profile/extension")
    public String saveExtension(@ModelAttribute LicenseAdminFeesExtensionDTO extensionDTO) {
        extensionService.saveExtension(extensionDTO);
        return "redirect:/licenses/license/extension/license_admin_fees_profile";
    }

}
