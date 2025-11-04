package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.dto.CodeDTO;
import atraintegratedsystems.codes.model.Code;
import atraintegratedsystems.codes.repository.CodeRepository;
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
public class CodeController {

    @Autowired
    private CodeRepository codeRepository;

    /**
     * Displays the Short Codes Master Table page
     */

    @GetMapping("/codes/standard/shortcodes/home")
    public String CodesHome() {
        return "codes/standard/shortcode_home";
    }

    @GetMapping("/codes/standard/shortcodes")
    public String getCodes(Model model) {
        List<Object[]> codes = codeRepository.getCodeData();
        model.addAttribute("codes", codes);
        return "codes/standard/shortcodes_mastertable";
    }

    /**
     * Displays the Add Short Code form
     */
    @GetMapping("/codes/standard/shortcodes/add")
    public String showAddShortCodeForm(Model model) {
        model.addAttribute("codeDTO", new CodeDTO());
        List<Object[]> codes = codeRepository.getCodeData();
        model.addAttribute("codes", codes);
        return "codes/standard/shortcodes_mastertableadd";
    }

    /**
     * Handles form submission for adding a new Short Code
     */
    @PostMapping("/codes/standard/shortcodes/add")
    public String handleAddShortCode(
            @Valid @ModelAttribute("codeDTO") Code code,
            BindingResult bindingResult,
            Model model
    ) throws IOException {

        if (bindingResult.hasErrors()) {
            return "codes/standard/shortcodes_mastertableadd";
        }

        if (codeRepository.existsById(code.getId())) {
            model.addAttribute("errorMessage", "ID already exists. Please enter a unique ID.");
            return "codes/standard/shortcodes_mastertableadd";
        }

        codeRepository.save(code);
        return "redirect:/codes/standard/shortcodes";
    }
}
