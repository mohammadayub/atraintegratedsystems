package atraintegratedsystems.typeofapproval.controller;

import atraintegratedsystems.typeofapproval.dto.TypeOfApprovalOrganizationDTO;
import atraintegratedsystems.typeofapproval.service.TypeOfApprovalOrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/organizations")
@RequiredArgsConstructor
public class TypeOfApprovalOrganizationController {

    private final TypeOfApprovalOrganizationService service;

    @GetMapping
    public String listOrganizations(Model model) {
        model.addAttribute("organizations", service.findAll());
        return "typeofapprovals/organizations/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("organization", new TypeOfApprovalOrganizationDTO());
        return "typeofapprovals/organizations/form";
    }

    @PostMapping("/save")
    public String saveOrganization(@Valid @ModelAttribute("organization") TypeOfApprovalOrganizationDTO dto,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "typeofapprovals/organizations/form";
        }
        if (service.existsByName(dto.getName())) {
            model.addAttribute("nameError", "Organization name already exists!");
            return "typeofapprovals/organizations/form";
        }
        service.save(dto);
        return "redirect:/organizations";
    }

    @GetMapping("/edit/{id}")
    public String editOrganization(@PathVariable int id, Model model) {
        TypeOfApprovalOrganizationDTO org = service.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid organization ID: " + id));
        model.addAttribute("organization", org);
        return "typeofapprovals/organizations/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrganization(@PathVariable int id) {
        service.deleteById(id);
        return "redirect:/organizations";
    }
}
