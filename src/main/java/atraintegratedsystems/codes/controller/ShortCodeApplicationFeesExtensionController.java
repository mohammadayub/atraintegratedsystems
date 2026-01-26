package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.dto.ShortCodeApplicationFeesExtensionDTO;
import atraintegratedsystems.codes.model.CodeDetail;
import atraintegratedsystems.codes.model.ShortCodeApplicationFeesExtension;
import atraintegratedsystems.codes.repository.CodeDetailRepository;
import atraintegratedsystems.codes.service.ShortCodeApplicationFeesExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

@Controller
public class ShortCodeApplicationFeesExtensionController {

    @Autowired
    private CodeDetailRepository codeDetailRepository;
    @Autowired
    private ShortCodeApplicationFeesExtensionService shortCodeApplicationFeesExtensionService;


    @GetMapping("/codes/finance/application-fee/list")
    public String applicationFeeExtensionList(Model model) {

        model.addAttribute(
                "extensions",
                shortCodeApplicationFeesExtensionService.findUnPaidApplicationFeeForExtension()
        );

        return "codes/standard/extension/shortcode/application-fee-extended-list";
    }

    // Extension Section

    @GetMapping("/finance/code-extension/application-fee/create/{id}")
    public String openForm(@PathVariable Long id, Model model) {

        CodeDetail codeDetail = shortCodeApplicationFeesExtensionService.getCodeDetailById(id);

        model.addAttribute("codeDetail", codeDetail);
        model.addAttribute("extensionDTO", new ShortCodeApplicationFeesExtensionDTO());

        return "codes/standard/extension/shortcode/application-fee-extension-form";
    }

   // SAVE EXTENSION

    @PostMapping("/finance/code-extension/application-fee/save/{id}")
    public String save(
            @PathVariable Long id,
            @ModelAttribute("extensionDTO") ShortCodeApplicationFeesExtensionDTO dto
    ) {

        shortCodeApplicationFeesExtensionService.saveExtension(id, dto);

        return "redirect:/codes/finance/application-fee/list";
    }
}
