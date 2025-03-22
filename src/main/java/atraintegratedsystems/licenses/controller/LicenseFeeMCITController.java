package atraintegratedsystems.licenses.controller;
import atraintegratedsystems.licenses.dto.LicenseApprovalDTO;
import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.service.LicenseFeeMCITService;
import atraintegratedsystems.licenses.service.LicenseTypeService;
import atraintegratedsystems.utils.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Controller
public class LicenseFeeMCITController {

    @Autowired
    private LicenseTypeService licenseTypeService;

    @Autowired
    private LicenseFeeMCITService licenseFeeMCITService;

    @PreAuthorize("hasRole('ROLE_MINISTRY') or hasRole('ROLE_ADMIN')")
    @GetMapping("/licenses/finance/mcit/license_fee_list")
    public String showApplicationProfile(Model model) {
        List<LicenseApproval> profiles = licenseFeeMCITService.getAllApprovalApplicants();
        model.addAttribute("profiles", profiles);
        return "licenses/finance/license_finance/mcit/license_fee_list";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MINISTRY')")
    @GetMapping("/licenses/finance/mcit/license_fee_list/add")
    public String PaymentConfirmationAdd(Model model){
        model.addAttribute("licenseApprovalDTO",new LicenseApprovalDTO());
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        return "licenses/finance/mcit/license_fee_payment_confirmation";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MINISTRY')")
    @PostMapping("/licenses/finance/mcit/license_fee_list/add")
    public String updateLicenseApproval(@ModelAttribute LicenseApprovalDTO licenseApprovalDTO) {
        // Fetch the existing entity from the database
        LicenseApproval existingLicenseApproval = licenseFeeMCITService.findById(licenseApprovalDTO.getId());
        // Update only the required fields
        DateConverter dateConverter= new DateConverter();
        LocalDate licenseFeeEntryVoucherDate = dateConverter.jalaliToGregorian(licenseApprovalDTO.getLicenseFeeEntryVoucherDate().getYear(), licenseApprovalDTO.getLicenseFeeEntryVoucherDate().getMonthValue(),licenseApprovalDTO.getLicenseFeeEntryVoucherDate().getDayOfMonth());
        existingLicenseApproval.setLicenseFeeEntryVoucherDate(licenseFeeEntryVoucherDate);
        LocalDate licenseFeeVoucherSubmissionDate = dateConverter.jalaliToGregorian(licenseApprovalDTO.getLicenseFeeBankVoucherSubmissionDate().getYear(),licenseApprovalDTO.getLicenseFeeBankVoucherSubmissionDate().getMonthValue(),licenseApprovalDTO.getLicenseFeeBankVoucherSubmissionDate().getDayOfMonth());
        existingLicenseApproval.setLicenseFeeBankVoucherSubmissionDate(licenseFeeVoucherSubmissionDate);
        existingLicenseApproval.setLicenseFeeBankVoucherNo(licenseApprovalDTO.getLicenseFeeBankVoucherNo());
        existingLicenseApproval.setLicenseFeePaymentStatus(licenseApprovalDTO.getLicenseFeePaymentStatus());
        // Set applicationFeeEnteredBy to the logged-in user
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String enteredBy = (principal instanceof UserDetails) ? ((UserDetails) principal).getUsername() : "Unknown";
        existingLicenseApproval.setLicenseFeesEnteredBy(enteredBy);

        // If applicationFeeCreatedDate is null, set it to the current time
        if (existingLicenseApproval.getLicenseFeesCreatedDate() == null) {
            existingLicenseApproval.setLicenseFeesCreatedDate(LocalDateTime.now());
        }
        // Save the updated entity
        licenseFeeMCITService.save(existingLicenseApproval);
        return "redirect:/licenses/finance/mcit/license_fee_list";
    }


    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MINISTRY')")
    @GetMapping("/licenses/finance/mcit/license_fee_list/update/{id}")
    public String UpdateMcitFee(@PathVariable Long id, Model model){
        LicenseApproval licenseApproval = licenseFeeMCITService.findById(id);
        LicenseApprovalDTO licenseApprovalDTO = new LicenseApprovalDTO();
        // Map fields from licenseApplicant to licenseApplicantDTO
        licenseApprovalDTO.setId(licenseApproval.getId());
        licenseApprovalDTO.setApprovalId(licenseApproval.getApprovalId());
        licenseApprovalDTO.setApplicantLicenseCompanyName(licenseApproval.getLicenseApplicant().getCompanyLicenseName());
        licenseApprovalDTO.setLicenseTypeName(licenseApproval.getLicenseType().getName());
        licenseApprovalDTO.setApprovalDate(licenseApproval.getApprovalDate());
        licenseApprovalDTO.setBoardDecisionNumber(licenseApproval.getBoardDecisionNumber());
        licenseApprovalDTO.setApprovalStatus(licenseApproval.getApprovalStatus());
        licenseApprovalDTO.setCurrencyType(licenseApproval.getCurrencyType());
        licenseApprovalDTO.setLicenseFees(licenseApproval.getLicenseFees());
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        model.addAttribute("licenseApprovalDTO", licenseApprovalDTO);
        return "licenses/finance/license_finance/mcit/license_fee_payment_confirmation";

    }


    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MINISTRY')")
    @GetMapping("/licenses/finance/mcit/license_fee_list/print/{id}")
    public String GetTariff(@PathVariable Long id, Model model){
        LicenseApproval licenseApproval = licenseFeeMCITService.findById(id);
        LicenseApprovalDTO licenseApprovalDTO = new LicenseApprovalDTO();
        // Map fields from licenseApplicant to licenseApplicantDTO
        licenseApprovalDTO.setId(licenseApproval.getId());
        licenseApprovalDTO.setApprovalId(licenseApproval.getApprovalId());
        licenseApprovalDTO.setApplicantLicenseCompanyName(licenseApproval.getLicenseApplicant().getCompanyLicenseName());
        licenseApprovalDTO.setLicenseTypeName(licenseApproval.getLicenseType().getName());
        licenseApprovalDTO.setApprovalDate(licenseApproval.getApprovalDate());
        licenseApprovalDTO.setBoardDecisionNumber(licenseApproval.getBoardDecisionNumber());
        licenseApprovalDTO.setApprovalStatus(licenseApproval.getApprovalStatus());
        licenseApprovalDTO.setCurrencyType(licenseApproval.getCurrencyType());
        licenseApprovalDTO.setLicenseFees(licenseApproval.getLicenseFees());
        licenseApprovalDTO.setLicenseValidity(licenseApproval.getLicenseApplicant().getValidity());
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        model.addAttribute("licenseApprovalDTO", licenseApprovalDTO);
        return "licenses/finance/license_finance/mcit/license_fee_print_tariff";
    }


}
