package atraintegratedsystems.licenses.controller;
import atraintegratedsystems.licenses.dto.LicenseApplicantDTO;
import atraintegratedsystems.licenses.model.LicenseApplicant;
import atraintegratedsystems.licenses.service.LicenseApplicantService;
import atraintegratedsystems.licenses.service.LicenseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/Licenses/License/Registration/LicenseApplicationProfile")
    public String showApplicationProfile(Model model) {
        List<LicenseApplicant> profiles = licenseService.getAllApplicants();
        model.addAttribute("profiles", profiles);
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        return "Licenses/License/Registration/LicenseApplicationProfile";
    }


    @GetMapping("/licenses/license/registration/license_new_applicant")
    public String showRegistrationForm(Model model) {
        model.addAttribute("profile", new LicenseApplicantDTO());
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        return "licenses/license/registration/license_new_applicant";
    }

    @PostMapping("/licenses/license/registration/license_new_applicant")
    public String saveProfile(@ModelAttribute("profile") LicenseApplicantDTO dto, Model model) {
        try {
            licenseService.saveProfile(dto);
            model.addAttribute("successMessage", "Profile registered successfully!");
        } catch (IOException e) {
            model.addAttribute("errorMessage", "An error occurred while saving the profile. Please try again.");
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        model.addAttribute("profile", new LicenseApplicantDTO());
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        return "licenses/license/registration/license_new_applicant";
    }


    @GetMapping("/Licenses/License/Registration/license/{id}")
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