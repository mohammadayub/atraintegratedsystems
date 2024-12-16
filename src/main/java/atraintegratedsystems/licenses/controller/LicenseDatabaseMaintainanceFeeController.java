package atraintegratedsystems.licenses.controller;

import atraintegratedsystems.licenses.dto.LicenseApprovalDTO;
import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.service.LicenseAdministrationFeeService;
import atraintegratedsystems.licenses.service.LicenseDatabaseMaintainanceFeeService;
import atraintegratedsystems.licenses.service.LicenseTypeService;
import atraintegratedsystems.utils.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class LicenseDatabaseMaintainanceFeeController {

    @Autowired
    private LicenseTypeService licenseTypeService;

    @Autowired
    private LicenseDatabaseMaintainanceFeeService licenseDatabaseMaintainanceFeeService;


    @GetMapping("/licenses/finance/license_finance/database_maintainance_fees/license_database_fee_list")
    public String showApplicationProfile(Model model) {
        List<LicenseApproval> profiles = licenseDatabaseMaintainanceFeeService.getAllApprovalApplicantsNotPaidDatabaseFees();
        model.addAttribute("profiles", profiles);
        return "licenses/finance/license_finance/database_maintainance_fees/license_database_fee_list";
    }

    @GetMapping("/licenses/finance/license_finance/database_maintainance_fees/license_database_fee_list/add")
    public String PaymentConfirmationAdd(Model model){
        model.addAttribute("licenseApprovalDTO",new LicenseApprovalDTO());
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        return "licenses/finance/license_finance/database_maintainance_fees/license_database_fee_payment_confirmation";
    }


    @PostMapping("/licenses/finance/license_finance/database_maintainance_fees/license_database_fee_list/add")
    public String updateLicenseApproval(@ModelAttribute LicenseApprovalDTO licenseApprovalDTO) {
        // Fetch the existing entity from the database
        LicenseApproval existingLicenseApproval = licenseDatabaseMaintainanceFeeService.findById(licenseApprovalDTO.getId());
        DateConverter dateConverter = new DateConverter();
        LocalDate databasemaintainanceEntryVoucherDate = dateConverter.jalaliToGregorian(
                licenseApprovalDTO.getDatabaseMaintianenceFeeEntryVoucherDate().getYear(),
                licenseApprovalDTO.getDatabaseMaintianenceFeeEntryVoucherDate().getMonthValue(),
                licenseApprovalDTO.getDatabaseMaintianenceFeeEntryVoucherDate().getDayOfMonth()
        );

        LocalDate databasemaintainanceSubmissionVoucherDate = dateConverter.jalaliToGregorian(
                licenseApprovalDTO.getDatabasemaintainanceFeeBankVoucherSubmissionDate().getYear(),
                licenseApprovalDTO.getDatabasemaintainanceFeeBankVoucherSubmissionDate().getMonthValue(),
                licenseApprovalDTO.getDatabasemaintainanceFeeBankVoucherSubmissionDate().getDayOfMonth()
        );
        // Update only the required fields
        existingLicenseApproval.setDatabaseMaintianenceFeeEntryVoucherDate(databasemaintainanceEntryVoucherDate);
        existingLicenseApproval.setDatabasemaintainanceFeeBankVoucherSubmissionDate(databasemaintainanceSubmissionVoucherDate);
        existingLicenseApproval.setDatabaseMaintianenceFeeBankVoucherNo(licenseApprovalDTO.getDatabaseMaintianenceFeeBankVoucherNo());
        existingLicenseApproval.setDatabaseMaintianenceFeePaymentStatus(licenseApprovalDTO.getDatabaseMaintianenceFeePaymentStatus());

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
        licenseApprovalDTO.setApprovalDate(licenseApproval.getApprovalDate());
        licenseApprovalDTO.setBoardDecisionNumber(licenseApproval.getBoardDecisionNumber());
        licenseApprovalDTO.setApprovalStatus(licenseApproval.getApprovalStatus());
        licenseApprovalDTO.setCurrencyType(licenseApproval.getCurrencyType());
        licenseApprovalDTO.setDatabaseYearlyMaintainanceFees(licenseApproval.getDatabaseYearlyMaintainanceFees());
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        model.addAttribute("licenseApprovalDTO", licenseApprovalDTO);
        return "licenses/finance/license_finance/database_maintainance_fees/license_database_fee_print_tariff";
    }
}
