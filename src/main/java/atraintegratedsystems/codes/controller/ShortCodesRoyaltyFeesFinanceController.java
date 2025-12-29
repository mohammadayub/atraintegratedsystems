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
public class ShortCodesRoyaltyFeesFinanceController {
    @Autowired
    private CodeDetailService codeDetailService;

    @Autowired
    private ShortCodesDetailPaymentsConfirmationFinanceService codesDetailPaymentsConfirmationService;

    @GetMapping("/codes/finance/royaltyFeelist")
    public String codeSummary(Model model) {
        List<Object[]> codes = codesDetailPaymentsConfirmationService.getunPaidRyaltyFees();
        model.addAttribute("codes", codes);
        return "codes/finance/royaltyfees/royaltyfeeslist";
    }

    // Printing Tariffs
    @GetMapping("/codes/royaltyfees/edit/{id}")
    public String showCodeTariff(@PathVariable Long id, Model model) {

        CodeDetail codeDetail = codesDetailPaymentsConfirmationService
                .getUnpaidApplicationFeeForEdit(id)
                .orElseThrow(() -> new RuntimeException("Unpaid application fee not found"));

        model.addAttribute("codeDetail", codeDetail);
        return "codes/finance/royaltyfees/applicationfee_tariff";
    }

    // Fees Confirmation

    // show confirm page
    @GetMapping("/codes/royaltyfees/confirm/{id}")
    public String showRoyaltyConfirmForm(@PathVariable Long id, Model model) {

        CodeDetail codeDetail = codeDetailService
                .getCodeDetailId(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        model.addAttribute("codeDetail", codeDetail);
        return "codes/finance/royaltyfees/royaltyfee_confirm";
    }


    // save confirmation
    @PostMapping("/codes/royaltyfees/confirm/save")
    public String confirmRoyaltyFee(CodeDetailDTO dto) {

        codesDetailPaymentsConfirmationService.confirmRoyaltyFee(dto);

        return "redirect:/codes/finance/royaltyFeelist";
    }



}
