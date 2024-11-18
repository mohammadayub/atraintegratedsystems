package atraintegratedsystems.licenses.controller;
import atraintegratedsystems.licenses.dto.LicenseApplicantDTO;
import atraintegratedsystems.licenses.model.LicenseApplicant;
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
        LicenseApplicant licenseApplicant = licenseApplicantService.getApplicantById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid applicant ID: " + id));
        LicenseApplicantDTO licenseApplicantDTO = new LicenseApplicantDTO();
        // Map fields from licenseApplicant to licenseApplicantDTO
        licenseApplicantDTO.setId(licenseApplicant.getId());
        licenseApplicantDTO.setLicenseTypeId(licenseApplicant.getLicenseType().getId());
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        model.addAttribute("licenseApplicantDTO", licenseApplicantDTO);
        return "licenses/license/approval/license_applicants_approval";
    }






}






