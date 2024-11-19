package atraintegratedsystems.licenses.controller;
import atraintegratedsystems.licenses.dto.LicenseApplicantDTO;
import atraintegratedsystems.licenses.dto.LicenseApprovalDTO;
import atraintegratedsystems.licenses.model.LicenseApplicant;
import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.service.LicenseApplicantFinanceService;
import atraintegratedsystems.licenses.service.LicenseApplicantService;
import atraintegratedsystems.licenses.service.LicenseApprovalService;
import atraintegratedsystems.licenses.service.LicenseTypeService;
import atraintegratedsystems.utils.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
public class LicenseApplicantApprovalController {

    @Autowired
    private LicenseApprovalService  licenseApprovalService;

    @Autowired
    private LicenseApplicantService licenseApplicantService;

    @Autowired
    private LicenseTypeService licenseTypeService;

    @GetMapping("/licenses/license/approval/license_applicants_approval_list")
    public String showApplicationProfile(Model model) {
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        List<LicenseApplicant> profiles = licenseApprovalService.getAllpaid();
        model.addAttribute("profiles", profiles);
        return "licenses/license/approval/license_applicants_approval_list";
    }

    @GetMapping("/licenses/license/approval/license_applicants_approval_list/add")
    public String PaymentConfirmationAdd(Model model){
        model.addAttribute("licenseApplicantDTO",new LicenseApplicantDTO());
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        return "licenses/license/approval/license_applicants_approval";
    }


    @GetMapping("/licenses/license/approval/license_applicants_approval_list/update/{id}")
    public String updateApplicantGet(@PathVariable Long id, Model model) throws Exception {
        // Fetch LicenseApplicant and validate ID
        LicenseApplicant licenseApplicant = licenseApplicantService.getApplicantById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid applicant ID: " + id));

        // Map LicenseApplicant to LicenseApplicantDTO
        LicenseApplicantDTO licenseApplicantDTO = new LicenseApplicantDTO();
        licenseApplicantDTO.setId(licenseApplicant.getId());
        licenseApplicantDTO.setLicenseTypeId(licenseApplicant.getLicenseType().getId());
        // Populate other fields if needed...
        LicenseApproval licenseApproval = licenseApprovalService.getByApplicantId(licenseApplicant.getId())
                .orElse(null); // Use Optional to handle null gracefully

        // Map LicenseApproval to LicenseApprovalDTO (if found)
        LicenseApprovalDTO licenseApprovalDTO = new LicenseApprovalDTO();
        if (licenseApproval != null) {
            licenseApprovalDTO.setId(licenseApproval.getId());
            licenseApprovalDTO.setApprovalId(licenseApproval.getApprovalId());
            licenseApprovalDTO.setApprovalDate(licenseApproval.getApprovalDate());
            licenseApprovalDTO.setApprovalStatus(licenseApproval.getApprovalStatus());
            licenseApprovalDTO.setLicenseTypeId(licenseApproval.getLicenseType().getId());
            licenseApprovalDTO.setCurrencyType(licenseApproval.getCurrencyType());
            licenseApprovalDTO.setLicenseFees(licenseApproval.getLicenseFees());
            licenseApprovalDTO.setLicensePaymentOffice(licenseApproval.getLicensePaymentOffice());
            licenseApprovalDTO.setAdministrativeYearlyFees(licenseApproval.getAdministrativeYearlyFees());
            licenseApprovalDTO.setAdminstrivateYearlyFeesPaymentOffice(licenseApproval.getAdminstrivateYearlyFeesPaymentOffice());
            licenseApprovalDTO.setGuaranteeFees(licenseApproval.getGuaranteeFees());
            licenseApprovalDTO.setGuaranteeFeesPaymentOffice(licenseApproval.getGuaranteeFeesPaymentOffice());
            licenseApprovalDTO.setDatabaseYearlyMaintainanceFees(licenseApproval.getDatabaseYearlyMaintainanceFees());
            licenseApprovalDTO.setDatabaseYearlyMaintainanceFeesPaymentOffice(licenseApproval.getDatabaseYearlyMaintainanceFeesPaymentOffice());
            licenseApprovalDTO.setLicenseApplicantId(licenseApproval.getLicenseApplicant().getId());
        }

        // Add DTOs and additional data to the model
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        model.addAttribute("licenseApplicantDTO", licenseApplicantDTO);
        model.addAttribute("licenseApprovalDTO", licenseApprovalDTO);

        return "licenses/license/approval/license_applicants_approval";
    }








}






