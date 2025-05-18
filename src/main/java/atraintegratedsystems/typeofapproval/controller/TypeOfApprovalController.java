package atraintegratedsystems.typeofapproval.controller;

import atraintegratedsystems.typeofapproval.dto.TypeOfApprovalFormDTO;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalManufacturerDetail;
import atraintegratedsystems.typeofapproval.repository.TypeOfApprovalApplicantRepository;
import atraintegratedsystems.typeofapproval.repository.TypeOfApprovalManufacturerDetailRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/typeofapprovals")
public class TypeOfApprovalController {

    private final TypeOfApprovalApplicantRepository applicantRepository;
    private final TypeOfApprovalManufacturerDetailRepository manufacturerRepository;

    public TypeOfApprovalController(TypeOfApprovalApplicantRepository applicantRepository,
                                    TypeOfApprovalManufacturerDetailRepository manufacturerRepository) {
        this.applicantRepository = applicantRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    @GetMapping("/form")
    public String showForm(Model model) {
        TypeOfApprovalFormDTO form = new TypeOfApprovalFormDTO();
        form.getManufacturers().add(new TypeOfApprovalManufacturerDetail()); // Add one empty row
        model.addAttribute("form", form);
        return "typeofapprovals/form";
    }

    @PostMapping("/submit")
    public String submitForm(@ModelAttribute TypeOfApprovalFormDTO form) {
        var applicant = form.getApplicant();
        var savedApplicant = applicantRepository.save(applicant);

        for (TypeOfApprovalManufacturerDetail manufacturer : form.getManufacturers()) {
            manufacturer.setApplicant(savedApplicant);
            manufacturerRepository.save(manufacturer);
        }

        return "redirect:/typeofapprovals/success";
    }

    @GetMapping("/success")
    public String successPage() {
        return "typeofapprovals/success";
    }
}
