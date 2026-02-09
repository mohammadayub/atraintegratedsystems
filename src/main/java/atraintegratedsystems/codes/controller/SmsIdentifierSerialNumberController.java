package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.dto.SmsIdentifierSerialNumberDTO;
import atraintegratedsystems.codes.service.SmsIdentifierSerialNumberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/codes/smsidentifier/serial-numbers")
public class SmsIdentifierSerialNumberController {

    private final SmsIdentifierSerialNumberService service;

    public SmsIdentifierSerialNumberController(SmsIdentifierSerialNumberService service) {
        this.service = service;
    }

    // LIST
    @GetMapping
    public String list(Model model) {
        model.addAttribute("list", service.findAll());
        return "codes/smsidentifier/serialnumber/list";
    }

    // CREATE FORM
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("serial", new SmsIdentifierSerialNumberDTO());
        return "codes/smsidentifier/serialnumber/form";
    }

    // SAVE
    @PostMapping("/save")
    public String save(@ModelAttribute("serial") SmsIdentifierSerialNumberDTO dto) {
        service.save(dto);
        return "redirect:/codes/smsidentifier/serial-numbers";
    }

    // EDIT
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("serial", service.findById(id));
        return "codes/smsidentifier/serialnumber/form";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/codes/smsidentifier/serial-numbers";
    }
}
