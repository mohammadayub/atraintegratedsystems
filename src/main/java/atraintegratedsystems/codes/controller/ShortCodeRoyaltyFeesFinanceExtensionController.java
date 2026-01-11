package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.dto.ShortCodeRoyaltyFeesExtensionDTO;
import atraintegratedsystems.codes.service.ShortCodeRoyaltyFeeFinanceExtensionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/royalty-fee-extension")
public class ShortCodeRoyaltyFeesFinanceExtensionController {

    private final ShortCodeRoyaltyFeeFinanceExtensionService service;

    public ShortCodeRoyaltyFeesFinanceExtensionController(
            ShortCodeRoyaltyFeeFinanceExtensionService service) {
        this.service = service;
    }

    @GetMapping("/unpaid")
    public String showUnpaidRoyaltyFeeExtensions(Model model) {
        model.addAttribute(
                "extensions",
                service.getUnpaidRoyaltyExtensions()
        );
        return "codes/finance/extension/shortcode/royalty_fee_extension_unpaid_list";
    }

    /* ===== SHOW PAY FORM ===== */
    @GetMapping("/pay/{id}")
    public String showPayForm(@PathVariable Long id, Model model) {
        model.addAttribute("extension", service.getById(id));
        return "codes/finance/extension/shortcode/royalty_fee_extension_pay";
    }

    /* ===== SAVE PAYMENT ===== */
    @PostMapping("/pay")
    public String savePayment(
            @ModelAttribute("extension")
            ShortCodeRoyaltyFeesExtensionDTO dto) {

        service.updatePayment(dto);
        return "redirect:/royalty-fee-extension/unpaid";
    }
}
