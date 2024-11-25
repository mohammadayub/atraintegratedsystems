package atraintegratedsystems.licenses.controller;

import atraintegratedsystems.licenses.dto.LicenseApplicantApprovalDTO;
import atraintegratedsystems.licenses.dto.LicenseApplicantDTO;
import atraintegratedsystems.licenses.dto.LicenseApprovalDTO;
import atraintegratedsystems.licenses.model.LicenseApplicant;
import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.model.LicenseType;
import atraintegratedsystems.licenses.service.LicenseApplicantService;
import atraintegratedsystems.licenses.service.LicenseApprovalService;
import atraintegratedsystems.licenses.service.LicenseTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/licenses/license/approval")
@Slf4j
public class LicenseApplicantApprovalController {

    @Autowired
    private LicenseApprovalService licenseApprovalService;

    @Autowired
    private LicenseApplicantService licenseApplicantService;

    @Autowired
    private LicenseTypeService licenseTypeService;

    /**
     * Displays the list of license applicants with paid status.
     */
    @GetMapping("/license_applicants_approval_list")
    public String showApplicationProfile(Model model) {
        List<LicenseApplicantApprovalDTO> profiles = licenseApplicantService.getAllLicenseApplicantApprovalDetails();
        model.addAttribute("profiles", profiles);
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        return "licenses/license/approval/license_applicants_approval_list";
    }

    /**
     * Displays the add approval form.
     */
    @GetMapping("/license_applicants_approval_list/add")
    public String paymentConfirmationAdd(Model model) {
        model.addAttribute("licenseApplicantDTO", new LicenseApplicantDTO());
        model.addAttribute("licenseApprovalDTO", new LicenseApprovalDTO());
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        model.addAttribute("licenseApplicants", licenseApplicantService.getAllApplicants());
        return "licenses/license/approval/license_applicants_approval";
    }

    /**
     * Handles saving a license approval.
     */
    @PostMapping("/license_applicants_approval")
    public String saveApproval(@ModelAttribute("licenseApprovalDTO") LicenseApprovalDTO dto, RedirectAttributes redirectAttributes) {
        log.info("LicenseApprovalDTO: {}", dto);  // Check the entire DTO to ensure ID is present
        try {
            licenseApprovalService.saveApproval(dto);
            redirectAttributes.addFlashAttribute("successMessage", "Approval saved successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error saving approval: " + e.getMessage());
            log.error("Error saving license approval: ", e);
        }
        return "redirect:/licenses/license/approval/license_applicants_approval_list";
    }

    /**
     * Displays the update form for an applicant's approval.
     */
    @GetMapping("/license_applicants_approval_list/update/{id}")
    public String updateApplicantGet(@PathVariable Long id, Model model) {
        try {
            // Fetch LicenseApplicant and validate ID
            LicenseApplicant licenseApplicant = licenseApplicantService.getApplicantById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid applicant ID: " + id));

            // Map LicenseApplicant to DTO
             LicenseApplicantApprovalDTO licenseApplicantDTO= new LicenseApplicantApprovalDTO();
             licenseApplicantDTO.setId(licenseApplicant.getId());
             licenseApplicantDTO.setLicenseTypeId(licenseApplicant.getLicenseType().getId());
             licenseApplicantDTO.setCompanyLicenseName(licenseApplicant.getCompanyLicenseName());

             //License Approval Section
             LicenseApproval licenseApproval = new LicenseApproval();
             licenseApplicantDTO.setAppId(licenseApproval.getId());
             licenseApplicantDTO.setApprovalId(licenseApproval.getApprovalId());




            // Add DTOs and other data to the model
            model.addAttribute("licenseApplicantDTO", licenseApplicantDTO);
            model.addAttribute("licenseTypes", licenseTypeService.findAll());
            model.addAttribute("licenseApplicants", licenseApplicantService.getAllApplicants());
            model.addAttribute("licenseTypeId", licenseApplicantDTO.getLicenseTypeId());

        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "redirect:/licenses/license/approval/license_applicants_approval_list";
        }

        return "licenses/license/approval/license_applicants_approval";
    }
}
