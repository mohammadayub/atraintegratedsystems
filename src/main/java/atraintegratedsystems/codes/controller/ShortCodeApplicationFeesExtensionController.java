package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.dto.ShortCodeApplicationFeesExtensionDTO;
import atraintegratedsystems.codes.model.ShortCodeDetail;
import atraintegratedsystems.codes.service.ShortCodeApplicationFeesExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/codes/finance/code-extension/application-fee")
public class ShortCodeApplicationFeesExtensionController {

    @Autowired
    private ShortCodeApplicationFeesExtensionService extensionService;

    // -------------------------------
    // LIST UNPAID EXTENSIONS
    // -------------------------------
    @GetMapping("/list")
    public String applicationFeeExtensionList(Model model) {
        model.addAttribute(
                "extensions",
                extensionService.findUnPaidApplicationFeeForExtension()
        );
        return "codes/standard/extension/shortcode/application-fee-extended-list";
    }

    // -------------------------------
    // OPEN CREATE FORM
    // -------------------------------
    @GetMapping("/create/{id}")
    public String openForm(@PathVariable Long id, Model model) {
        try {
            // Fetch code detail
            ShortCodeDetail codeDetail = extensionService.getCodeDetailById(id);

            // Create DTO with default values
            ShortCodeApplicationFeesExtensionDTO extensionDTO = new ShortCodeApplicationFeesExtensionDTO();
            extensionDTO.setApplicationFeeExtendedFees(4000); // default fee

            // Add to model
            model.addAttribute("codeDetail", codeDetail);
            model.addAttribute("extensionDTO", extensionDTO);

            return "codes/standard/extension/shortcode/application-fee-extension-form";

        } catch (RuntimeException e) {
            // If code detail not found, show error page
            model.addAttribute("errorMessage", "CodeDetail not found for ID: " + id);
            return "codes/error-page"; // create a simple error page template
        }
    }

    // -------------------------------
    // SAVE EXTENSION
    // -------------------------------
    @PostMapping("/save/{id}")
    public String save(
            @PathVariable Long id,
            @ModelAttribute("extensionDTO") ShortCodeApplicationFeesExtensionDTO dto,
            Model model
    ) {
        try {
            extensionService.saveExtension(id, dto);
            return "redirect:/codes/finance/code-extension/application-fee/list";

        } catch (RuntimeException e) {
            // Handle errors (e.g., null date fields, invalid data)
            model.addAttribute("errorMessage", "Error saving extension: " + e.getMessage());

            // Re-populate the form
            model.addAttribute("codeDetail", extensionService.getCodeDetailById(id));
            model.addAttribute("extensionDTO", dto);

            return "codes/standard/extension/shortcode/application-fee-extension-form";
        }
    }
}
