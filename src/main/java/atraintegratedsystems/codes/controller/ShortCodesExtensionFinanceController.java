package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.dto.CodeExtensionDetailDTO;
import atraintegratedsystems.codes.model.CodeExtensionDetail;
import atraintegratedsystems.codes.service.ShortCodesDetailFinanceExtensionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ShortCodesExtensionFinanceController {

    private final ShortCodesDetailFinanceExtensionService financeService;

    @GetMapping("/codes/finance/extension/home")
    public String codeExtensionFinanceHome() {
        return "codes/finance/extension/home";
    }

    /* ======================
       APPLICATION FEE
       ====================== */

    @GetMapping("/finance/code-extension/application-fee-extended")
    public String applicationFeeExtendedList(Model model) {

        model.addAttribute(
                "extensions",
                financeService.getApplicationFeeExtendedList()
        );

        return "codes/finance/extension/shortcode/application-fee-extended-list";
    }

    @GetMapping("/finance/code-extension/application-fee/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {

        CodeExtensionDetail detail = financeService.getById(id);
        model.addAttribute("detail", detail);

        return "codes/finance/extension/shortcode/application-fee-edit";
    }

    @PostMapping("/finance/code-extension/application-fee/update")
    public String updateApplicationFee(
            @ModelAttribute CodeExtensionDetailDTO dto
    ) {

        financeService.updateApplicationFee(dto);

        return "redirect:/finance/code-extension/application-fee-extended";
    }

    /* ======================
       ROYALTY FEE
       ====================== */

    @GetMapping("/finance/code-extension/royalty-fee-extended")
    public String royaltyFeeExtendedList(Model model) {

        model.addAttribute(
                "extensions",
                financeService.getRoyaltyFeeExtendedList()
        );

        return "codes/finance/extension/shortcode/royalty-fee-extended-list";
    }

    @GetMapping("/finance/code-extension/royalty-fee/edit/{id}")
    public String editRoyaltyForm(@PathVariable Long id, Model model) {

        CodeExtensionDetail detail = financeService.getById(id);
        model.addAttribute("detail", detail);

        return "codes/finance/extension/shortcode/royalty-fee-edit";
    }

    @PostMapping("/finance/code-extension/royalty-fee/update")
    public String updateRoyaltyFee(
            @ModelAttribute CodeExtensionDetailDTO dto
    ) {

        financeService.updateRoyaltyFee(dto);

        return "redirect:/finance/code-extension/royalty-fee-extended";
    }
}
