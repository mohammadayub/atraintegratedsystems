package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.dto.SmsIdentifierExtensionViewDTO;
import atraintegratedsystems.codes.service.SmsIdentifierExtensionPaidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SmsIdentifierExtensionPaidController {

    @Autowired
    private SmsIdentifierExtensionPaidService service;

    @GetMapping("/codes/sms-identifer/extensions/paid/list")
    public String viewPaidExtensions(Model model) {
        model.addAttribute("extensions", service.getPaidExtensions());
        return "codes/smsidentifier/extension/paid/extensions-list";
    }

    @GetMapping("/sms/extensions/print/{id}")
    public String printExtension(@PathVariable Long id, Model model) {
        SmsIdentifierExtensionViewDTO ext = service.getById(id);
        model.addAttribute("ext", ext);
        return "codes/smsidentifier/extension/paid/extension-print";
    }
}