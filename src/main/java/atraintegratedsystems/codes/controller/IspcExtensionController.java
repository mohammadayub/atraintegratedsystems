package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.dto.IspcExtensionDetailDTO;
import atraintegratedsystems.codes.service.IspcCodeExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IspcExtensionController {

    @Autowired
    private IspcCodeExtensionService ispcCodeExtensionService;

    /* ================= LIST ACTIVE ISPC CODES ================= */
    @GetMapping("/codes/ispc/extension/list")
    public String viewActiveIspcCodes(Model model) {

        model.addAttribute(
                "ispcDetails",
                ispcCodeExtensionService.getActiveIspcDetailCode()
        );

        return "codes/ispc/extension/ispc_extension_list";
    }

    /* ================= OPEN EXTENSION FORM ================= */
    @GetMapping("/codes/ispc-extension/create/{id}")
    public String openExtensionForm(@PathVariable Long id, Model model) {

        model.addAttribute(
                "extension",
                ispcCodeExtensionService.prepareExtensionForm(id)
        );

        model.addAttribute("detailId", id);

        return "codes/ispc/extension/ispc_extension_form";
    }

    /* ================= SAVE EXTENSION ================= */
    @PostMapping("/codes/ispc-extension/save/{id}")
    public String saveExtension(@PathVariable Long id,
                                @ModelAttribute("extension") IspcExtensionDetailDTO dto) {

        ispcCodeExtensionService.saveExtension(id, dto);

        return "redirect:/codes/ispc/extension/list";
    }
}