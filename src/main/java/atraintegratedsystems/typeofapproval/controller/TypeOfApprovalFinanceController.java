package atraintegratedsystems.typeofapproval.controller;

import atraintegratedsystems.typeofapproval.model.TypeOfApprovalApplicant;
import atraintegratedsystems.typeofapproval.service.TypeOfApprovalApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class TypeOfApprovalFinanceController {

    @Autowired
    private TypeOfApprovalApplicantService typeOfApprovalApplicantService;


    @GetMapping("/typeofapprovals/finance/paymentlists")
    public String listTypeOfApprovalApplicants(Model model) {
        List<TypeOfApprovalApplicant> applicants = typeOfApprovalApplicantService.getAllReferred();
        model.addAttribute("applicants", applicants);
        return "typeofapprovals/finance/paymentlists";
    }


    @GetMapping("/typeofapprovals/finance/print/{id}")
    public String printList(@PathVariable Long id, Model model) {
        Optional<TypeOfApprovalApplicant> profileOpt = typeOfApprovalApplicantService.getById(id);

        if (profileOpt.isPresent()) {
            model.addAttribute("profile", profileOpt.get());
            return "typeofapprovals/finance/paymentslip"; // This matches your HTML file name/path
        } else {
            // Handle not found case (redirect to list or show error)
            return "redirect:/typeofapprovals/finance/paymentlists";
        }
    }

}
