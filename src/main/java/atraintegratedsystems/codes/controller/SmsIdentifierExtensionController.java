package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.dto.SmsIdentifierExtensionDTO;
import atraintegratedsystems.codes.service.SmsIdentifierExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SmsIdentifierExtensionController {
    @Autowired
    private SmsIdentifierExtensionService smsIdentifierExtensionService;
    @GetMapping("/codes/sms-identifiers/extension/list")
    public String viewActiveSmsIdentifiers(Model model) {

        model.addAttribute(
                "smsIdentifierDetails",
                smsIdentifierExtensionService.getActiveSmsIdentifierDetails()
        );

        return "codes/smsidentifier/extension/sms_identifier_extension_list";
    }

    /* ================= OPEN FORM ================= */
    @GetMapping("/sms-identifier-extension/create/{id}")
    public String openExtensionForm(@PathVariable Long id, Model model) {

        model.addAttribute(
                "extension",
                smsIdentifierExtensionService.prepareExtensionForm(id)
        );
        model.addAttribute("detailId", id);

        return "codes/smsidentifier/extension/sms_identifier_extension_form";
    }

    /* ================= SAVE ================= */
    @PostMapping("/sms-identifier-extension/save/{id}")
    public String saveExtension(@PathVariable Long id,
                                @ModelAttribute("extension")
                                SmsIdentifierExtensionDTO dto) {

        smsIdentifierExtensionService.saveExtension(id, dto);

        return "redirect:/codes/sms-identifiers/extension/list";
    }
}
