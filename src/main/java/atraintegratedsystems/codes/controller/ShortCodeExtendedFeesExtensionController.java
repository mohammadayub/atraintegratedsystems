package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.dto.ShortCodeExtendedFeesExtensionDTO;
import atraintegratedsystems.codes.model.ShortCodeDetail;
import atraintegratedsystems.codes.service.ShortCodeExtendedFeesExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/codes/extended/code-extension/extension-fee")
public class ShortCodeExtendedFeesExtensionController {

    @Autowired
    private ShortCodeExtendedFeesExtensionService extensionService;

    // -------------------------------
    // DEFAULT (PREVENT 404)
    // -------------------------------
    @GetMapping("")
    public String root() {
        return "redirect:/codes/extended/code-extension/extension-fee/list";
    }

    // -------------------------------
    // LIST
    // -------------------------------
    @GetMapping("/list")
    public String applicationFeeExtensionList(Model model) {

        model.addAttribute(
                "extensions",
                extensionService.findUnPaidApplicationFeeForExtension()
        );

        return "codes/standard/extension/shortcode/extended-fee-extension-list";
    }

    // -------------------------------
    // OPEN FORM
    // -------------------------------
    @GetMapping("/create/{id}")
    public String openForm(@PathVariable Long id, Model model) {

        ShortCodeDetail codeDetail = extensionService.getCodeDetailById(id);

        if (codeDetail == null) {
            model.addAttribute("errorMessage", "CodeDetail not found for ID: " + id);
            return "codes/error-page";
        }

        ShortCodeExtendedFeesExtensionDTO extensionDTO = new ShortCodeExtendedFeesExtensionDTO();

        // ✅ CORRECT FIELD
        extensionDTO.setExtendedFees(4000);

        model.addAttribute("codeDetail", codeDetail);
        model.addAttribute("extensionDTO", extensionDTO);

        return "codes/standard/extension/shortcode/extended-fee-extension-form";
    }

    // -------------------------------
    // SAVE
    // -------------------------------
    @PostMapping("/save/{id}")
    public String save(
            @PathVariable Long id,
            @ModelAttribute("extensionDTO") ShortCodeExtendedFeesExtensionDTO dto,
            Model model
    ) {

        try {

            // 🔍 DEBUG
            System.out.println("Extended Fees: " + dto.getExtendedFees());

            extensionService.saveExtension(id, dto);

            return "redirect:/codes/extended/code-extension/extension-fee/list";

        } catch (Exception e) {

            model.addAttribute("errorMessage", "Error saving extension: " + e.getMessage());

            ShortCodeDetail codeDetail = extensionService.getCodeDetailById(id);

            model.addAttribute("codeDetail", codeDetail);
            model.addAttribute("extensionDTO", dto);

            return "codes/standard/extension/shortcode/extended-fee-extension-form";
        }
    }
}