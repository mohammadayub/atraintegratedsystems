package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.dto.IspcExtensionViewDTO;
import atraintegratedsystems.codes.service.IspcCodeExtensionPaidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class IspcExtensionPaidController {

    @Autowired
    private IspcCodeExtensionPaidService ispcCodeExtensionPaidService;

    @GetMapping("/codes/ispc/extensions/paid-list")
    public String getPaidExtensions(Model model) {

        List<IspcExtensionViewDTO> list =
                ispcCodeExtensionPaidService.getActiveIspcDetailCode();

        model.addAttribute("extensions", list);

        return "codes/ispc/extension/paid/extension-list";
    }
    @GetMapping("/codes/ispc/extensions/print/{id}")
    public String printPage(@PathVariable Long id, Model model) {

        IspcExtensionViewDTO dto = ispcCodeExtensionPaidService.getById(id);

        model.addAttribute("ext", dto);

        return "codes/ispc/extension/paid/extension-print";
    }
}