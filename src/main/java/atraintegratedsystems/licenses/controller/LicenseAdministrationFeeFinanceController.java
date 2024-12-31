package atraintegratedsystems.licenses.controller;

import atraintegratedsystems.licenses.dto.LicenseAdminFeesExtensionDTO;
import atraintegratedsystems.licenses.dto.LicenseApprovalDTO;
import atraintegratedsystems.licenses.model.LicenseAdminFeesExtension;
import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.service.LicenseAdminFeesFinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class LicenseAdministrationFeeFinanceController {
    @Autowired
    private LicenseAdminFeesFinanceService licenseAdminFeesFinanceService;

    @GetMapping("/licenses/finance/extension/admin_fees_extension/license_admin_fees")
    public String showApplicationProfile(Model model) {
        List<LicenseAdminFeesExtension> profiles = licenseAdminFeesFinanceService.getAllAdminFeesExtension() ;
        model.addAttribute("profiles", profiles);
        return "licenses/finance/extension/admin_fees_extension/license_admin_fees";
    }


    @GetMapping("/licenses/finance/extension/admin_fees_extension/license_admin_fees/print/{id}")
    public String GetTariff(@PathVariable Long id, Model model){
        LicenseAdminFeesExtension licenseAdminFeesExtension = licenseAdminFeesFinanceService.findById(id);
        LicenseAdminFeesExtensionDTO licenseAdminFeesExtensionDTO = new LicenseAdminFeesExtensionDTO();
        // Map fields from licenseApplicant to licenseApplicantDTO
        licenseAdminFeesExtensionDTO.setId(licenseAdminFeesExtension.getId());
        licenseAdminFeesExtensionDTO.setLicenseApprovalId(licenseAdminFeesExtension.getLicenseApproval().getId());
        licenseAdminFeesExtensionDTO.setLicenseAppId(licenseAdminFeesExtension.getLicenseApproval().getApprovalId());
        licenseAdminFeesExtensionDTO.setLicenseCompanyName(licenseAdminFeesExtension.getLicenseApproval().getLicenseApplicant().getCompanyLicenseName());
        licenseAdminFeesExtensionDTO.setLicenseTypeName(licenseAdminFeesExtension.getLicenseApproval().getLicenseType().getName());
        licenseAdminFeesExtensionDTO.setExtStartDate(licenseAdminFeesExtension.getExtentStartDate());
        licenseAdminFeesExtensionDTO.setExtExpireDate(licenseAdminFeesExtension.getExtentExpDate());
        licenseAdminFeesExtensionDTO.setExtensionAdministrationFees(licenseAdminFeesExtension.getExtensionAdministrationFees());
        model.addAttribute("licenseAdminFeesExtensionDTO", licenseAdminFeesExtensionDTO);
        return "licenses/finance/extension/admin_fees_extension/license_admin_fees_print_tariff";
    }


}
