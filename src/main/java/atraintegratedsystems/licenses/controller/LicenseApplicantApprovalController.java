package atraintegratedsystems.licenses.controller;
import atraintegratedsystems.licenses.dto.LicenseApplicantApprovalDTO;
import atraintegratedsystems.licenses.dto.LicenseApplicantDTO;
import atraintegratedsystems.licenses.dto.LicenseApprovalDTO;
import atraintegratedsystems.licenses.model.LicenseApplicant;
import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.service.LicenseApplicantService;
import atraintegratedsystems.licenses.service.LicenseApprovalService;
import atraintegratedsystems.licenses.service.LicenseTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

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
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_APPROVAL')")
    @GetMapping("/license_applicants_approval_list")
    public String showApplicationProfile(Model model) {
        List<LicenseApplicantApprovalDTO> profiles = licenseApplicantService.getAllLicenseApplicantApprovalDetails();
        model.addAttribute("profiles", profiles);
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        return "licenses/license/approval/license_applicants_approval_list";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_APPROVAL')")
    @GetMapping("/license_applicants_approval_list/add")
    public String paymentConfirmationAdd(Model model) {
        model.addAttribute("licenseApplicantDTO", new LicenseApplicantDTO());
        model.addAttribute("licenseApprovalDTO", new LicenseApprovalDTO());
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        model.addAttribute("licenseApplicants", licenseApplicantService.getAllApplicants());
        return "licenses/license/approval/license_applicants_approval";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_APPROVAL')")
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
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_APPROVAL')")
    @GetMapping("/license_applicants_approval_list/update/{id}")
    public String updateApplicantGet(@PathVariable Long id, Model model) {
        try {
            // Fetch LicenseApplicant by ID
            LicenseApplicant licenseApplicant = licenseApplicantService.getApplicantById(id)
                    .orElseThrow(() -> new IllegalArgumentException("No License Applicant found with ID: " + id));

            // Ensure the LicenseApplicant has a valid ID
            if (licenseApplicant.getId() == null) {
                throw new IllegalArgumentException("License Applicant ID is required but not found.");
            }

            // Map LicenseApplicant to LicenseApplicantApprovalDTO
            LicenseApplicantApprovalDTO licenseApplicantDTO = new LicenseApplicantApprovalDTO();
            licenseApplicantDTO.setId(licenseApplicant.getId());
            licenseApplicantDTO.setReqId(licenseApplicant.getReqId());
            licenseApplicantDTO.setReqDate(licenseApplicant.getReqDate());
            licenseApplicantDTO.setLicenseTypeId(
                    licenseApplicant.getLicenseType() != null ? licenseApplicant.getLicenseType().getId() : null
            );
            licenseApplicantDTO.setLicenseTypeName(
                    licenseApplicant.getLicenseType() != null ? licenseApplicant.getLicenseType().getName() : ""
            );
            licenseApplicantDTO.setFinanceType(licenseApplicant.getFinanceType());
            licenseApplicantDTO.setCompanyLicenseName(licenseApplicant.getCompanyLicenseName());
            licenseApplicantDTO.setLicenseNo(licenseApplicant.getLicenseNo());
            licenseApplicantDTO.setApplicationFees(licenseApplicant.getApplicationFees());
            licenseApplicantDTO.setValidity(licenseApplicant.getValidity());
            licenseApplicantDTO.setEntryVoucherDate(licenseApplicant.getEntryApplicationFeeVoucherDate());
            licenseApplicantDTO.setBankVoucher(licenseApplicant.getBankVoucher());
            licenseApplicantDTO.setPaymentStatus(licenseApplicant.getPaymentStatus());

            // Fetch LicenseApproval by applicant ID using LEFT OUTER JOIN
            LicenseApproval licenseApproval = licenseApprovalService.getApprovalByApplicantId(id)
                    .orElse(new LicenseApproval()); // Use empty object if no approval is found

            // Map LicenseApproval data
            licenseApplicantDTO.setAppId(licenseApproval.getId());
            licenseApplicantDTO.setApprovalId(licenseApproval.getApprovalId());
            licenseApplicantDTO.setApprovalDate(licenseApproval.getApprovalDate());
            licenseApplicantDTO.setApprovalStatus(licenseApproval.getApprovalStatus());
            licenseApplicantDTO.setBoardDecisions(licenseApproval.getBoardDecisions());
            licenseApplicantDTO.setBoardDecisionNumber(licenseApproval.getBoardDecisionNumber());
            licenseApplicantDTO.setCurrencyType(licenseApproval.getCurrencyType());
            licenseApplicantDTO.setLicenseFees(licenseApproval.getLicenseFees());
            licenseApplicantDTO.setLicensePaymentOffice(licenseApproval.getLicensePaymentOffice());
            licenseApplicantDTO.setAdministrativeYearlyFees(licenseApproval.getAdministrativeYearlyFees());
            licenseApplicantDTO.setAdminstrivateYearlyFeesPaymentOffice(licenseApproval.getAdminstrivateYearlyFeesPaymentOffice());
            licenseApplicantDTO.setGuaranteeFeesType(licenseApproval.getGuaranteeFeesType());
            licenseApplicantDTO.setGuaranteeFees(licenseApproval.getGuaranteeFees());
            licenseApplicantDTO.setGuaranteeFeesPaymentOffice(licenseApproval.getGuaranteeFeesPaymentOffice());
            licenseApplicantDTO.setDatabaseYearlyMaintainanceFees(licenseApproval.getDatabaseYearlyMaintainanceFees());
            licenseApplicantDTO.setDatabaseYearlyMaintainanceFeesPaymentOffice(licenseApproval.getDatabaseYearlyMaintainanceFeesPaymentOffice());

            // Set LicenseApplicant ID (from LicenseApplicant directly)
            licenseApplicantDTO.setLicenseApplicantId(licenseApplicant.getId());  // <-- Ensure the ID is set here
            // Add DTO and other data to the model
            model.addAttribute("licenseApplicantDTO", licenseApplicantDTO);
            model.addAttribute("licenseTypes", licenseTypeService.findAll());
            model.addAttribute("licenseTypeId", licenseApplicantDTO.getLicenseTypeId());

        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "redirect:/licenses/license/approval/license_applicants_approval_list";
        }

        return "licenses/license/approval/license_applicants_approval";
    }




}
