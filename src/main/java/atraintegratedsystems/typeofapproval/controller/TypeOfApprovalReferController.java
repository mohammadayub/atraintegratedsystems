package atraintegratedsystems.typeofapproval.controller;

import atraintegratedsystems.licenses.dto.LicenseApprovalDTO;
import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.typeofapproval.dto.TypeOfApprovalApplicantDTO;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalApplicant;
import atraintegratedsystems.typeofapproval.service.TypeOfApprovalApplicantService;
import atraintegratedsystems.utils.PersianCalendarUtils;
import atraintegratedsystems.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class TypeOfApprovalReferController {

    @Autowired
    private TypeOfApprovalApplicantService typeOfApprovalApplicantService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_typeofapprovals_ADMIN') or hasRole('ROLE_typeofapprovals_STANDARD')")
    @GetMapping("/typeofapprovals/refer/referlists")
    public String listTypeOfApprovalApplicants(Model model) {
        List<TypeOfApprovalApplicant> applicants = typeOfApprovalApplicantService.getAllTypeofApproval();
        model.addAttribute("applicants", applicants);
        return "typeofapprovals/refer/referlists";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_typeofapprovals_ADMIN') or hasRole('ROLE_typeofapprovals_STANDARD')")
    @GetMapping("/typeofapprovals/refer/referfinance/{id}")
    public String referForm(@PathVariable Long id, Model model) {
        Optional<TypeOfApprovalApplicant> profileOpt = typeOfApprovalApplicantService.getById(id);

        if (profileOpt.isPresent()) {
            TypeOfApprovalApplicant applicant = profileOpt.get();
            // Map entity to DTO
            TypeOfApprovalApplicantDTO dto = new TypeOfApprovalApplicantDTO();
            dto.setId(applicant.getId());
            dto.setRequestDate(applicant.getRequestDate());
            dto.setPerson(applicant.getPerson());
            dto.setCompanyName(applicant.getCompanyName());
            dto.setReferStatus(applicant.getReferStatus());

            // Convert referDate (Gregorian) to referDateJalali string for the form input
           dto.setReferDate(applicant.getReferDate());

            model.addAttribute("profile", dto);  // pass DTO, not entity!
            return "typeofapprovals/refer/referfinance";
        } else {
            return "redirect:/typeofapprovals/refer/referlists";
        }
    }


    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_typeofapprovals_ADMIN') or hasRole('ROLE_typeofapprovals_STANDARD')")
    @PostMapping("/typeofapprovals/refer/referfinance/save")
    public String updateRefer(@ModelAttribute TypeOfApprovalApplicantDTO typeOfApprovalApplicantDTO) {
        // Fetch the existing entity from the database
        TypeOfApprovalApplicant referApplicant = typeOfApprovalApplicantService.findById(typeOfApprovalApplicantDTO.getId());

        String[] parts = typeOfApprovalApplicantDTO.getReferDateJalali().split("-");
        int jYear = Integer.parseInt(parts[0]);
        int jMonth = Integer.parseInt(parts[1]);
        int jDay = Integer.parseInt(parts[2]);

        PersianCalendarUtils converter = new PersianCalendarUtils();
        LocalDate referDate = converter.jalaliToGregorian(jYear, jMonth, jDay);
        referApplicant.setReferDate(referDate);
        referApplicant.setEntryReferDate(LocalDate.now());
        String username = SecurityUtil.getCurrentUsername();
        referApplicant.setUserEntered(username);
        referApplicant.setReferStatus(typeOfApprovalApplicantDTO.getReferStatus());


        // Save the updated entity
        typeOfApprovalApplicantService.save(referApplicant);
        return "redirect:/typeofapprovals/refer/referlists";
    }

}