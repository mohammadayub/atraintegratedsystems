package atraintegratedsystems.typeofapproval.controller;

import atraintegratedsystems.typeofapproval.service.TypeOfApprovalTechnicalDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class TypeOfApprovalTechnicalDetailController {

    @Autowired
    private TypeOfApprovalTechnicalDetailService service;

    // Show all manufacturers
    @GetMapping("/typeofapprovals/tacnumber/technicallist")
    public String listTechnicalDetail(Model model) {
        model.addAttribute("technicalDetails", service.getAllTechnicalDetails());
        return "typeofapprovals/tacnumber/technicallist"; // Thymeleaf template
    }
}
