package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.dto.SmsIdentifierDetailDTO;
import atraintegratedsystems.codes.repository.SmsIdentifierCodeRepository;
import atraintegratedsystems.codes.service.SmsIdentifierDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/codes/sms-identifier/details")
public class SmsIdentifierDetailController {

    @Autowired
    private SmsIdentifierDetailService service;

    @Autowired
    private SmsIdentifierCodeRepository codeRepository;

    /* ================= LIST ================= */

    @GetMapping
    public String list(Model model) {
        model.addAttribute("details", service.getAll());
        return "codes/smsidentifier/smsidentifier/sms_identifier_detail_list";
    }

    /* ================= CREATE ================= */

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("detail", new SmsIdentifierDetailDTO());
        model.addAttribute("codes", codeRepository.findAll());
        return "codes/smsidentifier/smsidentifier/sms_identifier_detail_form";
    }

    /* ================= SAVE ================= */

    @PostMapping("/save")
    public String save(@ModelAttribute("detail") SmsIdentifierDetailDTO dto) {
        service.save(dto);
        return "redirect:/codes/sms-identifier/details";
    }

    /* ================= EDIT ================= */

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("detail", service.getById(id));
        model.addAttribute("codes", codeRepository.findAll());
        return "codes/smsidentifier/smsidentifier/sms_identifier_detail_form";
    }

    /* ================= UPDATE ================= */

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute("detail") SmsIdentifierDetailDTO dto) {

        service.update(id, dto);
        return "redirect:/codes/sms-identifier/details";
    }

    /* ================= DELETE ================= */

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/codes/sms-identifier/details";
    }

    /* ================= PRINT ================= */

    @GetMapping("/print/{id}")
    public String print(@PathVariable Long id, Model model) {
        SmsIdentifierDetailDTO dto = service.getById(id);
        model.addAttribute("detail", dto);
        return "codes/smsidentifier/smsidentifier/sms_identifier_detail_print";
    }
}