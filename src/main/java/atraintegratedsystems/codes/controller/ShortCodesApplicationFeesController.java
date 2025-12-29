package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.dto.CodeDetailDTO;
import atraintegratedsystems.codes.model.CodeDetail;
import atraintegratedsystems.codes.service.CodeDetailService;
import atraintegratedsystems.codes.service.CodesDetailPaymentsConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ShortCodesApplicationFeesController {

    @Autowired
    private CodeDetailService codeDetailService;

    @Autowired
    private CodesDetailPaymentsConfirmationService codesDetailPaymentsConfirmationService;

    @GetMapping("/codes/finance/applicationFeelist")
    public String codeSummary(Model model) {
        List<Object[]> codes = codesDetailPaymentsConfirmationService.getunPaidApplicationFees();
        model.addAttribute("codes", codes);
        return "codes/finance/applicationfees/applicationfeeslist";
    }

    // ✅ TARIFF PAGE
//    @GetMapping("/tariff/{id}")
//    public String showTariff(@PathVariable Long id, Model model) {
//
//        Object[] fee = codeDetailService.getApplicationFeeTariff(id);
//
//        // LOG IT (important)
//        System.out.println("TARIFF DATA = " + Arrays.toString(fee));
//
//        if (fee == null) {
//            model.addAttribute("error", "Tariff not found");
//            return "codes/finance/applicationfees/applicationfee_tariff";
//        }
//
//        model.addAttribute("fee", fee);
//        return "codes/finance/applicationfees/applicationfee_tariff";
//    }
    @GetMapping("/codes/applicationfees/edit/{id}")
    public String showCodeTariff(@PathVariable Long id, Model model) {

        CodeDetail codeDetail = codesDetailPaymentsConfirmationService
                .getUnpaidApplicationFeeForEdit(id)
                .orElseThrow(() -> new RuntimeException("Unpaid application fee not found"));

        model.addAttribute("codeDetail", codeDetail);
        return "codes/finance/applicationfees/applicationfee_tariff";
    }



    // confirmation section

    @GetMapping("/codes/applicationfees/confirm/{id}")
    public String showConfirmForm(@PathVariable Long id, Model model) {

        CodeDetail codeDetail = codeDetailService
                .getCodeDetailId(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        model.addAttribute("codeDetail", codeDetail);
        return "codes/finance/applicationfees/applicationfee_confirm";
    }

//    @PostMapping("/codes/applicationfees/confirm/save")
//    public String confirmPayment(
//            @RequestParam Long id,
//            @RequestParam String applicationFeebankVoucherNo,
//            @RequestParam String applicationFeeEnterVoucherDate,
//            @RequestParam String applicationFeebankVoucherSubmissionDate
//    ) {
//
//        codeDetailService.confirmApplicationFee(
//                id,
//                applicationFeebankVoucherNo,
//                applicationFeeEnterVoucherDate,
//                applicationFeebankVoucherSubmissionDate
//        );
//
//        return "redirect:/codes/finance/applicationFeelist";
//    }

    @PostMapping("/codes/applicationfees/confirm/save")
    public String confirmPayment(CodeDetailDTO dto) {

        codesDetailPaymentsConfirmationService.confirmApplicationFee(dto);

        return "redirect:/codes/finance/applicationFeelist";
    }



}
