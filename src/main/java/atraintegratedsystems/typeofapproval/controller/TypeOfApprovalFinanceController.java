package atraintegratedsystems.typeofapproval.controller;

import atraintegratedsystems.typeofapproval.model.TypeOfApprovalApplicant;
import atraintegratedsystems.typeofapproval.service.TypeOfApprovalApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class TypeOfApprovalFinanceController {

    @Autowired
    private TypeOfApprovalApplicantService typeOfApprovalApplicantService;


//    My code
    @GetMapping("/typeofapprovals/finance/home")
    public String listTypeOfApprovalFinanceHome(Model model) {
        return "typeofapprovals/finance/home";
    }
    @GetMapping("/typeofapprovals/finance/applicationfeelist")
    public String listTypeOfApprovalApplicantFee(Model model) {
        List<TypeOfApprovalApplicant> applicants = typeOfApprovalApplicantService.getAllReferred();
        model.addAttribute("applicants", applicants);
        return "typeofapprovals/finance/applicationfeelist";
    }
    @GetMapping("/typeofapprovals/finance/adminfeelist")
    public String listTypeOfApprovalAdminFee(Model model) {
        List<TypeOfApprovalApplicant> applicants = typeOfApprovalApplicantService.getAllReferred();
        model.addAttribute("applicants", applicants);
        return "typeofapprovals/finance/adminfeelist";
    }

    @PostMapping("/typeofapprovals/finance/payment/applicationfeeconfirmation/save")
    public String saveReferFinance(@ModelAttribute("profile") TypeOfApprovalApplicant applicant) {
        // Only pass the required fields
        typeOfApprovalApplicantService.updateApplicationFee(
                applicant.getId(),
                applicant.getApplicationFeeStatus(),
                applicant.getApplicationFeeBankVoucherNo(),
                applicant.getApplicationFeeVoucherDate(),
                applicant.getApplicationFeeBankVoucherSubmissionDate()
        );
        return "redirect:/typeofapprovals/finance/applicationfeelist";
    }

    @PostMapping("/typeofapprovals/finance/payment/adminfeeconfirmation/save")
    public String saveAdminFinance(@ModelAttribute("profile") TypeOfApprovalApplicant applicant) {
        // Only pass the required fields
        typeOfApprovalApplicantService.updateAdminFee(
                applicant.getId(),
                applicant.getAdminFeeStatus(),
                applicant.getAdminFeeBankVoucherNo(),
                applicant.getAdminFeeVoucherDate(),
                applicant.getAdminFeeBankVoucherSubmissionDate()
        );
        return "redirect:/typeofapprovals/finance/adminfeelist";
    }

    @PostMapping("/typeofapprovals/finance/payment/certificatefeeconfirmation/save")
    public String saveCertificateFinance(@ModelAttribute("profile") TypeOfApprovalApplicant applicant) {
        // Only pass the required fields
        typeOfApprovalApplicantService.updateCertificateFee(
                applicant.getId(),
                applicant.getCertificateFeeStatus(),
                applicant.getCertificateFeeBankVoucherNo(),
                applicant.getCertificateFeeVoucherDate(),
                applicant.getCertificateFeeBankVoucherSubmissionDate()
        );
        return "redirect:/typeofapprovals/finance/certificatefeelist";
    }




    @GetMapping("/typeofapprovals/finance/certificatefeelist")
    public String listTypeOfApprovalCertificateFee(Model model) {
        List<TypeOfApprovalApplicant> applicants = typeOfApprovalApplicantService.getAllReferred();
        model.addAttribute("applicants", applicants);
        return "typeofapprovals/finance/certificatefeelist";
    }

//    Payment Section
    @GetMapping("/typeofapprovals/finance/payment/adminfeeconfirmation/{id}")
    public String listTypeOfApprovalAdminFeeConfirmation(@PathVariable Long id,Model model) {

        Optional<TypeOfApprovalApplicant> profileOpt = typeOfApprovalApplicantService.getById(id);

        if (profileOpt.isPresent()) {
            model.addAttribute("profile", profileOpt.get());
            return "/typeofapprovals/finance/payment/adminfeeconfirmation";        }
        else {
            // Handle not found case (redirect to list or show error)
            return "redirect:/typeofapprovals/finance/adminfeelist";
        }

    }

    @GetMapping("/typeofapprovals/finance/payment/applicationfeeconfirmation/{id}")
    public String listTypeOfApprovalApplicationFeeConfirmation(@PathVariable Long id,Model model) {
        Optional<TypeOfApprovalApplicant> profileOpt = typeOfApprovalApplicantService.getById(id);

        if (profileOpt.isPresent()) {
            model.addAttribute("profile", profileOpt.get());
            return "/typeofapprovals/finance/payment/applicationfeeconfirmation";        }
        else {
            // Handle not found case (redirect to list or show error)
            return "redirect:/typeofapprovals/finance/applicationfeelist";
        }

    }

    @GetMapping("/typeofapprovals/finance/payment/certificatefeeconfirmation/{id}")
    public String listTypeOfApprovalCertificateFeeConfirmation(@PathVariable Long id,Model model) {
        Optional<TypeOfApprovalApplicant> profileOpt = typeOfApprovalApplicantService.getById(id);

        if (profileOpt.isPresent()) {
            model.addAttribute("profile", profileOpt.get());
            return "/typeofapprovals/finance/payment/certificatefeeconfirmation";        }
        else {
            // Handle not found case (redirect to list or show error)
            return "redirect:/typeofapprovals/finance/certificatefeelist";
        }

    }

//    End of My code
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
