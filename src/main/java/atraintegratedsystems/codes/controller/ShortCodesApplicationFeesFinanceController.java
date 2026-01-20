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

        // 1️⃣ Fetch entity
        CodeDetail codeDetail = codeDetailService
                .getCodeDetailId(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        // 2️⃣ Map entity → DTO
        CodeDetailDTO dto = new CodeDetailDTO();
        dto.setId(codeDetail.getId());
        dto.setShortCode(codeDetail.getShortCode());
        dto.setApplicationFeebankVoucherNo(codeDetail.getApplicationFeebankVoucherNo());

        PersianCalendarUtils converter = new PersianCalendarUtils();

        // ===== Convert Entry Voucher Date to Jalali =====
        if (codeDetail.getApplicationFeeEnterVoucherDate() != null) {
            int[] jalali = converter.gregorianToJalali(codeDetail.getApplicationFeeEnterVoucherDate());
            String jalaliStr = jalali[0] + "-" + String.format("%02d", jalali[1]) + "-" + String.format("%02d", jalali[2]);
            dto.setApplicationFeeEnterVoucherDateJalali(jalaliStr);
        }

        // ===== Convert Submission Date to Jalali =====
        if (codeDetail.getApplicationFeebankVoucherSubmissionDate() != null) {
            int[] jalali = converter.gregorianToJalali(codeDetail.getApplicationFeebankVoucherSubmissionDate());
            String jalaliStr = jalali[0] + "-" + String.format("%02d", jalali[1]) + "-" + String.format("%02d", jalali[2]);
            dto.setApplicationFeebankVoucherSubmissionDateJalali(jalaliStr);
        }

        // 3️⃣ Add DTO to model (not the entity)
        model.addAttribute("codeDetail", dto);

        return "codes/finance/shortcode/applicationfees/applicationfee_confirm";
    }



    @PostMapping("/codes/applicationfees/confirm/save")
    public String confirmPayment(CodeDetailDTO dto) {

        codesDetailPaymentsConfirmationService.confirmApplicationFee(dto);

        return "redirect:/codes/finance/applicationFeelist";
    }



}
