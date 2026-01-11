package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.dto.ShortCodeRoyaltyFeesExtensionDTO;
import atraintegratedsystems.codes.service.ShortCodeRoyaltyFeesExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ShortCodeRoyaltyFeesExtensionController {
    @Autowired
    private ShortCodeRoyaltyFeesExtensionService shortCodeRoyaltyFeesExtensionService;


    @GetMapping("/codes/finance/royalty-fee/list")
    public String royaltyFeeExtensionList(Model model) {

        model.addAttribute(
                "extensions",
                shortCodeRoyaltyFeesExtensionService.findUnPaidRoyaltyFeeForExtension()
        );

        return "codes/standard/extension/shortcode/royalty-fee-extended-list";
    }

    // ✅ OPEN FORM
    @GetMapping("/finance/code-extension/royalty-fee/create/{id}")
    public String createForm(
            @PathVariable("id") Long codeDetailId,
            Model model
    ) {
        model.addAttribute("codeDetailId", codeDetailId);
        model.addAttribute(
                "extension",
                new ShortCodeRoyaltyFeesExtensionDTO()
        );
        return "codes/standard/extension/shortcode/royalty-fee-extend-form";
    }

    // ✅ SAVE FORM
    @PostMapping("/finance/code-extension/royalty-fee/save/{id}")
    public String save(
            @PathVariable("id") Long codeDetailId,
            @ModelAttribute("extension")
            ShortCodeRoyaltyFeesExtensionDTO dto
    ) {
        shortCodeRoyaltyFeesExtensionService.saveRoyaltyFeeExtension(codeDetailId, dto);
        return "redirect:/codes/finance/royalty-fee/list";
    }
}
