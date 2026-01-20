package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.dto.CodeDetailDTO;
import atraintegratedsystems.codes.model.CodeDetail;
import atraintegratedsystems.codes.service.CodeDetailService;
import atraintegratedsystems.codes.service.ShortCodesDetailPaymentsConfirmationFinanceService;
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

        CodeDetail codeDetail = codeDetailService
                .getCodeDetailId(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        model.addAttribute("codeDetail", codeDetail);
        return "codes/finance/shortcode/applicationfees/applicationfee_confirm";
    }


    @PostMapping("/codes/applicationfees/confirm/save")
    public String confirmPayment(CodeDetailDTO dto) {

        codesDetailPaymentsConfirmationService.confirmApplicationFee(dto);

        return "redirect:/codes/finance/applicationFeelist";
    }



}
