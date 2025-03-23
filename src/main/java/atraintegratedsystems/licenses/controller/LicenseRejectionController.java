package atraintegratedsystems.licenses.controller;
import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.service.LicenseRejectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
@Controller
public class LicenseRejectionController {
    @Autowired
    private LicenseRejectionService licenseRejectionService;
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_ADMIN') or hasRole('ROLE_LICENSE_APPROVAL')")
    @GetMapping("/licenses/license/rejection/license_applicants_rejection_list")
    public String showRejectionProfile(Model model) {
        List<LicenseApproval> profiles = licenseRejectionService.getAllRejection();
        model.addAttribute("profiles", profiles);
        return "licenses/license/rejection/license_applicants_rejection_list";
    }
}
