package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.dto.SmsIdentifierDetailDTO;
import atraintegratedsystems.codes.repository.SmsIdentifierCodeRepository;
import atraintegratedsystems.codes.service.SmsIdentifierDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sms-identifier-details")
public class SmsIdentifierDetailController {

    @Autowired
    private SmsIdentifierDetailService detailService;

    @Autowired
    private SmsIdentifierCodeRepository codeRepo;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("details", detailService.findAll());
        return "codes/smsidentifier/smsidentifier/sms_identifier_detail_list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("detail", new SmsIdentifierDetailDTO());
        model.addAttribute("codes", codeRepo.findByAssignStatusIsNullOrAssignStatusEquals(""));
        return "codes/smsidentifier/smsidentifier/sms_identifier_detail_form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("detail") SmsIdentifierDetailDTO dto) {
        detailService.save(dto);
        return "redirect:/sms-identifier-details";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("detail", detailService.findById(id));
        model.addAttribute("codes", codeRepo.findByAssignStatusIsNullOrAssignStatusEquals(""));
        return "codes/smsidentifier/smsidentifier/sms_identifier_detail_form";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute("detail") SmsIdentifierDetailDTO dto) {
        detailService.update(id, dto);
        return "redirect:/sms-identifier-details";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        detailService.delete(id);
        return "redirect:/sms-identifier-details";
    }
}
