package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.dto.ShortCodeDTO;
import atraintegratedsystems.codes.model.ShortCode;
import atraintegratedsystems.codes.repository.ShortCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
public class ShortCodeController {

    @Autowired
    private ShortCodeRepository shortCodeRepository;

    /**
     * Displays the Short Codes Master Table page
     */

    @GetMapping("/codes/standard/shortcodes/home")
    public String CodesHome() {
        return "codes/standard/shortcodes_home";
    }

    @GetMapping("/codes/standard/shortcodes")
    public String getCodes(Model model) {
        List<Object[]> codes = shortCodeRepository.getCodeData();
        model.addAttribute("codes", codes);
        return "codes/standard/shortcodes_mastertable";
    }

    /**
     * Displays the Add Short Code form
     */
    @GetMapping("/codes/standard/shortcodes/add")
    public String showAddShortCodeForm(Model model) {
        model.addAttribute("codeDTO", new ShortCodeDTO());
        List<Object[]> codes = shortCodeRepository.getCodeData();
        model.addAttribute("codes", codes);
        return "codes/standard/shortcodes_mastertableadd";
    }

    /**
     * Handles form submission for adding a new Short Code
     */
    @PostMapping("/codes/standard/shortcodes/add")
    public String handleAddShortCode(
            @Valid @ModelAttribute("codeDTO") ShortCode code,
            BindingResult bindingResult,
            Model model
    ) throws IOException {

        if (bindingResult.hasErrors()) {
            return "codes/standard/shortcodes_mastertableadd";
        }

        if (shortCodeRepository.existsById(code.getId())) {
            model.addAttribute("errorMessage", "ID already exists. Please enter a unique ID.");
            return "codes/standard/shortcodes_mastertableadd";
        }

        shortCodeRepository.save(code);
        return "redirect:/codes/standard/shortcodes";
    }
}
