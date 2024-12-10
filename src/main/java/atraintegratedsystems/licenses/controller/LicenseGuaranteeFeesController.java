package atraintegratedsystems.licenses.controller;

import atraintegratedsystems.licenses.dto.LicenseApprovalDTO;
import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.service.LicenseAdministrationFeeService;
import atraintegratedsystems.licenses.service.LicenseGuaranteeFeeService;
import atraintegratedsystems.licenses.service.LicenseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class LicenseGuaranteeFeesController {

    @Autowired
    private LicenseTypeService licenseTypeService;

    @Autowired
    private LicenseGuaranteeFeeService licenseGuaranteeFeeService;

    @GetMapping("/licenses/finance/license_finance/guarantee_fees/license_guarantee_fee_list")
    public String showApplicationProfile(Model model) {
        List<LicenseApproval> profiles = licenseGuaranteeFeeService.getAllApplicantsUnPaidGuarantee();
        model.addAttribute("profiles", profiles);
        return "/licenses/finance/license_finance/guarantee_fees/license_guarantee_fee_list";
    }

    @GetMapping("/licenses/finance/license_finance/guarantee_fees/license_guarantee_fee_list/add")
    public String PaymentConfirmationAdd(Model model){
        model.addAttribute("licenseApprovalDTO",new LicenseApprovalDTO());
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        return "/licenses/finance/license_finance/guarantee_fees/license_guarantee_fee_payment_confirmation";
    }


    @PostMapping("/licenses/finance/license_finance/guarantee_fees/license_guarantee_fee_list/add")
    public String updateLicenseApproval(@ModelAttribute LicenseApprovalDTO licenseApprovalDTO) {
        // Fetch the existing entity from the database
        LicenseApproval existingLicenseApproval = licenseGuaranteeFeeService.findById(licenseApprovalDTO.getId());

        // Update only the required fields
        existingLicenseApproval.setGuaranteeFeeEntryVoucherDate(licenseApprovalDTO.getGuaranteeFeeEntryVoucherDate());
        existingLicenseApproval.setGuaranteeFeeBankVoucherNo(licenseApprovalDTO.getGuaranteeFeeBankVoucherNo());
        existingLicenseApproval.setGuaranteeFeePaymentStatus(licenseApprovalDTO.getGuaranteeFeePaymentStatus());

        // Save the updated entity
        licenseGuaranteeFeeService.save(existingLicenseApproval);
        return "redirect:/licenses/finance/license_finance/guarantee_fees/license_guarantee_fee_list";
    }


    @GetMapping("/licenses/finance/license_finance/guarantee_fees/license_guarantee_fee_list/update/{id}")
    public String UpdateMcitFee(@PathVariable Long id, Model model){
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
        licenseApprovalDTO.setAdministrativeYearlyFees(licenseApproval.getAdministrativeYearlyFees());
        licenseApprovalDTO.setAdministrationFeeEntryVoucherDate(licenseApproval.getAdministrationFeeEntryVoucherDate());
        licenseApprovalDTO.setAdministrationFeeBankVoucherNo(licenseApproval.getAdministrationFeeBankVoucherNo());
        licenseApprovalDTO.setAdministrationFeePaymentStatus(licenseApproval.getAdministrationFeePaymentStatus());
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        model.addAttribute("licenseApprovalDTO", licenseApprovalDTO);
        return "/licenses/finance/license_finance/guarantee_fees/license_guarantee_fee_payment_confirmation";
    }

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
        licenseApprovalDTO.setAdministrativeYearlyFees(licenseApproval.getAdministrativeYearlyFees());
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        model.addAttribute("licenseApprovalDTO", licenseApprovalDTO);
        return "/licenses/finance/license_finance/guarantee_fees/license_guarantee_fee_print_tariff";
    }
}
