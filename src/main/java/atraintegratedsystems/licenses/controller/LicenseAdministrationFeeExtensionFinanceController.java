package atraintegratedsystems.licenses.controller;

import atraintegratedsystems.licenses.dto.LicenseAdminFeesExtensionDTO;
import atraintegratedsystems.licenses.model.LicenseAdminFeesExtension;
import atraintegratedsystems.licenses.service.LicenseAdminFeesExtensionFinanceService;
import atraintegratedsystems.utils.DateConverter;
import atraintegratedsystems.utils.JalaliDate;
import atraintegratedsystems.utils.PersianCalendarUtils;
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



        String[] partsEntry = licenseAdminFeesExtensionDTO.getExtensionAdministrationFeeBankVoucherJalaliDate().split("-");
        int jYear = Integer.parseInt(partsEntry[0]);
        int jMonth = Integer.parseInt(partsEntry[1]);
        int jDay = Integer.parseInt(partsEntry[2]);
        PersianCalendarUtils converter = new PersianCalendarUtils();
        LocalDate licenseAdminVoucherDate = converter.jalaliToGregorian(jYear, jMonth, jDay);
        licenseAdminFeesExtensionDTO.setExtensionAdministrationFeeBankVoucherDate(licenseAdminVoucherDate);



        String[] partsSub = licenseAdminFeesExtensionDTO.getExtensionAdministrationFeeBankVoucherJalaliDate().split("-");
        int jSubYear = Integer.parseInt(partsSub[0]);
        int jSubMonth = Integer.parseInt(partsSub[1]);
        int jSubDay = Integer.parseInt(partsSub[2]);
        LocalDate licenseAdminSubmissionDate = converter.jalaliToGregorian(jSubYear, jSubMonth, jSubDay);
        licenseAdminFeesExtensionDTO.setExtensionAdministrationFeeBankVoucherSubmissionDate(licenseAdminSubmissionDate);



        // Update only the required fields
        existingLicenseAdminFeesExtension.setExtensionAdministrationFeeBankVoucherNo(licenseAdminFeesExtensionDTO.getExtensionAdministrationFeeBankVoucherNo());
        existingLicenseAdminFeesExtension.setExtensionAdministrationPaymentStatus(licenseAdminFeesExtensionDTO.getExtensionAdministrationPaymentStatus());

        // Set Admin extensionAdministrationPaymentStatusEnteredBy to the logged-in user
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String enteredBy = (principal instanceof UserDetails) ? ((UserDetails) principal).getUsername() : "Unknown";
        existingLicenseAdminFeesExtension.setExtensionAdministrationPaymentStatusCreatedBy(enteredBy);

        // If extensionAdministrationPaymentStatusCreatedDate is null, set it to the current time
        if (existingLicenseAdminFeesExtension.getExtensionAdministrationPaymentStatusCreatedDate() == null) {
            existingLicenseAdminFeesExtension.setExtensionAdministrationPaymentStatusCreatedDate(LocalDateTime.now());
        }



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

        //License Approval Date

        //License Approval Date
        DateConverter converter = new DateConverter();
        LocalDate gregDate = licenseAdminFeesExtension.getLicenseApproval().getApprovalDate();

        JalaliDate jalali = converter.gregorianToJalali(
                gregDate.getYear(),
                gregDate.getMonthValue(),
                gregDate.getDayOfMonth()
        );

// Convert JalaliDate → readable String (e.g. 1403-09-20)
        String jalaliString = jalali.getYear() + "-" + jalali.getMonthPersian().getValue() + "-" + jalali.getDay();

// Add to DTO (optional)
        licenseAdminFeesExtensionDTO.setLicenseApprovalDate(jalaliString);

// IMPORTANT — Add to model
        model.addAttribute("jalali", jalaliString);


        //End



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
