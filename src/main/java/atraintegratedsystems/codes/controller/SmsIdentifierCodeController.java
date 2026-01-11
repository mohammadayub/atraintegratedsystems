package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.dto.SmsIdentifierCodeDTO;
import atraintegratedsystems.codes.service.SmsIdentifierCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sms-identifier-codes")
public class SmsIdentifierCodeController {

    @Autowired
    private SmsIdentifierCodeService service;

    /* LIST */
    @GetMapping
    public String list(Model model) {
        model.addAttribute("codes", service.findAll());
        return "codes/smsidentifier/smsidentifier/sms_identifier_code_list";
    }

    /* CREATE FORM */
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("smsCode", new SmsIdentifierCodeDTO());
        return "codes/smsidentifier/smsidentifier/sms_identifier_code_form";
    }

    /* SAVE */
    @PostMapping("/save")
    public String save(@ModelAttribute("smsCode") SmsIdentifierCodeDTO dto) {
        service.save(dto);
        return "redirect:/sms-identifier-codes";
    }

    /* EDIT FORM */
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("smsCode", service.findById(id));
        return "codes/smsidentifier/smsidentifier/sms_identifier_code_form";
    }

    /* UPDATE */
    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute("smsCode") SmsIdentifierCodeDTO dto) {
        service.update(id, dto);
        return "redirect:/sms-identifier-codes";
    }

    /* DELETE */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/sms-identifier-codes";
    }
}
