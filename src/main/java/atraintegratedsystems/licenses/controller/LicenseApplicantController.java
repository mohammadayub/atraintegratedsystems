package atraintegratedsystems.licenses.controller;
import atraintegratedsystems.licenses.dto.LicenseApplicantDTO;
import atraintegratedsystems.licenses.model.LicenseApplicant;
import atraintegratedsystems.licenses.service.LicenseApplicantService;
import atraintegratedsystems.licenses.service.LicenseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Controller
public class LicenseApplicantController {

    @Autowired
    private LicenseApplicantService licenseService;

    @Autowired
    private LicenseTypeService licenseTypeService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE') or hasRole('ROLE_LICENSE_COMPLETION_PROFILE') or hasRole('ROLE_LICENSE_PROFILE_ENTRY')")
    @GetMapping("/licenses/license/registration/completion-profile/license_applicant_profile_list")
    public String showApplicationProfile(Model model) {
        List<LicenseApplicant> profiles = licenseService.getAllApplicants();
        model.addAttribute("profiles", profiles);
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        return "/licenses/license/registration/completion-profile/license_applicant_profile_list";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE') or hasRole('ROLE_LICENSE_PROFILE_ENTRY')")
    @GetMapping("/licenses/license/registration/send-profile-board/license_applicant_profile_send_board_list")
    public String showListProfile(Model model) {
        List<LicenseApplicant> profiles = licenseService.getAllApplicants();
        model.addAttribute("profiles", profiles);
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        return "licenses/license/registration/send-profile-board/license_applicant_profile_send_board_list";
    }


    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE') or hasRole('ROLE_LICENSE_COMPLETION_PROFILE')")
    @GetMapping("/licenses/license/registration/applicationDownload/{id}")
    public ResponseEntity<ByteArrayResource> downloadApplicationFile(@PathVariable Long id) {
        Optional<LicenseApplicant> profileOpt = licenseService.getApplicantById(id);
        if (!profileOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        LicenseApplicant profile = profileOpt.get();
        byte[] fileData = profile.getApplicationUpload();
        String fileName =  getFileExtension(profile.getApplicationUpload());
        String mimeType = getMimeType(fileData); // Get the correct MIME type
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, mimeType);

        ByteArrayResource resource = new ByteArrayResource(fileData);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileData.length)
                .body(resource);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE') or hasRole('ROLE_LICENSE_COMPLETION_PROFILE')")
    @GetMapping("/licenses/license/registration/licenseDownload/{id}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Long id) {
        Optional<LicenseApplicant> profileOpt = licenseService.getApplicantById(id);
        if (!profileOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        LicenseApplicant profile = profileOpt.get();
        byte[] fileData = profile.getLicenseUpload();
        String fileName =  getFileExtension(profile.getLicenseUpload());
        String mimeType = getMimeType(fileData); // Get the correct MIME type
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, mimeType);

        ByteArrayResource resource = new ByteArrayResource(fileData);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileData.length)
                .body(resource);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE') or hasRole('ROLE_LICENSE_COMPLETION_PROFILE')")
    @GetMapping("/licenses/license/registration/identityDownload/{id}")
    public ResponseEntity<ByteArrayResource> downloadTinFile(@PathVariable Long id) {
        Optional<LicenseApplicant> profileOpt = licenseService.getApplicantById(id);
        if (!profileOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        LicenseApplicant profile = profileOpt.get();
        byte[] fileData = profile.getIdentityFormUpload();
        String fileName =  getFileExtension(profile.getIdentityFormUpload());
        String mimeType = getMimeType(fileData); // Get the correct MIME type
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, mimeType);

        ByteArrayResource resource = new ByteArrayResource(fileData);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileData.length)
                .body(resource);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE') or hasRole('ROLE_LICENSE_COMPLETION_PROFILE')")
    @GetMapping("/licenses/license/registration/bankDownload/{id}")
    public ResponseEntity<ByteArrayResource> downloadBank(@PathVariable Long id) {
        Optional<LicenseApplicant> profileOpt = licenseService.getApplicantById(id);
        if (!profileOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        LicenseApplicant profile = profileOpt.get();
        byte[] fileData = profile.getBankStatementUpload();
        String fileName =  getFileExtension(profile.getBankStatementUpload());
        String mimeType = getMimeType(fileData); // Get the correct MIME type
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, mimeType);

        ByteArrayResource resource = new ByteArrayResource(fileData);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileData.length)
                .body(resource);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE') or hasRole('ROLE_LICENSE_COMPLETION_PROFILE')")
    @GetMapping("/licenses/license/registration/nidDownload/{id}")
    public ResponseEntity<ByteArrayResource> downloadNID(@PathVariable Long id) {
        Optional<LicenseApplicant> profileOpt = licenseService.getApplicantById(id);
        if (!profileOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        LicenseApplicant profile = profileOpt.get();
        byte[] fileData = profile.getEnidUpload();
        String fileName =  getFileExtension(profile.getEnidUpload());
        String mimeType = getMimeType(fileData); // Get the correct MIME type
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, mimeType);

        ByteArrayResource resource = new ByteArrayResource(fileData);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileData.length)
                .body(resource);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE') or hasRole('ROLE_LICENSE_COMPLETION_PROFILE')")
    @GetMapping("/licenses/license/registration/articleOfAssociationDownload/{id}")
    public ResponseEntity<ByteArrayResource> downloadArticleOfAssociation(@PathVariable Long id) {
        Optional<LicenseApplicant> profileOpt = licenseService.getApplicantById(id);
        if (!profileOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        LicenseApplicant profile = profileOpt.get();
        byte[] fileData = profile.getArticleOfAssociationUpload();
        String fileName =  getFileExtension(profile.getArticleOfAssociationUpload());
        String mimeType = getMimeType(fileData); // Get the correct MIME type
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, mimeType);

        ByteArrayResource resource = new ByteArrayResource(fileData);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileData.length)
                .body(resource);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE') or hasRole('ROLE_LICENSE_COMPLETION_PROFILE')")
    @GetMapping("/licenses/license/registration/businessPlanDownload/{id}")
    public ResponseEntity<ByteArrayResource> downloadBusinessPlan(@PathVariable Long id) {
        Optional<LicenseApplicant> profileOpt = licenseService.getApplicantById(id);
        if (!profileOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        LicenseApplicant profile = profileOpt.get();
        byte[] fileData = profile.getBusinessPlanUpload();
        String fileName =  getFileExtension(profile.getBusinessPlanUpload());
        String mimeType = getMimeType(fileData); // Get the correct MIME type
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, mimeType);

        ByteArrayResource resource = new ByteArrayResource(fileData);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileData.length)
                .body(resource);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE') or hasRole('ROLE_LICENSE_COMPLETION_PROFILE')")
    @GetMapping("/licenses/license/registration/proposalDownload/{id}")
    public ResponseEntity<ByteArrayResource> downloadProposal(@PathVariable Long id) {
        Optional<LicenseApplicant> profileOpt = licenseService.getApplicantById(id);
        if (!profileOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        LicenseApplicant profile = profileOpt.get();
        byte[] fileData = profile.getProposalUpload();
        String fileName =  getFileExtension(profile.getProposalUpload());
        String mimeType = getMimeType(fileData); // Get the correct MIME type
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
        headers.add(HttpHeaders.CONTENT_TYPE, mimeType);

        ByteArrayResource resource = new ByteArrayResource(fileData);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileData.length)
                .body(resource);
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

    //Refer to Board for Approval

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE') or hasRole('ROLE_LICENSE_COMPLETION_PROFILE')")
    @GetMapping("/licenses/license/registration/send-profile-board/license_applicants_send_board/{id}")
    public String referToBoard(@PathVariable Long id, Model model) {
        Optional<LicenseApplicant> profileOpt = licenseService.getApplicantById(id);

        if (profileOpt.isPresent()) {
            LicenseApplicant profile = profileOpt.get();
            LicenseApplicantDTO licenseApplicantDTO = new LicenseApplicantDTO();
            licenseApplicantDTO.setId(profile.getId());
            licenseApplicantDTO.setReqId(profile.getReqId());

            // Add the DTO to the model for the form
            model.addAttribute("licenseApplicant", licenseApplicantDTO);
        } else {
            // Handle case where applicant is not found
            model.addAttribute("error", "Applicant not found");
            return "error-page";
        }

        return "licenses/license/registration/send-profile-board/license_applicants_send_board";
    }


    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE') or hasRole('ROLE_LICENSE_COMPLETION_PROFILE')")
    @PostMapping("/licenses/license/registration/send-profile-board/license_applicants_send_board")
    public String updateProfile(
            @ModelAttribute("licenseApplicant") LicenseApplicantDTO dto,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "licenses/license/registration/send-profile-board/license_applicants_send_board"; // View for re-editing
        }

        try {
            LicenseApplicant updatedProfile = licenseService.SendToBoard(dto.getId(), dto);

            // Add success message
            model.addAttribute("message", "Profile updated successfully.");

            // Redirect to a success page
            return "redirect:/licenses/license/registration/send-profile-board/license_applicant_profile_send_board_list";

        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "licenses/license/registration/send-profile-board/license_applicants_send_board";
        } catch (IOException e) {
            model.addAttribute("error", "File upload failed. Please try again.");
            return "licenses/license/registration/send-profile-board/license_applicants_send_board";
        }
    }













}
