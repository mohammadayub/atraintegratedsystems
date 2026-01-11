package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.dto.CodeDetailDTO;
import atraintegratedsystems.codes.model.CodeDetail;
import atraintegratedsystems.codes.service.ShortCodeRejectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ShortCodeRejectionController {

    @Autowired
    private ShortCodeRejectionService shortCodeRejectionService;

    // SHOW REJECTION FORM
    @GetMapping("/codes/standard/shortcodes_details/reject/{id}")
    public String showRejectForm(@PathVariable Long id, Model model) {

        CodeDetail codeDetail = shortCodeRejectionService.getById(id);

        CodeDetailDTO dto = new CodeDetailDTO();
        dto.setId(codeDetail.getId());

        model.addAttribute("codeDetail", codeDetail);
        model.addAttribute("dto", dto);

        return "codes/standard/rejection/shortcode_rejection/reject-short-code";
    }

    // SUBMIT REJECTION
    @PostMapping("/codes/standard/shortcodes_details/reject/{id}")
    public String rejectShortCode(
            @PathVariable Long id,
            @ModelAttribute("dto") CodeDetailDTO dto
    ) {

        shortCodeRejectionService.rejectShortCode(id, dto);
        return "redirect:/codes/standard/shortcodes_details";
    }
}
