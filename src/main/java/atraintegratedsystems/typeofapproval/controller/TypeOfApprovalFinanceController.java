package atraintegratedsystems.typeofapproval.controller;

import atraintegratedsystems.typeofapproval.model.TypeOfApprovalApplicant;
import atraintegratedsystems.typeofapproval.service.TypeOfApprovalApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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

}
