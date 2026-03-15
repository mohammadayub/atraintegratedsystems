package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.dto.IspcExtensionDetailDTO;
import atraintegratedsystems.codes.service.IspcCodFinanceExtensionService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IspcCodeFinanceExtensionController {

    private final IspcCodFinanceExtensionService service;

    public IspcCodeFinanceExtensionController(IspcCodFinanceExtensionService service) {
        this.service = service;
    }

    /* LIST PAGE */
    @GetMapping("/codes/ispc/finance-extensions")
    public String extensionList(Model model) {

        model.addAttribute("extensions", service.getAllExtensions());

        return "codes/finance/ispc/ispc_extension/ispc-extension-list";
    }

    /* OPEN UPDATE PAGE */
    @GetMapping("/codes/ispc/finance-extension/edit/{id}")
    public String editExtension(@PathVariable Long id, Model model) {

        IspcExtensionDetailDTO dto = service.getExtensionForUpdate(id);

        model.addAttribute("extension", dto);

        return "codes/finance/ispc/ispc_extension/ispc-extension-finance-update";
    }

    /* SAVE UPDATE */
    @PostMapping("/codes/ispc/finance-extension/update")
    public String updateExtension(@ModelAttribute("extension") IspcExtensionDetailDTO dto,
                                  Authentication authentication) {

        String currentUser = authentication.getName();

        service.updateFinanceExtension(dto, currentUser);

        return "redirect:/codes/ispc/finance-extensions";
    }
}