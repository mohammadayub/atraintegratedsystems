package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.dto.SmsIdentifierExtensionViewDTO;
import atraintegratedsystems.codes.service.SmsIdentifierExtensionPaidService;
import atraintegratedsystems.codes.service.SmsIdentifierExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SmsIdentifierExtensionPaidController {

    @Autowired
    private SmsIdentifierExtensionPaidService service;

//    // ✅ Show ALL data
//    @GetMapping("/sms/extensions")
//    public String viewAllExtensions(Model model) {
//        model.addAttribute("extensions", service.getAllExtensions());
//        return "sms/extensions-list";
//    }

    // ✅ Show only PAID
    @GetMapping("/codes/sms-identifer/extensions/paid/list")
    public String viewPaidExtensions(Model model) {
        model.addAttribute("extensions", service.getPaidExtensions());
        return "codes/smsidentifier/extension/paid/extensions-list";
    }

    @GetMapping("/sms/extensions/print/{id}")
    public String printExtension(@PathVariable Long id, Model model) {

        SmsIdentifierExtensionViewDTO ext = service.getAllExtensions()
                .stream()
                .filter(e -> e.getDetailId().equals(id))
                .findFirst()
                .orElse(null);

        model.addAttribute("ext", ext);
        return "codes/smsidentifier/extension/paid/extension-print";
    }
}