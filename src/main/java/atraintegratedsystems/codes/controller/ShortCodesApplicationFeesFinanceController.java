package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.dto.CodeDetailDTO;
import atraintegratedsystems.codes.model.CodeDetail;
import atraintegratedsystems.codes.service.CodeDetailService;
import atraintegratedsystems.codes.service.ShortCodesDetailPaymentsConfirmationFinanceService;
import atraintegratedsystems.utils.PersianCalendarUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ShortCodesApplicationFeesFinanceController {

    @Autowired
    private CodeDetailService codeDetailService;

    @Autowired
    private ShortCodesDetailPaymentsConfirmationFinanceService codesDetailPaymentsConfirmationService;

    @GetMapping("/codes/finance/applicationFeelist")
    public String codeSummary(Model model) {
        List<Object[]> codes = codesDetailPaymentsConfirmationService.getunPaidApplicationFees();
        model.addAttribute("codes", codes);
        return "codes/finance/shortcode/applicationfees/applicationfeeslist";
    }

    @GetMapping("/codes/applicationfees/edit/{id}")
    public String showCodeTariff(@PathVariable Long id, Model model) {

        CodeDetail codeDetail = codesDetailPaymentsConfirmationService
                .getUnpaidApplicationFeeForEdit(id)
                .orElseThrow(() -> new RuntimeException("Unpaid application fee not found"));

        model.addAttribute("codeDetail", codeDetail);
        return "codes/finance/shortcode/applicationfees/applicationfee_tariff";
    }

    // confirmation section

    @GetMapping("/codes/applicationfees/confirm/{id}")
    public String showConfirmForm(@PathVariable Long id, Model model) {
        // Retrieve the CodeDetail object based on the provided ID
        CodeDetail codeDetail = codeDetailService
                .getCodeDetailId(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        CodeDetailDTO codeDetailDTO = new CodeDetailDTO();
        codeDetailDTO.setId(codeDetail.getId());
        codeDetailDTO.setShortCode(codeDetail.getShortCode());

        // Handle application fee enter voucher date
        String voucherDateJalali = codeDetailDTO.getApplicationFeeEnterVoucherDateJalali();
        LocalDate applicationFeeVoucherDate = null;

        if (voucherDateJalali != null && !voucherDateJalali.isEmpty()) {
            String[] parts = voucherDateJalali.split("-");
            if (parts.length == 3) {
                try {
                    int jYear = Integer.parseInt(parts[0]);
                    int jMonth = Integer.parseInt(parts[1]);
                    int jDay = Integer.parseInt(parts[2]);

                    PersianCalendarUtils converter = new PersianCalendarUtils();
                    applicationFeeVoucherDate = converter.jalaliToGregorian(jYear, jMonth, jDay);
                } catch (NumberFormatException e) {
                    // Log the exception and handle it as needed
                }
            }
        }
        codeDetail.setApplicationFeeEnterVoucherDate(applicationFeeVoucherDate);

        // Handle application fee bank voucher submission date
        String submissionDateJalali = codeDetailDTO.getApplicationFeebankVoucherSubmissionDateJalali();
        LocalDate submissionFeeVoucherDate = null;

        if (submissionDateJalali != null && !submissionDateJalali.isEmpty()) {
            String[] partsSub = submissionDateJalali.split("-");
            if (partsSub.length == 3) {
                try {
                    int jYearSub = Integer.parseInt(partsSub[0]);
                    int jMonthSub = Integer.parseInt(partsSub[1]);
                    int jDaySub = Integer.parseInt(partsSub[2]);

                    PersianCalendarUtils converter = new PersianCalendarUtils();
                    submissionFeeVoucherDate = converter.jalaliToGregorian(jYearSub, jMonthSub, jDaySub);
                } catch (NumberFormatException e) {
                    // Log the exception and handle it as needed
                }
            }
        }

        // Set the processed dates into DTO
        codeDetailDTO.setApplicationFeeEnterVoucherDate(applicationFeeVoucherDate);
        codeDetailDTO.setApplicationFeebankVoucherNo(codeDetail.getApplicationFeebankVoucherNo());
        codeDetailDTO.setApplicationFeebankVoucherSubmissionDate(submissionFeeVoucherDate); // Ensure to set here

        model.addAttribute("codeDetail", codeDetail);
        return "codes/finance/shortcode/applicationfees/applicationfee_confirm";
    }

    @PostMapping("/codes/applicationfees/confirm/save")
    public String confirmPayment(CodeDetailDTO dto) {

        codesDetailPaymentsConfirmationService.confirmApplicationFee(dto);

        return "redirect:/codes/finance/applicationFeelist";
    }



}
