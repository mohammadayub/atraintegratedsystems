package atraintegratedsystems.licenses.controller;
import atraintegratedsystems.licenses.dto.LicenseApprovalDTO;
import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.service.LicenseFeeMCITService;
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
public class LicenseFeeMCITController {

    @Autowired
    private LicenseTypeService licenseTypeService;

    @Autowired
    private LicenseFeeMCITService licenseFeeMCITService;

    @GetMapping("/licenses/finance/mcit/license_fee_list")
    public String showApplicationProfile(Model model) {
        List<LicenseApproval> profiles = licenseFeeMCITService.getAllApprovalApplicants();
        model.addAttribute("profiles", profiles);
        return "licenses/finance/license_finance/mcit/license_fee_list";
    }

    @GetMapping("/licenses/finance/mcit/license_fee_list/add")
    public String PaymentConfirmationAdd(Model model){
        model.addAttribute("licenseApprovalDTO",new LicenseApprovalDTO());
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        return "licenses/finance/mcit/license_fee_payment_confirmation";
    }

    @PostMapping("/licenses/finance/mcit/license_fee_list/add")
    public String updateLicenseApproval(@ModelAttribute LicenseApprovalDTO licenseApprovalDTO) {
        // Fetch the existing entity from the database
        LicenseApproval existingLicenseApproval = licenseFeeMCITService.findById(licenseApprovalDTO.getId());

        // Update only the required fields
        existingLicenseApproval.setLicenseFeeEntryVoucherDate(licenseApprovalDTO.getLicenseFeeEntryVoucherDate());
        existingLicenseApproval.setLicenseFeeBankVoucherNo(licenseApprovalDTO.getLicenseFeeBankVoucherNo());
        existingLicenseApproval.setLicenseFeePaymentStatus(licenseApprovalDTO.getLicenseFeePaymentStatus());

        // Save the updated entity
        licenseFeeMCITService.save(existingLicenseApproval);

        return "redirect:/licenses/finance/mcit/license_fee_list";
    }

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
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        model.addAttribute("licenseApprovalDTO", licenseApprovalDTO);
        return "licenses/finance/license_finance/mcit/license_fee_print_tariff";
    }


}
