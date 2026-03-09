package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.model.IspcCode;
import atraintegratedsystems.codes.service.IspcCodeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/codes/ispc")
public class IspcCodeController {

    private final IspcCodeService service;

    public IspcCodeController(IspcCodeService service) {
        this.service = service;
    }

    // LIST
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("codes", service.findAll());
        return "codes/ispc/ispc_code_list";
    }

    // SHOW ADD FORM
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("ispcCode", new IspcCode());
        return "codes/ispc/ispc_code_form";
    }

    // SAVE
    @PostMapping("/save")
    public String save(@ModelAttribute IspcCode ispcCode) {
        service.save(ispcCode);
        return "redirect:/codes/ispc/list";
    }

    // SHOW UPDATE FORM
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("ispcCode", service.findById(id));
        return "codes/ispc/ispc_code_form";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/codes/ispc/list";
    }
}