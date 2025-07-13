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
        List<TypeOfApprovalApplicant> applicants = typeOfApprovalApplicantService.findAllUnPaidAddminFee();
        model.addAttribute("applicants", applicants);
        return "typeofapprovals/finance/adminfeelist";
    }


    @GetMapping("/typeofapprovals/finance/certificatefeelist")
    public String listTypeOfApprovalCertificateFee(Model model) {
        List<TypeOfApprovalApplicant> applicants = typeOfApprovalApplicantService.findAllUnPaidCertificateFee();
        model.addAttribute("applicants", applicants);
        return "typeofapprovals/finance/certificatefeelist";
    }



    @GetMapping("/typeofapprovals/finance/payment/adminfeeconfirmation/{id}")
    public String listTypeOfApprovalAdminFeeConfirmation(@PathVariable Long id, Model model) {
        Optional<TypeOfApprovalApplicant> profileOpt = typeOfApprovalApplicantService.getById(id);

        if (profileOpt.isPresent()) {
            TypeOfApprovalApplicant applicant = profileOpt.get();
            // Map entity to DTO
            TypeOfApprovalApplicantDTO dto = new TypeOfApprovalApplicantDTO();
            dto.setId(applicant.getId());
            dto.setAdminFeeStatus(applicant.getAdminFeeStatus());
            dto.setAdminFeeBankVoucherNo(applicant.getAdminFeeBankVoucherNo());
            dto.setAdminFeeVoucherDate(applicant.getAdminFeeVoucherDate());
            dto.setAdminFeeBankVoucherSubmissionDate(applicant.getAdminFeeBankVoucherSubmissionDate());


            model.addAttribute("profile", dto);  // pass DTO, not entity!
            return "typeofapprovals/finance/payment/adminfeeconfirmation";
        } else {
            return "redirect:/typeofapprovals/finance/adminfeelist";
        }
    }

    @PostMapping("/typeofapprovals/finance/payment/adminfeeconfirmation/save")
    public String paymentAdminFee(@ModelAttribute TypeOfApprovalApplicantDTO typeOfApprovalApplicantDTO) {
        // Fetch the existing entity from the database
        TypeOfApprovalApplicant referApplicant = typeOfApprovalApplicantService.findById(typeOfApprovalApplicantDTO.getId());

        referApplicant.setAdminFeeBankVoucherNo(typeOfApprovalApplicantDTO.getAdminFeeBankVoucherNo());

        String[] parts = typeOfApprovalApplicantDTO.getAdminFeeVoucherDateJalali().split("-");
        int jYear = Integer.parseInt(parts[0]);
        int jMonth = Integer.parseInt(parts[1]);
        int jDay = Integer.parseInt(parts[2]);

        PersianCalendarUtils converter = new PersianCalendarUtils();
        LocalDate adminVoucherDate = converter.jalaliToGregorian(jYear, jMonth, jDay);
        referApplicant.setAdminFeeVoucherDate(adminVoucherDate);

        String[] subParts = typeOfApprovalApplicantDTO.getAdminFeeBankVoucherSubmissionDateJalali().split("-");
        int jSubYear = Integer.parseInt(subParts[0]);
        int jSubMonth = Integer.parseInt(subParts[1]);
        int jSubDay = Integer.parseInt(subParts[2]);

        LocalDate adminSubmissionDate = converter.jalaliToGregorian(jSubYear, jSubMonth, jSubDay);
        referApplicant.setAdminFeeBankVoucherSubmissionDate(adminSubmissionDate);


        referApplicant.setAdminFeeStatus(typeOfApprovalApplicantDTO.getAdminFeeStatus());

        // Save the updated entity
        typeOfApprovalApplicantService.save(referApplicant);
        return "redirect:/typeofapprovals/finance/adminfeelist";
    }


    @GetMapping("/typeofapprovals/finance/payment/applicationfeeconfirmation/{id}")
    public String listTypeOfApprovalApplicationFeeConfirmation(@PathVariable Long id, Model model) {
        Optional<TypeOfApprovalApplicant> profileOpt = typeOfApprovalApplicantService.getById(id);

        if (profileOpt.isPresent()) {
            TypeOfApprovalApplicant applicant = profileOpt.get();
            // Map entity to DTO
            TypeOfApprovalApplicantDTO dto = new TypeOfApprovalApplicantDTO();
            dto.setId(applicant.getId());
            dto.setApplicationFeeStatus(applicant.getApplicationFeeStatus());
            dto.setApplicationFeeBankVoucherNo(applicant.getApplicationFeeBankVoucherNo());
            dto.setApplicationFeeVoucherDate(applicant.getApplicationFeeVoucherDate());
            dto.setApplicationFeeBankVoucherSubmissionDate(applicant.getApplicationFeeBankVoucherSubmissionDate());


            model.addAttribute("profile", dto);  // pass DTO, not entity!
            return "typeofapprovals/finance/payment/applicationfeeconfirmation";
        } else {
            return "redirect:/typeofapprovals/finance/applicationfeelist";
        }
    }


    @PostMapping("/typeofapprovals/finance/payment/applicationfeeconfirmation/save")
    public String paymentApplicationFee(@ModelAttribute TypeOfApprovalApplicantDTO typeOfApprovalApplicantDTO) {
        // Fetch the existing entity from the database
        TypeOfApprovalApplicant referApplicant = typeOfApprovalApplicantService.findById(typeOfApprovalApplicantDTO.getId());

        referApplicant.setApplicationFeeBankVoucherNo(typeOfApprovalApplicantDTO.getApplicationFeeBankVoucherNo());

        String[] parts=typeOfApprovalApplicantDTO.getApplicationFeeVoucherDateJalali().split("-");
        int jYear = Integer.parseInt(parts[0]);
        int jMonth = Integer.parseInt(parts[1]);
        int jDay = Integer.parseInt(parts[2]);

        PersianCalendarUtils converter = new PersianCalendarUtils();
        LocalDate applicationVoucherDate = converter.jalaliToGregorian(jYear, jMonth, jDay);
        referApplicant.setApplicationFeeVoucherDate(applicationVoucherDate);

        String[] subParts = typeOfApprovalApplicantDTO.getApplicationFeeBankVoucherSubmissionDateJalali().split("-");
        int jSubYear = Integer.parseInt(subParts[0]);
        int jSubMonth = Integer.parseInt(subParts[1]);
        int jSubDay = Integer.parseInt(subParts[2]);

        LocalDate applicationSubmissionDate = converter.jalaliToGregorian(jSubYear, jSubMonth, jSubDay);
        referApplicant.setApplicationFeeBankVoucherSubmissionDate(applicationSubmissionDate);


        referApplicant.setApplicationFeeStatus(typeOfApprovalApplicantDTO.getApplicationFeeStatus());

        // Save the updated entity
        typeOfApprovalApplicantService.save(referApplicant);
        return "redirect:/typeofapprovals/finance/applicationfeelist";
    }


    @GetMapping("/typeofapprovals/finance/payment/certificatefeeconfirmation/{id}")
    public String listTypeOfApprovalCertificateFeeConfirmation(@PathVariable Long id, Model model) {
        Optional<TypeOfApprovalApplicant> profileOpt = typeOfApprovalApplicantService.getById(id);

        if (profileOpt.isPresent()) {
            TypeOfApprovalApplicant applicant = profileOpt.get();
            // Map entity to DTO
            TypeOfApprovalApplicantDTO dto = new TypeOfApprovalApplicantDTO();
            dto.setId(applicant.getId());
            dto.setCertificateFeeStatus(applicant.getCertificateFeeStatus());
            dto.setCertificateFeeBankVoucherNo(applicant.getCertificateFeeBankVoucherNo());
            dto.setCertificateFeeVoucherDate(applicant.getCertificateFeeVoucherDate());
            dto.setCertificateFeeBankVoucherSubmissionDate(applicant.getCertificateFeeBankVoucherSubmissionDate());


            model.addAttribute("profile", dto);  // pass DTO, not entity!
            return "typeofapprovals/finance/payment/certificatefeeconfirmation";
        } else {
            return "redirect:/typeofapprovals/finance/certificatefeelist";
        }
    }


    @PostMapping("/typeofapprovals/finance/payment/certificatefeeconfirmation/save")
    public String paymentCertificateFee(@ModelAttribute TypeOfApprovalApplicantDTO typeOfApprovalApplicantDTO) {
        // Fetch the existing entity from the database
        TypeOfApprovalApplicant referApplicant = typeOfApprovalApplicantService.findById(typeOfApprovalApplicantDTO.getId());

        referApplicant.setCertificateFeeBankVoucherNo(typeOfApprovalApplicantDTO.getCertificateFeeBankVoucherNo());

        String[] parts = typeOfApprovalApplicantDTO.getCertificateFeeVoucherDateJalali().split("-");
        int jYear = Integer.parseInt(parts[0]);
        int jMonth = Integer.parseInt(parts[1]);
        int jDay = Integer.parseInt(parts[2]);

        PersianCalendarUtils converter = new PersianCalendarUtils();
        LocalDate certificateVoucherDate = converter.jalaliToGregorian(jYear, jMonth, jDay);
        referApplicant.setCertificateFeeVoucherDate(certificateVoucherDate);

        String[] subParts = typeOfApprovalApplicantDTO.getCertificateFeeBankVoucherSubmissionDateJalali().split("-");
        int jSubYear = Integer.parseInt(subParts[0]);
        int jSubMonth = Integer.parseInt(subParts[1]);
        int jSubDay = Integer.parseInt(subParts[2]);

        LocalDate certificateSubmissionDate = converter.jalaliToGregorian(jSubYear, jSubMonth, jSubDay);
        referApplicant.setCertificateFeeBankVoucherSubmissionDate(certificateSubmissionDate);


        referApplicant.setCertificateFeeStatus(typeOfApprovalApplicantDTO.getCertificateFeeStatus());

        // Save the updated entity
        typeOfApprovalApplicantService.save(referApplicant);
        return "redirect:/typeofapprovals/finance/certificatefeelist";
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
