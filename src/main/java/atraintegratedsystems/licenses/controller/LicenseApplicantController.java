package atraintegratedsystems.licenses.controller;

import atraintegratedsystems.licenses.dto.LicenseApplicantDTO;
import atraintegratedsystems.licenses.model.LicenseApplicant;
import atraintegratedsystems.licenses.service.LicenseApplicantService;
import atraintegratedsystems.licenses.service.LicenseTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
public class LicenseApplicantController {

    @Autowired
    private LicenseApplicantService licenseService;

    @Autowired
    private LicenseTypeService licenseTypeService;

    // =========================================================
    // LIST PROFILE
    // =========================================================

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_ADMIN') or hasRole('ROLE_LICENSE') or hasRole('ROLE_LICENSE_COMPLETION_PROFILE') or hasRole('ROLE_LICENSE_PROFILE_ENTRY')")
    @GetMapping("/licenses/license/registration/completion-profile/license_applicant_profile_list")
    public String showApplicationProfile(Model model) {

        List<LicenseApplicant> profiles = licenseService.getAllApplicants();

        model.addAttribute("profiles", profiles);
        model.addAttribute("licenseTypes", licenseTypeService.findAll());

        return "/licenses/license/registration/completion-profile/license_applicant_profile_list";
    }

    // =========================================================
    // SEND BOARD LIST
    // =========================================================

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_ADMIN') or hasRole('ROLE_LICENSE') or hasRole('ROLE_LICENSE_PROFILE_ENTRY') or hasRole('ROLE_LICENSE_COMPLETION_PROFILE')")
    @GetMapping("/licenses/license/registration/send-profile-board/license_applicant_profile_send_board_list")
    public String showListProfile(Model model) {

        List<LicenseApplicant> profiles = licenseService.getAllApplicants();

        model.addAttribute("profiles", profiles);
        model.addAttribute("licenseTypes", licenseTypeService.findAll());

        return "licenses/license/registration/send-profile-board/license_applicant_profile_send_board_list";
    }

    // =========================================================
    // GENERIC FILE DOWNLOAD
    // =========================================================

    private ResponseEntity<Resource> downloadFile(String filePath) throws IOException {

        if (filePath == null) {
            return ResponseEntity.notFound().build();
        }

        Path path = Paths.get(filePath);
        Resource resource = new UrlResource(path.toUri());

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + path.getFileName().toString() + "\"")
                .body(resource);
    }

    // =========================================================
    // APPLICATION DOWNLOAD
    // =========================================================

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_ADMIN') or hasRole('ROLE_LICENSE') or hasRole('ROLE_LICENSE_COMPLETION_PROFILE')")
    @GetMapping("/licenses/license/registration/applicationDownload/{id}")
    public ResponseEntity<Resource> downloadApplicationFile(@PathVariable Long id) throws IOException {

        Optional<LicenseApplicant> profileOpt = licenseService.getApplicantById(id);

        if (!profileOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return downloadFile(profileOpt.get().getApplicationUpload());
    }

    // =========================================================
    // LICENSE DOWNLOAD
    // =========================================================

    @GetMapping("/licenses/license/registration/licenseDownload/{id}")
    public ResponseEntity<Resource> downloadLicense(@PathVariable Long id) throws IOException {

        Optional<LicenseApplicant> profileOpt = licenseService.getApplicantById(id);

        if (!profileOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return downloadFile(profileOpt.get().getLicenseUpload());
    }

    // =========================================================
    // IDENTITY DOWNLOAD
    // =========================================================

    @GetMapping("/licenses/license/registration/identityDownload/{id}")
    public ResponseEntity<Resource> downloadIdentity(@PathVariable Long id) throws IOException {

        Optional<LicenseApplicant> profileOpt = licenseService.getApplicantById(id);

        if (!profileOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return downloadFile(profileOpt.get().getIdentityFormUpload());
    }

    // =========================================================
    // BANK DOWNLOAD
    // =========================================================

    @GetMapping("/licenses/license/registration/bankDownload/{id}")
    public ResponseEntity<Resource> downloadBank(@PathVariable Long id) throws IOException {

        Optional<LicenseApplicant> profileOpt = licenseService.getApplicantById(id);

        if (!profileOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return downloadFile(profileOpt.get().getBankStatementUpload());
    }

    // =========================================================
    // NID DOWNLOAD
    // =========================================================

    @GetMapping("/licenses/license/registration/nidDownload/{id}")
    public ResponseEntity<Resource> downloadNid(@PathVariable Long id) throws IOException {

        Optional<LicenseApplicant> profileOpt = licenseService.getApplicantById(id);

        if (!profileOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return downloadFile(profileOpt.get().getEnidUpload());
    }

    // =========================================================
    // ARTICLE DOWNLOAD
    // =========================================================

    @GetMapping("/licenses/license/registration/articleOfAssociationDownload/{id}")
    public ResponseEntity<Resource> downloadArticle(@PathVariable Long id) throws IOException {

        Optional<LicenseApplicant> profileOpt = licenseService.getApplicantById(id);

        if (!profileOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return downloadFile(profileOpt.get().getArticleOfAssociationUpload());
    }

    // =========================================================
    // BUSINESS PLAN DOWNLOAD
    // =========================================================

    @GetMapping("/licenses/license/registration/businessPlanDownload/{id}")
    public ResponseEntity<Resource> downloadBusinessPlan(@PathVariable Long id) throws IOException {

        Optional<LicenseApplicant> profileOpt = licenseService.getApplicantById(id);

        if (!profileOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return downloadFile(profileOpt.get().getBusinessPlanUpload());
    }

    // =========================================================
    // PROPOSAL DOWNLOAD
    // =========================================================

    @GetMapping("/licenses/license/registration/proposalDownload/{id}")
    public ResponseEntity<Resource> downloadProposal(@PathVariable Long id) throws IOException {

        Optional<LicenseApplicant> profileOpt = licenseService.getApplicantById(id);

        if (!profileOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return downloadFile(profileOpt.get().getProposalUpload());
    }

    // =========================================================
    // REFER TO BOARD PAGE
    // =========================================================

    @GetMapping("/licenses/license/registration/send-profile-board/license_applicants_send_board/{id}")
    public String referToBoard(@PathVariable Long id, Model model) {

        Optional<LicenseApplicant> profileOpt = licenseService.getApplicantById(id);

        if (profileOpt.isPresent()) {

            LicenseApplicant profile = profileOpt.get();

            LicenseApplicantDTO dto = new LicenseApplicantDTO();

            dto.setId(profile.getId());
            dto.setReqId(profile.getReqId());

            model.addAttribute("licenseApplicant", dto);

        } else {

            model.addAttribute("error", "Applicant not found");

            return "error-page";
        }

        return "licenses/license/registration/send-profile-board/license_applicants_send_board";
    }

    // =========================================================
    // SEND TO BOARD
    // =========================================================

    @PostMapping("/licenses/license/registration/send-profile-board/license_applicants_send_board")
    public String updateProfile(
            @ModelAttribute("licenseApplicant") LicenseApplicantDTO dto,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {

            return "licenses/license/registration/send-profile-board/license_applicants_send_board";
        }

        try {

            licenseService.SendToBoard(dto.getId(), dto);

            model.addAttribute("message", "Profile updated successfully.");

            return "redirect:/licenses/license/registration/send-profile-board/license_applicant_profile_send_board_list";

        } catch (Exception ex) {

            model.addAttribute("error", ex.getMessage());

            return "licenses/license/registration/send-profile-board/license_applicants_send_board";
        }
    }

}