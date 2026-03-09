package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.dto.IspcDetailDTO;
import atraintegratedsystems.codes.repository.IspcCodeRepository;
import atraintegratedsystems.codes.service.IspcDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/codes/ispc/details")
public class IspcCodeDetailController {

    @Autowired
    private IspcDetailService service;

    @Autowired
    private IspcCodeRepository codeRepository;


    @GetMapping
    public String list(Model model){

        model.addAttribute("details",service.getAllDetails());
        return "codes/ispc/ispc_detail_list";
    }


    @GetMapping("/create")
    public String create(Model model){

        model.addAttribute("detail",new IspcDetailDTO());

        // SEND CODES
        model.addAttribute("codes",codeRepository.findAll());

        return "codes/ispc/ispc_detail_form";
    }


    @PostMapping("/save")
    public String save(@ModelAttribute("detail") IspcDetailDTO dto){

        service.save(dto);

        return "redirect:/codes/ispc/details";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){

        model.addAttribute("detail",service.getById(id));
        model.addAttribute("codes",codeRepository.findAll());

        return "codes/ispc/ispc_detail_form";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){

        service.delete(id);

        return "redirect:/codes/ispc/details";
    }
}