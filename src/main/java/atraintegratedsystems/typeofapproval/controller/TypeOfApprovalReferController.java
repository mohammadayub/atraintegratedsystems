package atraintegratedsystems.typeofapproval.controller;

import atraintegratedsystems.typeofapproval.dto.TypeOfApprovalApplicantDTO;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalApplicant;
import atraintegratedsystems.typeofapproval.service.TypeOfApprovalApplicantService;
import atraintegratedsystems.utils.PersianCalendarUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class TypeOfApprovalReferController {

    @Autowired
    private TypeOfApprovalApplicantService typeOfApprovalApplicantService;

    @GetMapping("/typeofapprovals/refer/referlists")
    public String listTypeOfApprovalApplicants(Model model) {
        List<TypeOfApprovalApplicant> applicants = typeOfApprovalApplicantService.getAllTypeofApproval();
        model.addAttribute("applicants", applicants);
        return "typeofapprovals/refer/referlists";
    }

    @GetMapping("/typeofapprovals/refer/referfinance/{id}")
    public String editApplicantForm(@PathVariable Long id, Model model) {
        Optional<TypeOfApprovalApplicant> profileOpt = typeOfApprovalApplicantService.getById(id);

        if (profileOpt.isPresent()) {
            model.addAttribute("profile", profileOpt.get());
            return "typeofapprovals/refer/referfinance"; // This matches your HTML file name/path
        } else {
            // Handle not found case (redirect to list or show error)
            return "redirect:/typeofapprovals/refer/referlists";
        }
    }

    @PostMapping("/typeofapprovals/refer/referfinance/save")
    public String saveReferFinance(@ModelAttribute("profile") TypeOfApprovalApplicant applicant) {
        // Only pass the required fields
        typeOfApprovalApplicantService.updateReferFinance(
                applicant.getId(),
                applicant.getReferDateJalali(),
                applicant.getReferStatus()
        );
        return "redirect:/typeofapprovals/refer/referlists";
    }
}
