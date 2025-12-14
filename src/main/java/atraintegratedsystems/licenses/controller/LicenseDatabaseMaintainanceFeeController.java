package atraintegratedsystems.licenses.controller;

import atraintegratedsystems.licenses.dto.LicenseApprovalDTO;
import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.service.LicenseDatabaseMaintainanceFeeService;
import atraintegratedsystems.licenses.service.LicenseTypeService;
import atraintegratedsystems.utils.DateConverter;
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
public class LicenseDatabaseMaintainanceFeeController {

    @Autowired
    private LicenseTypeService licenseTypeService;

    @Autowired
    private LicenseDatabaseMaintainanceFeeService licenseDatabaseMaintainanceFeeService;


    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_ADMIN') or hasRole('ROLE_FINANCE')")
    @GetMapping("/licenses/finance/license_finance/database_maintainance_fees/license_database_fee_list")
    public String showApplicationProfile(Model model) {
        List<LicenseApproval> profiles = licenseDatabaseMaintainanceFeeService.getAllApprovalApplicantsNotPaidDatabaseFees();
        model.addAttribute("profiles", profiles);
        return "licenses/finance/license_finance/database_maintainance_fees/license_database_fee_list";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_ADMIN') or hasRole('ROLE_FINANCE')")
    @GetMapping("/licenses/finance/license_finance/database_maintainance_fees/license_database_fee_list/add")
    public String PaymentConfirmationAdd(Model model){
        model.addAttribute("licenseApprovalDTO",new LicenseApprovalDTO());
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        return "licenses/finance/license_finance/database_maintainance_fees/license_database_fee_payment_confirmation";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_ADMIN') or hasRole('ROLE_FINANCE')")
    @PostMapping("/licenses/finance/license_finance/database_maintainance_fees/license_database_fee_list/add")
    public String updateLicenseApproval(@ModelAttribute LicenseApprovalDTO licenseApprovalDTO) {
        // Fetch the existing entity from the database
        LicenseApproval existingLicenseApproval = licenseDatabaseMaintainanceFeeService.findById(licenseApprovalDTO.getId());

        String[] parts = licenseApprovalDTO.getDatabaseMaintianenceFeeEntryVoucherDateJalaliDate().split("-");
        int jYear = Integer.parseInt(parts[0]);
        int jMonth = Integer.parseInt(parts[1]);
        int jDay = Integer.parseInt(parts[2]);

        PersianCalendarUtils converter = new PersianCalendarUtils();
        LocalDate databaseEntryVoucherDate = converter.jalaliToGregorian(jYear, jMonth, jDay);
        existingLicenseApproval.setDatabaseMaintianenceFeeEntryVoucherDate(databaseEntryVoucherDate);


        String[] subParts = licenseApprovalDTO.getDatabasemaintainanceFeeBankVoucherSubmissionDateJalaliDate().split("-");
        int jSubYear = Integer.parseInt(subParts[0]);
        int jSubMonth = Integer.parseInt(subParts[1]);
        int jSubDay = Integer.parseInt(subParts[2]);

        LocalDate databaseSubmissionDate = converter.jalaliToGregorian(jSubYear, jSubMonth, jSubDay);
        existingLicenseApproval.setDatabasemaintainanceFeeBankVoucherSubmissionDate(databaseSubmissionDate);




        // Update only the required fields

        existingLicenseApproval.setDatabaseMaintianenceFeeBankVoucherNo(licenseApprovalDTO.getDatabaseMaintianenceFeeBankVoucherNo());
        existingLicenseApproval.setDatabaseMaintianenceFeePaymentStatus(licenseApprovalDTO.getDatabaseMaintianenceFeePaymentStatus());
        // Set applicationFeeEnteredBy to the logged-in user
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String enteredBy = (principal instanceof UserDetails) ? ((UserDetails) principal).getUsername() : "Unknown";
        existingLicenseApproval.setDatabaseMaintainanceFeesEnteredBy(enteredBy);

        // If applicationFeeCreatedDate is null, set it to the current time
        if (existingLicenseApproval.getDatabaseMaintainanceFeesCreatedDate() == null) {
            existingLicenseApproval.setDatabaseMaintainanceFeesCreatedDate(LocalDateTime.now());
        }

        // Save the updated entity
        licenseDatabaseMaintainanceFeeService.save(existingLicenseApproval);
        return "redirect:/licenses/finance/license_finance/database_maintainance_fees/license_database_fee_list";
    }


    @GetMapping("/licenses/finance/license_finance/database_maintainance_fees/license_database_fee_list/update/{id}")
    public String UpdateMcitFee(@PathVariable Long id, Model model){
        LicenseApproval licenseApproval = licenseDatabaseMaintainanceFeeService.findById(id);
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


        licenseApprovalDTO.setDatabaseYearlyMaintainanceFees(licenseApproval.getDatabaseYearlyMaintainanceFees());
        licenseApprovalDTO.setDatabaseMaintianenceFeeEntryVoucherDate(licenseApproval.getDatabaseMaintianenceFeeEntryVoucherDate());
        licenseApprovalDTO.setDatabaseMaintianenceFeeBankVoucherNo(licenseApproval.getDatabaseMaintianenceFeeBankVoucherNo());
        licenseApprovalDTO.setDatabaseMaintianenceFeePaymentStatus(licenseApproval.getDatabaseMaintianenceFeePaymentStatus());
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        model.addAttribute("licenseApprovalDTO", licenseApprovalDTO);
        return "/licenses/finance/license_finance/database_maintainance_fees/license_database_fee_payment_confirmation";
    }

    @GetMapping("/licenses/finance/license_finance/database_maintainance_fees/license_database_fee_list/print/{id}")
    public String GetTariff(@PathVariable Long id, Model model){
        LicenseApproval licenseApproval = licenseDatabaseMaintainanceFeeService.findById(id);
        LicenseApprovalDTO licenseApprovalDTO = new LicenseApprovalDTO();
        // Map fields from licenseApplicant to licenseApplicantDTO
        licenseApprovalDTO.setId(licenseApproval.getId());
        licenseApprovalDTO.setApprovalId(licenseApproval.getApprovalId());
        licenseApprovalDTO.setApplicantLicenseCompanyName(licenseApproval.getLicenseApplicant().getCompanyLicenseName());
        licenseApprovalDTO.setLicenseTypeName(licenseApproval.getLicenseType().getName());
//        licenseApprovalDTO.setApprovalDate(licenseApproval.getApprovalDate());


        LocalDate approvalDateGregorian = licenseApproval.getApprovalDate();
        PersianCalendarUtils converter = new PersianCalendarUtils();
        int[] jalaliDate = converter.gregorianToJalali(approvalDateGregorian);

        String approvalDateShamsi = String.format("%04d-%02d-%02d", jalaliDate[0], jalaliDate[1], jalaliDate[2]);
        licenseApprovalDTO.setApprovalDate(LocalDate.parse(approvalDateShamsi));


        // Extract year
        String year = approvalDateShamsi.split("-")[0];
        licenseApprovalDTO.setApprovalYear(year);







        licenseApprovalDTO.setBoardDecisionNumber(licenseApproval.getBoardDecisionNumber());
        licenseApprovalDTO.setApprovalStatus(licenseApproval.getApprovalStatus());
        licenseApprovalDTO.setCurrencyType(licenseApproval.getCurrencyType());
        licenseApprovalDTO.setDatabaseYearlyMaintainanceFees(licenseApproval.getDatabaseYearlyMaintainanceFees());
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        model.addAttribute("licenseApprovalDTO", licenseApprovalDTO);
        return "licenses/finance/license_finance/database_maintainance_fees/license_database_fee_print_tariff";
    }
}
