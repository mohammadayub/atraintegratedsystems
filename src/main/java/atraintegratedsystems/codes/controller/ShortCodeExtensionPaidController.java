package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.service.ShortCodeExtensionPaidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShortCodeExtensionPaidController {

    @Autowired
    private ShortCodeExtensionPaidService extensionService;

    // Paid Short Code Extensions List
    @GetMapping("/codes/extended/extensions/paid/list")
    public String getPaidShortCodeExtensions(Model model) {
        model.addAttribute("extensions", extensionService.getPaidShortCodeExtensions());
        return "codes/standard/extension/shortcode/paid/short_extension_paid_list";
    }
}