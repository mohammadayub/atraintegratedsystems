package atraintegratedsystems.licenses.controller;


import atraintegratedsystems.licenses.dto.LicenseApprovalDTO;
import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.service.LicenseAdministrationFeeService;
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
public class LicenseAdministrationFeeController {

    @Autowired
    private LicenseTypeService licenseTypeService;

    @Autowired
    private LicenseAdministrationFeeService licenseAdministrationFeeService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_ADMIN') or hasRole('ROLE_FINANCE')")
    @GetMapping("/licenses/finance/license_finance/administration_fees/license_administration_fee_list")
    public String showApplicationProfile(Model model) {
        List<LicenseApproval> profiles = licenseAdministrationFeeService.getAllApprovalApplicantsNotPaidAdministrationFee();
        model.addAttribute("profiles", profiles);
        return "licenses/finance/license_finance/administration_fees/license_administration_fee_list";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_ADMIN') or hasRole('ROLE_FINANCE')")
    @GetMapping("/licenses/finance/license_finance/administration_fees/license_administration_fee_list/add")
    public String PaymentConfirmationAdd(Model model){
        model.addAttribute("licenseApprovalDTO",new LicenseApprovalDTO());
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        return "licenses/finance/license_finance/administration_fees/license_administration_fee_payment_confirmation";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_ADMIN') or hasRole('ROLE_FINANCE')")
    @PostMapping("/licenses/finance/license_finance/administration_fees/license_administration_fee_list/add")
    public String updateLicenseApproval(@ModelAttribute LicenseApprovalDTO licenseApprovalDTO) {
        // Fetch the existing entity from the database
        LicenseApproval existingLicenseApproval = licenseAdministrationFeeService.findById(licenseApprovalDTO.getId());

        // Update only the required fields

//        DateConverter dateConverter = new DateConverter();
//        LocalDate administrationEntryVoucherDate = dateConverter.jalaliToGregorian(
//                licenseApprovalDTO.getAdministrationFeeEntryVoucherDate().getYear(),
//                licenseApprovalDTO.getAdministrationFeeEntryVoucherDate().getMonthValue(),
//                licenseApprovalDTO.getAdministrationFeeEntryVoucherDate().getDayOfMonth()
//        );
//        existingLicenseApproval.setAdministrationFeeEntryVoucherDate(administrationEntryVoucherDate);



        String[] parts = licenseApprovalDTO.getAdministrationFeeEntryVoucherDateJalaliDate().split("-");
        int jYear = Integer.parseInt(parts[0]);
        int jMonth = Integer.parseInt(parts[1]);
        int jDay = Integer.parseInt(parts[2]);

        PersianCalendarUtils converter = new PersianCalendarUtils();
        LocalDate administrationEntryVoucherDate = converter.jalaliToGregorian(jYear, jMonth, jDay);
        existingLicenseApproval.setAdministrationFeeEntryVoucherDate(administrationEntryVoucherDate);

        existingLicenseApproval.setAdministrationFeeBankVoucherNo(licenseApprovalDTO.getAdministrationFeeBankVoucherNo());
        existingLicenseApproval.setAdministrationFeePaymentStatus(licenseApprovalDTO.getAdministrationFeePaymentStatus());


        String[] subParts = licenseApprovalDTO.getAdministrationFeeBankVoucherSubmissionDateJalaliDate().split("-");
        int jSubYear = Integer.parseInt(subParts[0]);
        int jSubMonth = Integer.parseInt(subParts[1]);
        int jSubDay = Integer.parseInt(subParts[2]);

        LocalDate administrationSubmissionDate = converter.jalaliToGregorian(jSubYear, jSubMonth, jSubDay);
        existingLicenseApproval.setAdministrationFeeBankVoucherSubmissionDate(administrationSubmissionDate);




        // Set applicationFeeEnteredBy to the logged-in user
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String enteredBy = (principal instanceof UserDetails) ? ((UserDetails) principal).getUsername() : "Unknown";
        existingLicenseApproval.setAdministrationFeesEnteredBy(enteredBy);

        // If applicationFeeCreatedDate is null, set it to the current time
        if (existingLicenseApproval.getAdministrationFeesCreatedDate() == null) {
            existingLicenseApproval.setAdministrationFeesCreatedDate(LocalDateTime.now());
        }

        // Save the updated entity
        licenseAdministrationFeeService.save(existingLicenseApproval);
        return "redirect:/licenses/finance/license_finance/administration_fees/license_administration_fee_list";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_ADMIN') or hasRole('ROLE_FINANCE')")
    @GetMapping("/licenses/finance/license_finance/administration_fees/license_administration_fee_list/update/{id}")
    public String UpdateMcitFee(@PathVariable Long id, Model model){
        LicenseApproval licenseApproval = licenseAdministrationFeeService.findById(id);
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
        return "licenses/finance/license_finance/administration_fees/license_adminstration_fee_payment_confirmation";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_ADMIN') or hasRole('ROLE_FINANCE')")
    @GetMapping("/licenses/finance/license_finance/administration_fees/license_administration_fee_list/print/{id}")
    public String GetTariff(@PathVariable Long id, Model model){
        LicenseApproval licenseApproval = licenseAdministrationFeeService.findById(id);
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

        licenseApprovalDTO.setAdministrativeYearlyFees(licenseApproval.getAdministrativeYearlyFees());
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        model.addAttribute("licenseApprovalDTO", licenseApprovalDTO);
        return "licenses/finance/license_finance/administration_fees/license_administration_fee_print_tariff";
    }
}
