package atraintegratedsystems.licenses.controller;

import atraintegratedsystems.licenses.dto.LicenseAdminFeesExtensionDTO;
import atraintegratedsystems.licenses.model.LicenseAdminFeesExtension;
import atraintegratedsystems.licenses.service.LicenseAdminFeesExtensionFinanceService;
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
public class LicenseAdministrationFeeExtensionFinanceController {
    @Autowired
    private LicenseAdminFeesExtensionFinanceService licenseAdminFeesExtensionFinanceService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_ADMIN') or hasRole('ROLE_FINANCE')")
    @GetMapping("/licenses/finance/extension/admin_fees_extension/license_admin_fees")
    public String showApplicationProfile(Model model) {
        List<LicenseAdminFeesExtension> profiles = licenseAdminFeesExtensionFinanceService.getAllAdminFeesExtension() ;
        model.addAttribute("profiles", profiles);
        return "licenses/finance/extension/admin_fees_extension/license_admin_fees";
    }


    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_ADMIN') or hasRole('ROLE_FINANCE')")
    @GetMapping("/licenses/finance/extension/admin_fees_extension/license_admin_fees/add")
    public String PaymentConfirmationAdd(Model model){
        model.addAttribute("licenseAdminFeesExtensionDTO",new LicenseAdminFeesExtensionDTO());
        return "/licenses/finance/extension/admin_fees_extension/license_admin_fees_confirmation";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_ADMIN') or hasRole('ROLE_FINANCE')")
    @PostMapping("/licenses/finance/extension/admin_fees_extension/license_admin_fees/add")
    public String updateLicenseApproval(@ModelAttribute LicenseAdminFeesExtensionDTO licenseAdminFeesExtensionDTO) {
        // Fetch the existing entity from the database
        LicenseAdminFeesExtension existingLicenseAdminFeesExtension = licenseAdminFeesExtensionFinanceService.findById(licenseAdminFeesExtensionDTO.getId());
        DateConverter dateConverter = new DateConverter();
        LocalDate adminEntryVoucherDate = dateConverter.jalaliToGregorian(
                licenseAdminFeesExtensionDTO.getExtensionAdministrationFeeBankVoucherDate().getYear(),
                licenseAdminFeesExtensionDTO.getExtensionAdministrationFeeBankVoucherDate().getMonthValue(),
                licenseAdminFeesExtensionDTO.getExtensionAdministrationFeeBankVoucherDate().getDayOfMonth()
        );

        LocalDate adminSubmissionVoucherDate = dateConverter.jalaliToGregorian(
                licenseAdminFeesExtensionDTO.getExtensionAdministrationFeeBankVoucherSubmissionDate().getYear(),
                licenseAdminFeesExtensionDTO.getExtensionAdministrationFeeBankVoucherSubmissionDate().getMonthValue(),
                licenseAdminFeesExtensionDTO.getExtensionAdministrationFeeBankVoucherSubmissionDate().getDayOfMonth()
        );
        // Update only the required fields
        existingLicenseAdminFeesExtension.setExtensionAdministrationFeeBankVoucherDate(adminSubmissionVoucherDate);
        existingLicenseAdminFeesExtension.setExtensionAdministrationFeeBankVoucherSubmissionDate(adminSubmissionVoucherDate);
        existingLicenseAdminFeesExtension.setExtensionAdministrationFeeBankVoucherNo(licenseAdminFeesExtensionDTO.getExtensionAdministrationFeeBankVoucherNo());
        existingLicenseAdminFeesExtension.setExtensionAdministrationPaymentStatus(licenseAdminFeesExtensionDTO.getExtensionAdministrationPaymentStatus());

        // Save the updated entity
        licenseAdminFeesExtensionFinanceService.save(existingLicenseAdminFeesExtension);
        return "redirect:/licenses/finance/extension/admin_fees_extension/license_admin_fees";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_ADMIN') or hasRole('ROLE_FINANCE')")
    @GetMapping("/licenses/finance/extension/admin_fees_extension/license_admin_fees/print/{id}")
    public String GetTariff(@PathVariable Long id, Model model){
        LicenseAdminFeesExtension licenseAdminFeesExtension = licenseAdminFeesExtensionFinanceService.findById(id);
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

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_ADMIN') or hasRole('ROLE_FINANCE')")
    @GetMapping("/licenses/finance/extension/admin_fees_extension/license_admin_fees/update/{id}")
    public String AdminFeesGet(@PathVariable Long id, Model model){
        LicenseAdminFeesExtension licenseAdminFeesExtension = licenseAdminFeesExtensionFinanceService.findById(id);
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
        return "licenses/finance/extension/admin_fees_extension/license_admin_fees_confirmation";
    }


}
