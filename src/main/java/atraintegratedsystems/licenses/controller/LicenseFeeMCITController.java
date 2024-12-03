package atraintegratedsystems.licenses.controller;

import atraintegratedsystems.licenses.dto.LicenseApplicantDTO;
import atraintegratedsystems.licenses.dto.LicenseApprovalDTO;
import atraintegratedsystems.licenses.model.LicenseApplicant;
import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.service.LicenseFeeMCITService;
import atraintegratedsystems.licenses.service.LicenseTypeService;
import atraintegratedsystems.utils.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
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

    @GetMapping("/licenses/finance/mcit/license_fee_list/print/{id}")
    public String GetTariff(@PathVariable Long id, Model model){
        LicenseApproval licenseApproval = licenseFeeMCITService.getLicenseApprovalId(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid license approval ID: " + id));
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
