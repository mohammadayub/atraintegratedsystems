package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.dto.IspcDetailDTO;
import atraintegratedsystems.codes.service.IspcDetailService;
import atraintegratedsystems.codes.service.IspcRejectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/codes/ispc/details")
public class IspcRejectionController {

    @Autowired
    private IspcDetailService ispcDetailService;

    @Autowired
    private IspcRejectionService ispcRejectionService;




    @GetMapping("/rejected")
    public String rejectedList(Model model){
        model.addAttribute("rejects", ispcRejectionService.getRejectedList());
        return "codes/ispc/rejection/ispc_reject_list";
    }



    /* ================= REJECT FORM ================= */
    @GetMapping("/reject/{id}")
    public String rejectForm(@PathVariable Long id, Model model){
        model.addAttribute("detail", ispcDetailService.getById(id));
        return "codes/ispc/rejection/ispc_reject_form";
    }


    /* ================= SAVE REJECT ================= */

    @PostMapping("/reject/save")
    public String saveReject(@ModelAttribute("detail") IspcDetailDTO dto){

        ispcRejectionService.reject(dto);

        return "redirect:/codes/ispc/details";
    }


}