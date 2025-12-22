package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.model.ShortCodeSerialNumber;
import atraintegratedsystems.codes.service.ShortCodeSerialNumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/serial-numbers")
@RequiredArgsConstructor
public class ShortCodeSerialNumberController {

    private final ShortCodeSerialNumberService service;

    // 📌 LIST
    @GetMapping
    public String list(Model model) {
        model.addAttribute("serialNumbers", service.findAll());
        return "codes/standard/shortserailnumber/list";
    }

    // 📌 ADD FORM
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("serialNumber", new ShortCodeSerialNumber());
        return "codes/standard/shortserailnumber/form";
    }

    // 📌 SAVE
    @PostMapping("/save")
    public String save(@ModelAttribute ShortCodeSerialNumber serialNumber) {
        service.save(serialNumber);
        return "redirect:/serial-numbers";
    }

    // 📌 EDIT FORM
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("serialNumber", service.findById(id));
        return "codes/standard/shortserailnumber/form";
    }

    // 📌 DELETE
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/serial-numbers";
    }
}
