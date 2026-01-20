package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.dto.ShortCodeApplicationFeesExtensionDTO;
import atraintegratedsystems.codes.service.ShortCodeApplicationFeesFinanceExtensionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ShortCodeApplicationFeesFinanceExtensionController {

    private final ShortCodeApplicationFeesFinanceExtensionService service;

    public ShortCodeApplicationFeesFinanceExtensionController(
            ShortCodeApplicationFeesFinanceExtensionService service) {
        this.service = service;
    }

    @GetMapping("/application-fee-extension/unpaid")
    public String showUnpaidApplicationFeeExtensions(Model model) {
        model.addAttribute(
                "extensions",
                service.getUnpaidExtensions()
        );
        return "codes/finance/shortcode/extension/shortcode/application-fee-extention-list";

    }

    /* ===================== PAY FORM ===================== */
    @GetMapping("/application-fee-extension/pay/{id}")
    public String showPayForm(@PathVariable Long id, Model model) {
        model.addAttribute("extension", service.getById(id));
        return "codes/finance/shortcode/extension/shortcode/application_fee_extension_pay";
    }

    /* ===================== SAVE PAYMENT ===================== */
    @PostMapping("/application-fee-extension/pay")
    public String savePayment(
            @ModelAttribute("extension")
            ShortCodeApplicationFeesExtensionDTO dto) {

        service.updatePayment(dto);
        return "redirect:/application-fee-extension/unpaid";
    }
}
