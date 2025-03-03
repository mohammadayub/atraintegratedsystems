package atraintegratedsystems.licenses.controller;

import atraintegratedsystems.licenses.dto.LicenseApprovalDTO;
import atraintegratedsystems.licenses.dto.LicenseDatabaseFeesExtensionDTO;
import atraintegratedsystems.licenses.service.LicenseApprovalService;
import atraintegratedsystems.licenses.service.LicenseDatabaseFeesExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class LicenseApprovalAndDatabaseFeesExtensionController {

    @Autowired
    private LicenseApprovalService approvalService;

    @Autowired
    private LicenseDatabaseFeesExtensionService extensionService;
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE') or hasRole('ROLE_LICENSE_COMPLETION_PROFILE')")
    @GetMapping("/licenses/license/extension/license_database_fees_profile")
    public String getLicenseDetails(Model model) {
        List<LicenseApprovalDTO> approvals = approvalService.getAllForDatabaseFeesExtension();
        List<LicenseDatabaseFeesExtensionDTO> extensions = extensionService.getAllExtensions();
        model.addAttribute("approvals", approvals);
        model.addAttribute("extensions", extensions);
        return "licenses/license/extension/license_database_fees_profile"; // Thymeleaf template
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE') or hasRole('ROLE_LICENSE_COMPLETION_PROFILE')")
    @PostMapping("/licenses/license/extension/license_database_fees_profile/update-status")
    public String updateExtendStatus(@RequestParam Long id, @RequestParam String extendStatus, RedirectAttributes redirectAttributes) {
        try {
            extensionService.updateExtendStatus(id, extendStatus);
            redirectAttributes.addFlashAttribute("message", "Extend status updated successfully for ID: " + id);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Failed to update extend status for ID: " + id);
        }
        return "redirect:/licenses/license/extension/license_database_fees_profile";
    }

}
