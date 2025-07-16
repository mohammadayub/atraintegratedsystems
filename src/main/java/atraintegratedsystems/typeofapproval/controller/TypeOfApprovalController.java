package atraintegratedsystems.typeofapproval.controller;

import atraintegratedsystems.typeofapproval.dto.TypeOfApprovalFormDTO;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalManufacturerDetail;
import atraintegratedsystems.typeofapproval.service.TypeOfApprovalService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TypeOfApprovalController {

    private final TypeOfApprovalService approvalService;

    public TypeOfApprovalController(TypeOfApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_typeofapprovals_ADMIN') or hasRole('ROLE_typeofapprovals_ONLINE_APPLICATION') or hasRole('ROLE_typeofapprovals_STANDARD')")
    @GetMapping("/typeofapprovals/onlineapplicationform/form")
    public String showForm(Model model) {
        TypeOfApprovalFormDTO form = new TypeOfApprovalFormDTO();
        form.getManufacturers().add(new TypeOfApprovalManufacturerDetail()); // Add one empty row
        model.addAttribute("form", form);
        return "typeofapprovals/onlineapplicationform/form";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_typeofapprovals_ADMIN') or hasRole('ROLE_typeofapprovals_ONLINE_APPLICATION') or hasRole('ROLE_typeofapprovals_STANDARD')")
    @PostMapping("/typeofapprovals/onlineapplicationform/submit")
    public String submitForm(@ModelAttribute TypeOfApprovalFormDTO form, Model model) {
        try {
            approvalService.submitForm(form);
            return "redirect:/typeofapprovals/onlineapplicationform/success";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("form", form);
            model.addAttribute("errorMessage", ex.getMessage());
            return "typeofapprovals/onlineapplicationform/form";
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_typeofapprovals_ADMIN') or hasRole('ROLE_typeofapprovals_ONLINE_APPLICATION')")
    @GetMapping("/typeofapprovals/onlineapplicationform/success")
    public String successPage() {
        return "typeofapprovals/onlineapplicationform/success";
    }
}
