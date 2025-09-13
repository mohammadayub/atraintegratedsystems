package atraintegratedsystems.typeofapproval.controller;

import atraintegratedsystems.typeofapproval.dto.TypeOfApprovalTechnicalDetailDTO;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalApplicant;
import atraintegratedsystems.typeofapproval.service.GeneralReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class GeneralReportController {

    @Autowired
    private GeneralReportService generalReportService;


    // Show all applicants
    @GetMapping("/typeofapprovals/reports/general_report")
    public String showGeneralReport(Model model) {
        List<TypeOfApprovalApplicant> applicants = generalReportService.getAllTypeofApproval();
        model.addAttribute("applicants", applicants);
        return "typeofapprovals/reports/general_report"; // Thymeleaf file name
    }

    // Show all applicants
    @GetMapping("/typeofapprovals/reports/refer_report")
    public String showReferedReport(Model model) {
        List<TypeOfApprovalApplicant> applicants = generalReportService.getAllTypeofApproval();
        model.addAttribute("applicants", applicants);
        return "typeofapprovals/reports/refer_report"; // Thymeleaf file name
    }


    // Company Service based Report

    @GetMapping("/typeofapprovals/reports/company-services")
    public String showApprovedTechnicalDetails(Model model) {
        List<TypeOfApprovalTechnicalDetailDTO> details = generalReportService.getAllApprovedTechnicalDetails();
        model.addAttribute("details", details);
        return "typeofapprovals/reports/services_report";
    }
}