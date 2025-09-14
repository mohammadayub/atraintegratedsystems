package atraintegratedsystems.typeofapproval.controller;

import atraintegratedsystems.typeofapproval.service.TypeOfApprovalManufacturerDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class TypeOfApprovalManufacturerDetailController {

    private final TypeOfApprovalManufacturerDetailService service;

    // Show all manufacturers
    @GetMapping("/typeofapprovals/tacnumber/manufacturerlist")
    public String listManufacturers(Model model) {
        model.addAttribute("manufacturers", service.getAllManufacturers());
        return "typeofapprovals/tacnumber/manufacturerlist"; // Thymeleaf template
    }
}
