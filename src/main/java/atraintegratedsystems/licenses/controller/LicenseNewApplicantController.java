package atraintegratedsystems.licenses.controller;

import atraintegratedsystems.licenses.dto.LicenseApplicantDTO;
import atraintegratedsystems.licenses.model.LicenseApplicant;
import atraintegratedsystems.licenses.service.LicenseNewApplicantService;
import atraintegratedsystems.licenses.service.LicenseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class LicenseNewApplicantController {

    @Autowired
    private LicenseNewApplicantService licenseService;

    @Autowired
    private LicenseTypeService licenseTypeService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE') or hasRole('ROLE_LICENSE_COMPLETION_PROFILE') or hasRole('ROLE_LICENSE_PROFILE_ENTRY')")
    @GetMapping("/licenses/license/registration/profile-entry/license_new_profile_list")
    public String showApplicationProfile(Model model) {
        List<LicenseApplicant> profiles = licenseService.getAllApplicants();
        model.addAttribute("profiles", profiles);
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        return "licenses/license/registration/profile-entry/license_new_profile_list";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE') or hasRole('ROLE_LICENSE_COMPLETION_PROFILE') or hasRole('ROLE_LICENSE_PROFILE_ENTRY')")
    @GetMapping("/licenses/license/registration/profile-entry/license_new_profile")
    public String showRegistrationForm(Model model) {
        model.addAttribute("profile", new LicenseApplicantDTO());
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        return "licenses/license/registration/profile-entry/license_new_profile";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE') or hasRole('ROLE_LICENSE_COMPLETION_PROFILE') or hasRole('ROLE_LICENSE_PROFILE_ENTRY')")
    @PostMapping("/licenses/license/registration/profile-entry/license_new_profile")
    public String saveProfile(@ModelAttribute("profile")  LicenseApplicantDTO dto,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            // If the reqDate is null, set it to the current date as a LocalDate
            model.addAttribute("licenseTypes", licenseTypeService.findAll());
            return "licenses/license/registration/profile-entry/license_new_profile";
        }
        try {
            licenseService.saveProfile(dto);
            redirectAttributes.addFlashAttribute("successMessage", "Profile registered successfully!");
            return "redirect:/licenses/license/registration/profile-entry/license_new_profile_list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e instanceof IOException
                    ? "An error occurred while saving the profile. Please try again."
                    : e.getMessage());
            model.addAttribute("profile", dto);
            model.addAttribute("licenseTypes", licenseTypeService.findAll());
            return "licenses/license/registration/profile-entry/license_new_profile";
        }
    }

    //Refer to Complete Profile
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE') or hasRole('ROLE_LICENSE_COMPLETION_PROFILE')")
    @GetMapping("/licenses/license/registration/completion-profile/license_applicant_complete_profile/{id}")
    public String completeProfile(@PathVariable Long id, Model model) {
        Optional<LicenseApplicant> profileOpt = licenseService.getApplicantById(id);
        if (profileOpt.isPresent()) {
            LicenseApplicant profile = profileOpt.get();
            LicenseApplicantDTO licenseApplicantDTO = new LicenseApplicantDTO();
            licenseApplicantDTO.setId(profile.getId());
            licenseApplicantDTO.setLicenseNo(profile.getLicenseNo());

            MultipartFile enidUpload=licenseApplicantDTO.getEnidUpload();
            licenseApplicantDTO.setEnidUpload(enidUpload);

            MultipartFile articleOfAssociation= licenseApplicantDTO.getArticleOfAssociationUpload();
            licenseApplicantDTO.setArticleOfAssociationUpload(articleOfAssociation);

            MultipartFile businessPlan= licenseApplicantDTO.getBusinessPlanUpload();
            licenseApplicantDTO.setBusinessPlanUpload(businessPlan);

            MultipartFile licenseUpload=licenseApplicantDTO.getLicenseUpload();
            licenseApplicantDTO.setLicenseUpload(licenseUpload);

            licenseApplicantDTO.setTinNo(profile.getTinNo());
            MultipartFile identityForm= licenseApplicantDTO.getIdentityFormUpload();
            licenseApplicantDTO.setIdentityFormUpload(identityForm);
            licenseApplicantDTO.setYearOfEstablishment(profile.getYearOfEstablishment());
            licenseApplicantDTO.setExpiryDate(profile.getExpiryDate());
            licenseApplicantDTO.setPlannedActivitiesAndServices(profile.getPlannedActivitiesAndServices());
            licenseApplicantDTO.setTotalNationalEmployees(profile.getTotalNationalEmployees());
            licenseApplicantDTO.setTotalInternationalEmployees(profile.getTotalInternationalEmployees());
            licenseApplicantDTO.setExpectedInvestment(profile.getExpectedInvestment());
            licenseApplicantDTO.setCash(profile.getCash());
            // Add the DTO to the model for the form
            model.addAttribute("licenseApplicant", licenseApplicantDTO);
        } else {
            // Handle case where applicant is not found
            model.addAttribute("error", "Applicant not found");
            return "error-page";
        }

        return "licenses/license/registration/completion-profile/license_applicant_complete_profile";
    }


    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE') or hasRole('ROLE_LICENSE_COMPLETION_PROFILE')")
    @PostMapping("/licenses/license/registration/completion-profile/license_applicant_complete_profile")
    public String updateCompleteProfile(
            @ModelAttribute("licenseApplicant") LicenseApplicantDTO dto,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "licenses/license/registration/completion-profile/license_applicant_complete_profile"; // View for re-editing
        }

        try {
            LicenseApplicant updatedProfile = licenseService.updateCompleteProfile(dto.getId(), dto);
            // Add success message
            model.addAttribute("message", "Profile updated successfully.");
            // Redirect to a success page
            return "redirect:/licenses/license/registration/send-profile-board/license_applicant_profile_send_board_list";

        } catch (IllegalArgumentException ex) {
            // Handle errors
            model.addAttribute("error", ex.getMessage());
            return "licenses/license/registration/completion-profile/license_applicant_complete_profile";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
