package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.dto.ShortCodeExtendedFeesExtensionDTO;
import atraintegratedsystems.codes.service.ShortCodeExtendedFeesFinanceExtensionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ShortCodeExtendedFeesFinanceExtensionController {

    private final ShortCodeExtendedFeesFinanceExtensionService service;

    public ShortCodeExtendedFeesFinanceExtensionController(
            ShortCodeExtendedFeesFinanceExtensionService service) {
        this.service = service;
    }

    @GetMapping("/extended-fee-extension/unpaid")
    public String showUnpaidApplicationFeeExtensions(Model model) {
        model.addAttribute(
                "extensions",
                service.getUnpaidExtensions()
        );
        return "codes/finance/shortcode/extension/shortcode/extended-fee-extension-list";

    }

    /* ===================== PAY FORM ===================== */
    @GetMapping("/extended-fee-extension/pay/{id}")
    public String showPayForm(@PathVariable Long id, Model model) {
        model.addAttribute("extension", service.getById(id));
        return "codes/finance/shortcode/extension/shortcode/extended_fee_extension_pay";
    }

    /* ===================== SAVE PAYMENT ===================== */
    @PostMapping("/extended-fee-extension/pay")
    public String savePayment(
            @ModelAttribute("extension")
            ShortCodeExtendedFeesExtensionDTO dto) {

        service.updatePayment(dto);
        return "redirect:/extended-fee-extension/unpaid";
    }
}
