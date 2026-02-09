package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.dto.SmsIdentifierExtensionDTO;
import atraintegratedsystems.codes.service.SmsIdentifierFinanceExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;   // ✅ CORRECT
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SmsIdentifierFinanceExtensionController {

    @Autowired
    private SmsIdentifierFinanceExtensionService service;

    @GetMapping("/codes/smsidentifier/finance/extensions/list")
    public String financeExtensionList(Model model) {
        model.addAttribute("extensions", service.getFinanceExtensions());
        return "codes/finance/smsidentifier/smsidentifier_extension/extension-list";
    }

    /* OPEN UPDATE FORM */
    @GetMapping("/codes/smsidentifier/finance/extensions/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        SmsIdentifierExtensionDTO dto = service.getExtensionForUpdate(id);
        model.addAttribute("extension", dto);
        return "codes/finance/smsidentifier/smsidentifier_extension/extension-edit";
    }

    /* SUBMIT UPDATE */
    @PostMapping("/codes/smsidentifier/finance/extensions/update")
    public String updateExtension(
            @ModelAttribute("extension") SmsIdentifierExtensionDTO dto,
            Authentication authentication
    ) {
        String currentUser = authentication.getName(); // ✅ works now
        service.updateFinanceExtension(dto, currentUser);
        return "redirect:/codes/smsidentifier/finance/extensions/list";
    }
}
