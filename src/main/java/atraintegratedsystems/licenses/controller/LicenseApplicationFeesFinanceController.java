package atraintegratedsystems.licenses.controller;

import atraintegratedsystems.licenses.dto.LicenseApplicantDTO;
import atraintegratedsystems.licenses.model.LicenseApplicant;
import atraintegratedsystems.licenses.service.LicenseApplicantFinanceService;
import atraintegratedsystems.licenses.service.LicenseTypeService;
import atraintegratedsystems.utils.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
public class LicenseApplicationFeesFinanceController {

    @Autowired
    private LicenseApplicantFinanceService licenseApplicantFinanceService;

    @Autowired
    private LicenseTypeService licenseTypeService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_FINANCE')")
    @GetMapping("/licenses/finance/license_finance/application_fees/license_application_fee_list")
    public String showApplicationProfile(Model model) {
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        List<LicenseApplicant> profiles = licenseApplicantFinanceService.getAllUnpaid();
        model.addAttribute("profiles", profiles);
        return "licenses/finance/license_finance/application_fees/license_application_fee_list";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_FINANCE')")
    @GetMapping("/licenses/finance/license_finance/application_fees/license_application_fee_list/add")
    public String PaymentConfirmationAdd(Model model){
        model.addAttribute("licenseApplicantDTO",new LicenseApplicantDTO());
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        return "licenses/finance/license_finance/application_fees/license_application_payment_confirmation";
    }


    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_FINANCE')")
    @PostMapping("/licenses/finance/license_finance/application_fees/license_application_fee_list/add")
    public String updateBankVoucherNoAndPaymentStatus(@Valid @ModelAttribute("licenseApplicantDTO") LicenseApplicantDTO licenseApplicantDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "licenses/finance/license_finance/application_fees/license_application_payment_confirmation";
        }
        LicenseApplicant licenseApplicant = licenseApplicantFinanceService.getApplicantByReqId(licenseApplicantDTO.getReqId()).orElseThrow(() -> new IllegalArgumentException("Invalid code ID: " + licenseApplicantDTO.getReqId()));
        // Update only the editable fields
        DateConverter dateConverter = new DateConverter();
        // Convert Jalali date to Gregorian
        LocalDate entryDate = dateConverter.jalaliToGregorian(licenseApplicantDTO.getEntryApplicationFeeVoucherDate().getYear(), licenseApplicantDTO.getEntryApplicationFeeVoucherDate().getMonthValue(), licenseApplicantDTO.getEntryApplicationFeeVoucherDate().getDayOfMonth());
        LocalDate entrySubmissionDate = dateConverter.jalaliToGregorian(licenseApplicantDTO.getApplicationFeeBankVoucherSubmissionDate().getYear(), licenseApplicantDTO.getApplicationFeeBankVoucherSubmissionDate().getMonthValue(), licenseApplicantDTO.getApplicationFeeBankVoucherSubmissionDate().getDayOfMonth());
        licenseApplicant.setEntryApplicationFeeVoucherDate(entryDate);
        licenseApplicant.setApplicationFeeBankVoucherSubmissionDate(entrySubmissionDate);
        licenseApplicant.setBankVoucher(licenseApplicantDTO.getBankVoucher());
        licenseApplicant.setPaymentStatus(licenseApplicantDTO.getPaymentStatus());
        licenseApplicantFinanceService.PaymentSave(licenseApplicant);
        return "redirect:/licenses/finance/license_finance/application_fees/license_application_fee_list";
    }



    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_FINANCE')")
    @GetMapping("/licenses/finance/license_finance/application_fees/license_application_fee_list/update/{reqId}")
    public String updateApplicantGet(@PathVariable String reqId, Model model) throws Exception {
        LicenseApplicant licenseApplicant = licenseApplicantFinanceService.getApplicantByReqId(reqId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid applicant ID: " + reqId));
        LicenseApplicantDTO licenseApplicantDTO = new LicenseApplicantDTO();
        // Map fields from licenseApplicant to licenseApplicantDTO
        licenseApplicantDTO.setId(licenseApplicant.getId());
        licenseApplicantDTO.setReqId(licenseApplicant.getReqId());
        licenseApplicantDTO.setReqDate(licenseApplicant.getReqDate());
        licenseApplicantDTO.setLicenseTypeId(licenseApplicant.getLicenseType().getId());
        licenseApplicantDTO.setCurrencyType(licenseApplicant.getCurrencyType());
        licenseApplicantDTO.setFinanceType(licenseApplicant.getFinanceType());
        licenseApplicantDTO.setCompanyLicenseName(licenseApplicant.getCompanyLicenseName());
        licenseApplicantDTO.setLicenseNo(licenseApplicant.getLicenseNo());
        licenseApplicantDTO.setYearOfEstablishment(licenseApplicant.getYearOfEstablishment());
        licenseApplicantDTO.setExpiryDate(licenseApplicant.getExpiryDate());
        licenseApplicantDTO.setApplicationFees(licenseApplicant.getApplicationFees());
        licenseApplicantDTO.setEntryApplicationFeeVoucherDate(licenseApplicant.getEntryApplicationFeeVoucherDate());
        licenseApplicantDTO.setBankVoucher(licenseApplicant.getBankVoucher());
        licenseApplicantDTO.setPaymentStatus(licenseApplicant.getPaymentStatus());
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        model.addAttribute("licenseApplicantDTO", licenseApplicantDTO);
        return "licenses/finance/license_finance/application_fees/license_application_payment_confirmation";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_FINANCE')")
    @GetMapping("/licenses/finance/license_finance/application_fees/license_application_fee_list/print/{reqId}")
    public String GetTariff(@PathVariable String reqId, Model model){
        LicenseApplicant licenseApplicant = licenseApplicantFinanceService.getApplicantByReqId(reqId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid applicant ID: " + reqId));
        LicenseApplicantDTO licenseApplicantDTO = new LicenseApplicantDTO();
        // Map fields from licenseApplicant to licenseApplicantDTO
        licenseApplicantDTO.setId(licenseApplicant.getId());
        licenseApplicantDTO.setReqId(licenseApplicant.getReqId());
        licenseApplicantDTO.setReqDate(licenseApplicant.getReqDate());
        licenseApplicantDTO.setLicenseTypeId(licenseApplicant.getLicenseType().getId());
        licenseApplicantDTO.setCurrencyType(licenseApplicant.getCurrencyType());
        licenseApplicantDTO.setFinanceType(licenseApplicant.getFinanceType());
        licenseApplicantDTO.setCompanyLicenseName(licenseApplicant.getCompanyLicenseName());
        licenseApplicantDTO.setLicenseNo(licenseApplicant.getLicenseNo());
        licenseApplicantDTO.setYearOfEstablishment(licenseApplicant.getYearOfEstablishment());
        licenseApplicantDTO.setExpiryDate(licenseApplicant.getExpiryDate());
        licenseApplicantDTO.setApplicationFees(licenseApplicant.getApplicationFees());
        licenseApplicantDTO.setValidity(licenseApplicant.getValidity());
        licenseApplicantDTO.setEntryApplicationFeeVoucherDate(licenseApplicant.getEntryApplicationFeeVoucherDate());
        licenseApplicantDTO.setBankVoucher(licenseApplicant.getBankVoucher());
        licenseApplicantDTO.setPaymentStatus(licenseApplicant.getPaymentStatus());
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        model.addAttribute("licenseApplicantDTO", licenseApplicantDTO);
        return "licenses/finance/license_finance/application_fees/license_application_print_tariff";
    }


}
