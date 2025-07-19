package atraintegratedsystems.typeofapproval.controller;

import atraintegratedsystems.typeofapproval.service.TypeOfApprovalApplicantAttachmentService;
import atraintegratedsystems.typeofapproval.service.TypeOfApprovalApplicantService;
import atraintegratedsystems.typeofapproval.service.TypeOfApprovalAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

@Controller
public class TypeOfApprovalApplicantAttachmentController {

    @Autowired
    private TypeOfApprovalApplicantAttachmentService type;

    @Autowired
    private TypeOfApprovalAttachmentService attachmentService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_typeofapprovals_ADMIN') or hasRole('ROLE_typeofapprovals_STANDARD')")
    @GetMapping("/typeofapprovals/applicant/attachmentapplicantlists")
    public String listTypeOfApprovalApplicantsWithAttachment(Model model) {
        model.addAttribute("applicants", type.getTypeOfApprovalApplicantWithAttachment());
        return "typeofapprovals/applicant/attachmentapplicantlists";
    }



    // Common method to handle downloads
    private ResponseEntity<ByteArrayResource> serveFile(byte[] compressedData, String filename) {
        if (compressedData == null || compressedData.length == 0) {
            return ResponseEntity.noContent().build();
        }

        byte[] decompressedData;
        try {
            decompressedData = decompressFile(compressedData);
        } catch (IOException e) {
            String errorMessage = "The file has a problem.Please go back.";
            return ResponseEntity
                    .status(500)
                    .body(new ByteArrayResource(errorMessage.getBytes()));
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, getMimeType(decompressedData));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(decompressedData.length)
                .body(new ByteArrayResource(decompressedData));
    }

    // Declaration of Conformity
    @GetMapping("/typeofapprovals/applicant/attachmentapplicationlists/declarationdownload/{id}")
    public ResponseEntity<ByteArrayResource> downloadDeclarationFile(@PathVariable Long id) {
        return attachmentService.findById(id)
                .map(a -> serveFile(a.getDeclarationOfConformity(), "declaration.pdf"))
                .orElse(ResponseEntity.notFound().build());
    }

    // Technical Operational Document of the RCE
    @GetMapping("/typeofapprovals/applicant/attachmentapplicationlists/technicaloperation/{id}")
    public ResponseEntity<ByteArrayResource> downloadTechnicalFile(@PathVariable Long id) {
        return attachmentService.findById(id)
                .map(a -> serveFile(a.getTechnicalOperationalDocOfTheRCE(), "technical_operation.pdf"))
                .orElse(ResponseEntity.notFound().build());
    }

    // Test Reports of Accredited Laboratory
    @GetMapping("/typeofapprovals/applicant/attachmentapplicationlists/testreportdownload/{id}")
    public ResponseEntity<ByteArrayResource> testReportsFile(@PathVariable Long id) {
        return attachmentService.findById(id)
                .map(a -> serveFile(a.getTestReportsOfAccreditedLaboratory(), "test_reports.pdf"))
                .orElse(ResponseEntity.notFound().build());
    }

    // Circuit Diagram PCB
    @GetMapping("/typeofapprovals/applicant/attachmentapplicationlists/circuitdiagramdownload/{id}")
    public ResponseEntity<ByteArrayResource> circuitDiagramFile(@PathVariable Long id) {
        return attachmentService.findById(id)
                .map(a -> serveFile(a.getCircuitDiagramPCB(), "circuit_diagram.pdf"))
                .orElse(ResponseEntity.notFound().build());
    }

    // Photographs
    @GetMapping("/typeofapprovals/applicant/attachmentapplicationlists/photographdownload/{id}")
    public ResponseEntity<ByteArrayResource> photographFile(@PathVariable Long id) {
        return attachmentService.findById(id)
                .map(a -> serveFile(a.getPhotographs(), "photographs.pdf"))
                .orElse(ResponseEntity.notFound().build());
    }

    // Label
    @GetMapping("/typeofapprovals/applicant/attachmentapplicationlists/labeldownload/{id}")
    public ResponseEntity<ByteArrayResource> labelFile(@PathVariable Long id) {
        return attachmentService.findById(id)
                .map(a -> serveFile(a.getLabel(), "label.pdf"))
                .orElse(ResponseEntity.notFound().build());
    }

    // Test Reports Issued by Accredited Testing
    @GetMapping("/typeofapprovals/applicant/attachmentapplicationlists/testreprotsissueddownload/{id}")
    public ResponseEntity<ByteArrayResource> testReportsIssuedFile(@PathVariable Long id) {
        return attachmentService.findById(id)
                .map(a -> serveFile(a.getTestReportsIssuedByAccreditedTesting(), "test_reports_issued.pdf"))
                .orElse(ResponseEntity.notFound().build());
    }

    // ===========================
    // Utilities
    // ===========================

    private byte[] decompressFile(byte[] compressedData) throws IOException {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(compressedData);
             GZIPInputStream gzipIn = new GZIPInputStream(bais);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzipIn.read(buffer)) > 0) {
                baos.write(buffer, 0, len);
            }
            return baos.toByteArray();
        }
    }

    private String getMimeType(byte[] data) {
        if (data.length > 4 && data[0] == 0x25 && data[1] == 0x50 && data[2] == 0x44 && data[3] == 0x46) {
            return "application/pdf";
        } else if (data.length > 2 && data[0] == (byte) 0xFF && data[1] == (byte) 0xD8) {
            return "image/jpeg";
        } else if (data.length > 8 && data[0] == (byte) 0x89 && data[1] == (byte) 0x50) {
            return "image/png";
        }
        return "application/octet-stream";
    }
}
