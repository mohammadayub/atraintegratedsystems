package atraintegratedsystems.typeofapproval.controller;

import atraintegratedsystems.typeofapproval.dto.ApplicantCertificateDTO;
import atraintegratedsystems.typeofapproval.service.CertificateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class CertificateController {

    private final CertificateService service;

    public CertificateController(CertificateService service) {
        this.service = service;
    }

    @GetMapping("/typeofapprovals/certificate/certificate-list/{id}")
    public String listCertificates(@PathVariable("id") Long companyId, Model model) {
        List<ApplicantCertificateDTO> certificates = service.getApprovedCertificatesByCompanyId(companyId);
        model.addAttribute("certificates", certificates);
        return "typeofapprovals/certificate/certificate-list";
    }

    @GetMapping("/typeofapprovals/certificate/certificate-list-design")
    public String certificateDesign() {
        return "typeofapprovals/certificate/certificate-design";
    }
}
