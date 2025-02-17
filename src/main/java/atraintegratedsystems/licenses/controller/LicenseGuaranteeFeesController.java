package atraintegratedsystems.licenses.controller;

import atraintegratedsystems.licenses.dto.LicenseApprovalDTO;
import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.service.LicenseAdministrationFeeService;
import atraintegratedsystems.licenses.service.LicenseGuaranteeFeeService;
import atraintegratedsystems.licenses.service.LicenseTypeService;
import atraintegratedsystems.utils.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class LicenseGuaranteeFeesController {

    @Autowired
    private LicenseTypeService licenseTypeService;

    @Autowired
    private LicenseGuaranteeFeeService licenseGuaranteeFeeService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_FINANCE')")
    @GetMapping("/licenses/finance/license_finance/guarantee_fees/license_guarantee_fee_list")
    public String showApplicationProfile(Model model) {
        List<LicenseApproval> profiles = licenseGuaranteeFeeService.getAllApplicantsUnPaidGuarantee();
        model.addAttribute("profiles", profiles);
        return "/licenses/finance/license_finance/guarantee_fees/license_guarantee_fee_list";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_FINANCE')")
    @GetMapping("/licenses/finance/license_finance/guarantee_fees/license_guarantee_fee_list/add")
    public String PaymentConfirmationAdd(Model model){
        model.addAttribute("licenseApprovalDTO",new LicenseApprovalDTO());
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        return "/licenses/finance/license_finance/guarantee_fees/license_guarantee_fee_payment_confirmation";
    }


    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_FINANCE')")
    @PostMapping("/licenses/finance/license_finance/guarantee_fees/license_guarantee_fee_list/add")
    public String updateLicenseApproval(@ModelAttribute LicenseApprovalDTO licenseApprovalDTO) {
        // Fetch the existing entity from the database
        LicenseApproval existingLicenseApproval = licenseGuaranteeFeeService.findById(licenseApprovalDTO.getId());

        DateConverter dateConverter = new DateConverter();
        LocalDate guaranteeEntryVoucherDate = dateConverter.jalaliToGregorian(
                licenseApprovalDTO.getGuaranteeFeeEntryVoucherDate().getYear(),
                licenseApprovalDTO.getGuaranteeFeeEntryVoucherDate().getMonthValue(),
                licenseApprovalDTO.getGuaranteeFeeEntryVoucherDate().getDayOfMonth()
        );
        // Update only the required fields
        existingLicenseApproval.setGuaranteeFeeEntryVoucherDate(guaranteeEntryVoucherDate);

        LocalDate guaranteeEntrySubmissionVoucherDate = dateConverter.jalaliToGregorian(
                licenseApprovalDTO.getGuaranteeFeeBankVoucherSubmissionDate().getYear(),
                licenseApprovalDTO.getGuaranteeFeeBankVoucherSubmissionDate().getMonthValue(),
                licenseApprovalDTO.getGuaranteeFeeBankVoucherSubmissionDate().getDayOfMonth()
        );
        existingLicenseApproval.setGuaranteeFeeBankVoucherSubmissionDate(guaranteeEntrySubmissionVoucherDate);
        existingLicenseApproval.setGuaranteeFeeBankVoucherNo(licenseApprovalDTO.getGuaranteeFeeBankVoucherNo());
        existingLicenseApproval.setGuaranteeFeePaymentStatus(licenseApprovalDTO.getGuaranteeFeePaymentStatus());

        // Save the updated entity
        licenseGuaranteeFeeService.save(existingLicenseApproval);
        return "redirect:/licenses/finance/license_finance/guarantee_fees/license_guarantee_fee_list";
    }


    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_FINANCE')")
    @GetMapping("/licenses/finance/license_finance/guarantee_fees/license_guarantee_fee_list/update/{id}")
    public String UpdateGuaranteeFee(@PathVariable Long id, Model model){
        LicenseApproval licenseApproval = licenseGuaranteeFeeService.findById(id);
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
        licenseApprovalDTO.setGuaranteeFees(licenseApproval.getGuaranteeFees());
        licenseApprovalDTO.setGuaranteeFeeEntryVoucherDate(licenseApproval.getGuaranteeFeeEntryVoucherDate());
        licenseApprovalDTO.setGuaranteeFeeBankVoucherNo(licenseApproval.getGuaranteeFeeBankVoucherNo());
        licenseApprovalDTO.setGuaranteeFeePaymentStatus(licenseApproval.getGuaranteeFeePaymentStatus());
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        model.addAttribute("licenseApprovalDTO", licenseApprovalDTO);
        return "licenses/finance/license_finance/guarantee_fees/license_guarantee_fee_payment_confirmation";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_FINANCE')")
    @GetMapping("/licenses/finance/license_finance/guarantee_fees/license_guarantee_fee_list/print/{id}")
    public String GetTariff(@PathVariable Long id, Model model){
        LicenseApproval licenseApproval = licenseGuaranteeFeeService.findById(id);
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
        licenseApprovalDTO.setLicenseValidity(licenseApproval.getLicenseApplicant().getValidity());
        licenseApprovalDTO.setGuaranteeFees(licenseApproval.getGuaranteeFees());
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        model.addAttribute("licenseApprovalDTO", licenseApprovalDTO);
        return "licenses/finance/license_finance/guarantee_fees/license_guarantee_fee_print_tariff";
    }
}
