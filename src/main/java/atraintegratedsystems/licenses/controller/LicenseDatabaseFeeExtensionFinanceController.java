package atraintegratedsystems.licenses.controller;

import atraintegratedsystems.licenses.dto.LicenseDatabaseFeesExtensionDTO;
import atraintegratedsystems.licenses.model.LicenseDatabaseFeesExtension;
import atraintegratedsystems.licenses.service.LicenseDatabaseFeesExtensionFinanceService;
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
public class LicenseDatabaseFeeExtensionFinanceController {
    @Autowired
    private LicenseDatabaseFeesExtensionFinanceService licenseDatabaseFeesExtensionFinanceService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_ADMIN') or hasRole('ROLE_FINANCE')")
    @GetMapping("/licenses/finance/extension/database_fees_extension/license_database_fees")
    public String showApplicationProfile(Model model) {
        List<LicenseDatabaseFeesExtension> profiles = licenseDatabaseFeesExtensionFinanceService.getAllDatabaseFeesExtension();
        model.addAttribute("profiles", profiles);
        return "licenses/finance/extension/database_fees_extension/license_database_fees";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_ADMIN') or hasRole('ROLE_FINANCE')")
    @GetMapping("/licenses/finance/extension/database_fees_extension/license_database_fees/add")
    public String PaymentConfirmationAdd(Model model){
        model.addAttribute("licenseDatabaseFeesExtensionDTO",new LicenseDatabaseFeesExtensionDTO());
        return "/licenses/finance/extension/database_fees_extension/license_database_fees_confirmation";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_ADMIN') or hasRole('ROLE_FINANCE')")
    @PostMapping("/licenses/finance/extension/database_fees_extension/license_database_fees/add")
    public String updateLicenseApproval(@ModelAttribute LicenseDatabaseFeesExtensionDTO licenseDatabaseFeesExtensionDTO) {
        // Fetch the existing entity from the database
        LicenseDatabaseFeesExtension existingLicenseDatabaseFeesExtension = licenseDatabaseFeesExtensionFinanceService.findById(licenseDatabaseFeesExtensionDTO.getId());
        DateConverter dateConverter = new DateConverter();
        LocalDate databaseEntryVoucherDate = dateConverter.jalaliToGregorian(
                licenseDatabaseFeesExtensionDTO.getExtensionDatabaseFeeBankVoucherDate().getYear(),
                licenseDatabaseFeesExtensionDTO.getExtensionDatabaseFeeBankVoucherDate().getMonthValue(),
                licenseDatabaseFeesExtensionDTO.getExtensionDatabaseFeeBankVoucherDate().getDayOfMonth()
        );

        LocalDate databaseSubmissionVoucherDate = dateConverter.jalaliToGregorian(
                licenseDatabaseFeesExtensionDTO.getExtensionDatabaseFeeBankVoucherSubmissionDate().getYear(),
                licenseDatabaseFeesExtensionDTO.getExtensionDatabaseFeeBankVoucherSubmissionDate().getMonthValue(),
                licenseDatabaseFeesExtensionDTO.getExtensionDatabaseFeeBankVoucherSubmissionDate().getDayOfMonth()
        );
        // Update only the required fields
        existingLicenseDatabaseFeesExtension.setExtensionDatabaseFeeBankVoucherDate(databaseSubmissionVoucherDate);
        existingLicenseDatabaseFeesExtension.setExtensionDatabaseFeeBankVoucherSubmissionDate(databaseSubmissionVoucherDate);
        existingLicenseDatabaseFeesExtension.setExtensionDatabaseFeeBankVoucherNo(licenseDatabaseFeesExtensionDTO.getExtensionDatabaseFeeBankVoucherNo());
        existingLicenseDatabaseFeesExtension.setExtensionDatabasePaymentStatus(licenseDatabaseFeesExtensionDTO.getExtensionDatabasePaymentStatus());

        // Set Database extensionDatabasePaymentStatusEnteredBy to the logged-in user
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String enteredBy = (principal instanceof UserDetails) ? ((UserDetails) principal).getUsername() : "Unknown";
        existingLicenseDatabaseFeesExtension.setExtensionDatabasePaymentStatusCreatedBy(enteredBy);

        // If extensionDatabasePaymentStatusCreatedDate is null, set it to the current time
        if (existingLicenseDatabaseFeesExtension.getExtensionDatabasePaymentStatusCreatedDate() == null) {
            existingLicenseDatabaseFeesExtension.setExtensionDatabasePaymentStatusCreatedDate(LocalDateTime.now());
        }
        // Save the updated entity
        licenseDatabaseFeesExtensionFinanceService.save(existingLicenseDatabaseFeesExtension);
        return "redirect:/licenses/finance/extension/database_fees_extension/license_database_fees";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_ADMIN') or hasRole('ROLE_FINANCE')")
    @GetMapping("/licenses/finance/extension/database_fees_extension/license_database_fees/print/{id}")
    public String GetTariff(@PathVariable Long id, Model model){
        LicenseDatabaseFeesExtension licenseDatabaseFeesExtension = licenseDatabaseFeesExtensionFinanceService.findById(id);
        LicenseDatabaseFeesExtensionDTO licenseDatabaseFeesExtensionDTO = new LicenseDatabaseFeesExtensionDTO();
        // Map fields from licenseApplicant to licenseApplicantDTO
        licenseDatabaseFeesExtensionDTO.setId(licenseDatabaseFeesExtension.getId());
        licenseDatabaseFeesExtensionDTO.setLicenseApprovalId(licenseDatabaseFeesExtension.getLicenseApproval().getId());
        licenseDatabaseFeesExtensionDTO.setLicenseAppId(licenseDatabaseFeesExtension.getLicenseApproval().getApprovalId());
        licenseDatabaseFeesExtensionDTO.setLicenseCompanyName(licenseDatabaseFeesExtension.getLicenseApproval().getLicenseApplicant().getCompanyLicenseName());
        licenseDatabaseFeesExtensionDTO.setLicenseTypeName(licenseDatabaseFeesExtension.getLicenseApproval().getLicenseType().getName());
        licenseDatabaseFeesExtensionDTO.setExtStartDate(licenseDatabaseFeesExtension.getExtentStartDate());
        licenseDatabaseFeesExtensionDTO.setExtExpireDate(licenseDatabaseFeesExtension.getExtentExpDate());
        licenseDatabaseFeesExtensionDTO.setExtensionDatabaseFees(licenseDatabaseFeesExtension.getExtensionDatabaseFees());
        model.addAttribute("licenseDatabaseFeesExtensionDTO", licenseDatabaseFeesExtensionDTO);
        return "licenses/finance/extension/database_fees_extension/license_database_fees_print_tariff";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_ADMIN') or hasRole('ROLE_FINANCE')")
    @GetMapping("/licenses/finance/extension/database_fees_extension/license_database_fees/update/{id}")
    public String DatabaseFeesGet(@PathVariable Long id, Model model){
        LicenseDatabaseFeesExtension licenseDatabaseFeesExtension = licenseDatabaseFeesExtensionFinanceService.findById(id);
        LicenseDatabaseFeesExtensionDTO licenseDatabaseFeesExtensionDTO = new LicenseDatabaseFeesExtensionDTO();
        // Map fields from licenseApplicant to licenseApplicantDTO
        licenseDatabaseFeesExtensionDTO.setId(licenseDatabaseFeesExtension.getId());
        licenseDatabaseFeesExtensionDTO.setLicenseApprovalId(licenseDatabaseFeesExtension.getLicenseApproval().getId());
        licenseDatabaseFeesExtensionDTO.setLicenseAppId(licenseDatabaseFeesExtension.getLicenseApproval().getApprovalId());
        licenseDatabaseFeesExtensionDTO.setLicenseCompanyName(licenseDatabaseFeesExtension.getLicenseApproval().getLicenseApplicant().getCompanyLicenseName());
        licenseDatabaseFeesExtensionDTO.setLicenseTypeName(licenseDatabaseFeesExtension.getLicenseApproval().getLicenseType().getName());
        licenseDatabaseFeesExtensionDTO.setExtStartDate(licenseDatabaseFeesExtension.getExtentStartDate());
        licenseDatabaseFeesExtensionDTO.setExtExpireDate(licenseDatabaseFeesExtension.getExtentExpDate());
        licenseDatabaseFeesExtensionDTO.setExtensionDatabaseFees(licenseDatabaseFeesExtension.getExtensionDatabaseFees());
        model.addAttribute("licenseDatabaseFeesExtensionDTO", licenseDatabaseFeesExtensionDTO);
        return "licenses/finance/extension/database_fees_extension/license_database_fees_confirmation";
    }
}