package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.dto.ShortCodeDTO;
import atraintegratedsystems.codes.service.ShortCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ShortCodeController {

    @Autowired
    private ShortCodeService shortCodeService;

    // Home page
    @GetMapping("/codes/standard/shortcodes/home")
    public String CodesHome() {
        return "codes/standard/shortcodes_home";
    }

    // List page
    @GetMapping("/codes/standard/shortcodes")
    public String getCodes(Model model) {

        model.addAttribute("codes", shortCodeService.getAllCodes());

        return "codes/standard/shortcodes_mastertable";
    }

    // Show add form
    @GetMapping("/codes/standard/shortcodes/add")
    public String showAddShortCodeForm(Model model) {

        model.addAttribute("codeDTO", new ShortCodeDTO());

        return "codes/standard/shortcodes_mastertableAdd";
    }

    // Save shortcode
    @PostMapping("/codes/standard/shortcodes/add")
    public String handleAddShortCode(
            @ModelAttribute("codeDTO") ShortCodeDTO dto,
            Model model) {

        try {

            shortCodeService.saveShortCode(dto);

        } catch (Exception e) {

            model.addAttribute("errorMessage", e.getMessage());
            return "codes/standard/shortcodes_mastertableAdd";
        }

        return "redirect:/codes/standard/shortcodes";
    }
}