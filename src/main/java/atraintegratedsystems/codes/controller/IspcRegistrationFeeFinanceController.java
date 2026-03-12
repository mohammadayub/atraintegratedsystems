package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.dto.IspcDetailDTO;
import atraintegratedsystems.codes.service.IspcRegistrationFeesFinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IspcRegistrationFeeFinanceController {
    @Autowired
    private IspcRegistrationFeesFinanceService service;

    @GetMapping("/codes/ispc-registrationfee/unpaid/list")
    public String pendingIspcCode(Model model) {
        model.addAttribute("list", service.getPendingIspcCodes());
        return "codes/finance/ispc/ispc_payments/registrationfeeslist";
    }

    // Confirmation Section
    // 🔹 Show payment form
    @GetMapping("/codes/ispc-registrationfee/pay/{id}")
    public String showPaymentForm(@PathVariable Long id, Model model) {

        IspcDetailDTO dto = service.getById(id); // fetch record
        dto.setId(id);
        model.addAttribute("dto", dto);
        return "codes/finance/ispc/ispc_payments/registrationfeesconfirmation";
    }

    // 🔹 Save payment
    @PostMapping("/codes/ispc-registrationfee/pay/save")
    public String savePayment(@ModelAttribute IspcDetailDTO dto) {

        service.saveRegistrationFees(dto);
        return "redirect:/codes/ispc-registrationfee/unpaid/list";
    }

//     For Tariff
    @GetMapping("/codes/ispc-registrationfee/print-tariff/{id}")
    public String printTariff(@PathVariable Long id, Model model) {

        model.addAttribute("tariff", service.getTariffById(id));
        return "codes/finance/ispc/ispc_payments/registrationfeestariff";
    }


}