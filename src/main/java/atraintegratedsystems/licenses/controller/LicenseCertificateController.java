package atraintegratedsystems.licenses.controller;

import atraintegratedsystems.licenses.dto.LicenseAdminFeesExtensionDTO;
import atraintegratedsystems.licenses.dto.LicenseApplicantDTO;
import atraintegratedsystems.licenses.dto.LicenseApprovalDTO;
import atraintegratedsystems.licenses.model.LicenseAdminFeesExtension;
import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.service.LicenseCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
@Controller
public class LicenseCertificateController {
    @Autowired
    private LicenseCertificateService licenseCertificateService;
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_ADMIN') or hasRole('ROLE_LICENSE')")
    @GetMapping("/licenses/license/certificate/license_applicants_certificate_list")
    public String showApplicationProfile(Model model) {
        List<LicenseApproval> profiles = licenseCertificateService.getAllPaid();
        model.addAttribute("profiles", profiles);
        return "licenses/license/certificate/license_applicants_certificate_list";
    }


    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_ADMIN') or hasRole('ROLE_LICENSE')")
    @GetMapping("/licenses/license/certificate/license_applicants_certificate_list/print/{id}")
    public String GetTariff(@PathVariable Long id, Model model){
        LicenseApproval licenseApproval = licenseCertificateService.getApprovalByApplicantId(id)
                .orElseThrow(() -> new RuntimeException("License Approval not found for applicantId: " + id));
        LicenseApprovalDTO licenseApprovalDTO = new LicenseApprovalDTO();

        licenseApprovalDTO.setId(licenseApproval.getId());
        licenseApprovalDTO.setApprovalId(licenseApproval.getApprovalId());
        licenseApprovalDTO.setApplicantLicenseCompanyName(licenseApproval.getLicenseApplicant().getCompanyLicenseName());
        licenseApprovalDTO.setLicenseTypeName(licenseApproval.getLicenseType().getName());
        licenseApprovalDTO.setApprovalDate(licenseApproval.getApprovalDate());
        licenseApprovalDTO.setLicenseFeeExpiryDate(licenseApproval.getLicenseFeeExpiryDate());
        licenseApprovalDTO.setCompanyAddress(licenseApproval.getLicenseApplicant().getCompanyAddress());
        licenseApprovalDTO.setYearOfEstablishment(licenseApproval.getLicenseApplicant().getYearOfEstablishment());
        licenseApprovalDTO.setContactNo(licenseApproval.getLicenseApplicant().getContactNo());
        licenseApprovalDTO.setLicenseNo(licenseApproval.getLicenseApplicant().getLicenseNo());
        model.addAttribute("licenseApprovalDTO", licenseApprovalDTO);
        return "licenses/license/certificate/license_applicant_certificate_print";
    }
}
