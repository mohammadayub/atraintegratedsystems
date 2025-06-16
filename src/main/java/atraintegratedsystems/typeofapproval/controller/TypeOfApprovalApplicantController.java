package atraintegratedsystems.typeofapproval.controller;
import atraintegratedsystems.licenses.model.LicenseApplicant;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalApplicant;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalAttachment;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class TypeOfApprovalApplicantController {

    @Autowired
    private TypeOfApprovalApplicantService typeOfApprovalApplicantService;

    @Autowired
    private TypeOfApprovalAttachmentService  attachmentService;


    @GetMapping("/typeofapprovals/applicant/applicantlists")
    public String listTypeOfApprovalApplicants(Model model) {
        List<TypeOfApprovalApplicant> applicants = typeOfApprovalApplicantService.getAllTypeOfApprovalApplicants();
        model.addAttribute("applicants", applicants);
        return "typeofapprovals/applicant/applicantlists";
    }


    @GetMapping("/typeofapprovals/applicant/applicationlists/declarationdownload/{id}")
    public ResponseEntity<ByteArrayResource> downloadDeclarationFile(@PathVariable Long id) {
        Optional<TypeOfApprovalAttachment> attachmentOpt = attachmentService.findById(id);

        if (!attachmentOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        TypeOfApprovalAttachment attachment = attachmentOpt.get();

        byte[] fileData = attachment.getDeclarationOfConformity();

        if (fileData == null || fileData.length == 0) {
            return ResponseEntity.noContent().build();
        }

        String fileExtension = getFileExtension(fileData);
        String mimeType = getMimeType(fileData);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"declaration" + fileExtension + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, mimeType);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileData.length)
                .body(new ByteArrayResource(fileData));
    }


    @GetMapping("/typeofapprovals/applicant/applicationlists/technicaloperation/{id}")
    public ResponseEntity<ByteArrayResource> downloadTechnicalFile(@PathVariable Long id) {
        Optional<TypeOfApprovalAttachment> attachmentOpt = attachmentService.findById(id);

        if (!attachmentOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        TypeOfApprovalAttachment attachment = attachmentOpt.get();

        byte[] fileData = attachment.getTechnicalOperationalDocOfTheRCE();

        if (fileData == null || fileData.length == 0) {
            return ResponseEntity.noContent().build();
        }

        String fileExtension = getFileExtension(fileData);
        String mimeType = getMimeType(fileData);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"declaration" + fileExtension + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, mimeType);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileData.length)
                .body(new ByteArrayResource(fileData));
    }

    @GetMapping("/typeofapprovals/applicant/applicationlists/testreportdownload/{id}")
    public ResponseEntity<ByteArrayResource> testReportsFile(@PathVariable Long id) {
        Optional<TypeOfApprovalAttachment> attachmentOpt = attachmentService.findById(id);

        if (!attachmentOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        TypeOfApprovalAttachment attachment = attachmentOpt.get();

        byte[] fileData = attachment.getTestReportsOfAccreditedLaboratory();

        if (fileData == null || fileData.length == 0) {
            return ResponseEntity.noContent().build();
        }

        String fileExtension = getFileExtension(fileData);
        String mimeType = getMimeType(fileData);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"declaration" + fileExtension + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, mimeType);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileData.length)
                .body(new ByteArrayResource(fileData));
    }


    @GetMapping("/typeofapprovals/applicant/applicationlists/circuitdiagramdownload/{id}")
    public ResponseEntity<ByteArrayResource> circuiteDiagramFile(@PathVariable Long id) {
        Optional<TypeOfApprovalAttachment> attachmentOpt = attachmentService.findById(id);

        if (!attachmentOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        TypeOfApprovalAttachment attachment = attachmentOpt.get();

        byte[] fileData = attachment.getCircuitDiagramPCB();

        if (fileData == null || fileData.length == 0) {
            return ResponseEntity.noContent().build();
        }

        String fileExtension = getFileExtension(fileData);
        String mimeType = getMimeType(fileData);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"declaration" + fileExtension + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, mimeType);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileData.length)
                .body(new ByteArrayResource(fileData));
    }

    @GetMapping("/typeofapprovals/applicant/applicationlists/photographdownload/{id}")
    public ResponseEntity<ByteArrayResource> photographFile(@PathVariable Long id) {
        Optional<TypeOfApprovalAttachment> attachmentOpt = attachmentService.findById(id);

        if (!attachmentOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        TypeOfApprovalAttachment attachment = attachmentOpt.get();

        byte[] fileData = attachment.getPhotographs();

        if (fileData == null || fileData.length == 0) {
            return ResponseEntity.noContent().build();
        }

        String fileExtension = getFileExtension(fileData);
        String mimeType = getMimeType(fileData);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"declaration" + fileExtension + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, mimeType);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileData.length)
                .body(new ByteArrayResource(fileData));
    }


    @GetMapping("/typeofapprovals/applicant/applicationlists/labeldownload/{id}")
    public ResponseEntity<ByteArrayResource> labelFile(@PathVariable Long id) {
        Optional<TypeOfApprovalAttachment> attachmentOpt = attachmentService.findById(id);

        if (!attachmentOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        TypeOfApprovalAttachment attachment = attachmentOpt.get();

        byte[] fileData = attachment.getLabel();

        if (fileData == null || fileData.length == 0) {
            return ResponseEntity.noContent().build();
        }

        String fileExtension = getFileExtension(fileData);
        String mimeType = getMimeType(fileData);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"declaration" + fileExtension + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, mimeType);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileData.length)
                .body(new ByteArrayResource(fileData));
    }

    @GetMapping("/typeofapprovals/applicant/applicationlists/testreprotsissueddownload/{id}")
    public ResponseEntity<ByteArrayResource> testreprotsissuedFile(@PathVariable Long id) {
        Optional<TypeOfApprovalAttachment> attachmentOpt = attachmentService.findById(id);

        if (!attachmentOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        TypeOfApprovalAttachment attachment = attachmentOpt.get();

        byte[] fileData = attachment.getTestReportsIssuedByAccreditedTesting();

        if (fileData == null || fileData.length == 0) {
            return ResponseEntity.noContent().build();
        }

        String fileExtension = getFileExtension(fileData);
        String mimeType = getMimeType(fileData);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"declaration" + fileExtension + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, mimeType);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileData.length)
                .body(new ByteArrayResource(fileData));
    }




    private String getFileExtension(byte[] fileData) {
        if (fileData == null || fileData.length < 4) {
            return ".bin"; // Default to binary if no data or too short
        }

        // Check for PDF files
        if (fileData.length > 4 && fileData[0] == 0x25 && fileData[1] == 0x50 &&
                fileData[2] == 0x44 && fileData[3] == 0x46) {
            return ".pdf";
        }

        // Check for JPEG files
        if (fileData.length > 2 && fileData[0] == (byte) 0xFF && fileData[1] == (byte) 0xD8) {
            return ".jpg";
        }

        // Check for PNG files
        if (fileData.length > 8 && fileData[0] == (byte) 0x89 && fileData[1] == (byte) 0x50 &&
                fileData[2] == (byte) 0x4E && fileData[3] == (byte) 0x47 &&
                fileData[4] == (byte) 0x0D && fileData[5] == (byte) 0x0A &&
                fileData[6] == (byte) 0x1A && fileData[7] == (byte) 0x0A) {
            return ".png";
        }

        // Check for JPG files (alternative signature)
        if (fileData.length > 2 && fileData[0] == (byte) 0xFF && fileData[1] == (byte) 0xD8 &&
                fileData[fileData.length - 2] == (byte) 0xFF && fileData[fileData.length - 1] == (byte) 0xD9) {
            return ".jpg";
        }

        return ".bin"; // Default to binary if no match
    }


    private String getMimeType(byte[] fileData) {
        // Determine MIME type based on file content
        // For example:
        if (fileData.length > 0 && fileData[0] == 0x25) {
            return "application/pdf"; // PDF
        } else if (fileData.length > 1 && fileData[0] == (byte) 0xFF && fileData[1] == (byte) 0xD8) {
            return "image/jpeg"; // JPEG
        } else if (fileData.length > 1 && fileData[0] == (byte) 0x89 && fileData[1] == (byte) 0x50) {
            return "image/png"; // PNG
        }
        return "application/octet-stream"; // Default to binary stream
    }



























}
