package atraintegratedsystems.typeofapproval.controller;

import atraintegratedsystems.typeofapproval.service.TypeOfApprovalApplicantService;
import atraintegratedsystems.typeofapproval.service.TypeOfApprovalAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;

@Controller
public class TypeOfApprovalApplicantController {

    @Autowired
    private TypeOfApprovalApplicantService typeOfApprovalApplicantService;

    @Autowired
    private TypeOfApprovalAttachmentService attachmentService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TYPEOFAPPROVAL_ADMIN') or hasRole('ROLE_TYPEOFAPPROVAL_STANDARD')")
    @GetMapping("/typeofapprovals/applicant/applicantlists")
    public String listTypeOfApprovalApplicants(Model model) {
        model.addAttribute("applicants", typeOfApprovalApplicantService.getAllTypeOfApprovalApplicants());
        return "typeofapprovals/applicant/applicantlists";
    }

    // ===========================
    // File download utility
    // ===========================
    private ResponseEntity<Resource> serveFile(String filePath, String filename) {
        if (filePath == null || filePath.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        File file = new File(filePath);
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        FileSystemResource resource = new FileSystemResource(file);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .body(resource);
    }

    // Declaration of Conformity
    @GetMapping("/typeofapprovals/applicant/applicationlists/declarationdownload/{id}")
    public ResponseEntity<Resource> downloadDeclarationFile(@PathVariable Long id) {
        return attachmentService.findById(id)
                .map(a -> serveFile(a.getDeclarationOfConformity(), "declaration.pdf"))
                .orElse(ResponseEntity.notFound().build());
    }

    // Technical Operational Document of the RCE
    @GetMapping("/typeofapprovals/applicant/applicationlists/technicaloperation/{id}")
    public ResponseEntity<Resource> downloadTechnicalFile(@PathVariable Long id) {
        return attachmentService.findById(id)
                .map(a -> serveFile(a.getTechnicalOperationalDocOfTheRCE(), "technical_operation.pdf"))
                .orElse(ResponseEntity.notFound().build());
    }

    // Test Reports of Accredited Laboratory
    @GetMapping("/typeofapprovals/applicant/applicationlists/testreportdownload/{id}")
    public ResponseEntity<Resource> testReportsFile(@PathVariable Long id) {
        return attachmentService.findById(id)
                .map(a -> serveFile(a.getTestReportsOfAccreditedLaboratory(), "test_reports.pdf"))
                .orElse(ResponseEntity.notFound().build());
    }

    // Circuit Diagram PCB
    @GetMapping("/typeofapprovals/applicant/applicationlists/circuitdiagramdownload/{id}")
    public ResponseEntity<Resource> circuitDiagramFile(@PathVariable Long id) {
        return attachmentService.findById(id)
                .map(a -> serveFile(a.getCircuitDiagramPCB(), "circuit_diagram.pdf"))
                .orElse(ResponseEntity.notFound().build());
    }

    // Photographs
    @GetMapping("/typeofapprovals/applicant/applicationlists/photographdownload/{id}")
    public ResponseEntity<Resource> photographFile(@PathVariable Long id) {
        return attachmentService.findById(id)
                .map(a -> serveFile(a.getPhotographs(), "photographs.pdf"))
                .orElse(ResponseEntity.notFound().build());
    }

    // Label
    @GetMapping("/typeofapprovals/applicant/applicationlists/labeldownload/{id}")
    public ResponseEntity<Resource> labelFile(@PathVariable Long id) {
        return attachmentService.findById(id)
                .map(a -> serveFile(a.getLabel(), "label.pdf"))
                .orElse(ResponseEntity.notFound().build());
    }

    // Test Reports Issued by Accredited Testing
    @GetMapping("/typeofapprovals/applicant/applicationlists/testreprotsissueddownload/{id}")
    public ResponseEntity<Resource> testReportsIssuedFile(@PathVariable Long id) {
        return attachmentService.findById(id)
                .map(a -> serveFile(a.getTestReportsIssuedByAccreditedTesting(), "test_reports_issued.pdf"))
                .orElse(ResponseEntity.notFound().build());
    }
}