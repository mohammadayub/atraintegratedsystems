package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.dto.SmsIdentifierDetailDTO;
import atraintegratedsystems.codes.service.SmsIdentifierRejectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

@Controller
public class SmsIdentifierRejectionController {
    @Autowired
    private SmsIdentifierRejectionService service;

//    @GetMapping("/rejection-list")
//    public String rejectionList(Model model) {
//
//        model.addAttribute(
//                "rejectionList",
//                service.getRejectionList()
//        );
//        return "codes/smsidentifier/rejection/sms_identifier_rejection_list";
//    }
@GetMapping("/sms-identifiers/active")
public String viewActiveSmsIdentifiers(Model model) {

    model.addAttribute(
            "smsIdentifierDetails",
            service.getActiveSmsIdentifierDetails()
    );

    return "codes/smsidentifier/rejection/sms_identifier_rejection_list";
}


    // 🔹 Show reject confirmation page
    // 🔹 SHOW REJECTION FORM
    @GetMapping("/sms-identifiers/reject/{id}")
    public String showRejectForm(@PathVariable Long id, Model model) {

        SmsIdentifierDetailDTO dto = service.getById(id);
        model.addAttribute("sms", dto);

        return "codes/smsidentifier/rejection/sms-identifier-reject";


    }

    // 🔹 Perform rejection
    // 🔹 SAVE REJECTION DATA
    @PostMapping("/sms-identifiers/reject")
    public String submitRejectForm(
            @ModelAttribute("sms") SmsIdentifierDetailDTO dto
    ) {
        service.rejectUsingDto(dto);
        return "redirect:/sms-identifiers/active";
    }
}