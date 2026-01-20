package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.dto.SmsIdentifierDetailDTO;
import atraintegratedsystems.codes.service.SmsIdentifierApplicationFeesFinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SmsIdentifierApplicationFeeFinanceController {
    @Autowired
    private SmsIdentifierApplicationFeesFinanceService service;

    @GetMapping("/codes/sms-identifier-applicationfee/unpaid/list")
    public String pendingSmsIdentifiers(Model model) {
        model.addAttribute("list", service.getPendingSmsIdentifiers());
        return "codes/finance/smsidentifier/smsidentifier_payments/applicationfees/applicationfeeslist";
    }

    // Confirmation Section
    // 🔹 Show payment form
    @GetMapping("/codes/sms-identifier-applicationfee/pay/{id}")
    public String showPaymentForm(@PathVariable Long id, Model model) {

        SmsIdentifierDetailDTO dto = new SmsIdentifierDetailDTO();
        dto.setId(id);

        model.addAttribute("dto", dto);
        return "codes/finance/smsidentifier/smsidentifier_payments/applicationfees/applicationfeesconfirmation";
    }

    // 🔹 Save payment
    @PostMapping("/codes/sms-identifier-applicationfee/pay/save")
    public String savePayment(@ModelAttribute SmsIdentifierDetailDTO dto) {

        service.saveApplicationFees(dto);
        return "redirect:/codes/sms-identifier-applicationfee/unpaid/list";
    }

    // For Tariff
    @GetMapping("/codes/sms-identifier-applicationfee/print-tariff/{id}")
    public String printTariff(@PathVariable Long id, Model model) {

        model.addAttribute("tariff", service.getTariffById(id));
        return "codes/finance/smsidentifier/smsidentifier_payments/applicationfees/applicationfeestariff";
    }


}