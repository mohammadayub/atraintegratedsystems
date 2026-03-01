package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.dto.ShortCodeDetailDTO;
import atraintegratedsystems.codes.model.ShortCode;
import atraintegratedsystems.codes.model.ShortCodeDetail;
import atraintegratedsystems.codes.repository.ShortCodeDetailRepository;
import atraintegratedsystems.codes.service.ShortCodeDetailService;
import atraintegratedsystems.codes.service.ShortCodeService;
import atraintegratedsystems.licenses.model.LicenseApplicant;
import atraintegratedsystems.licenses.repository.LicenseApplicantRepository;
import atraintegratedsystems.licenses.service.LicenseApplicantService;
import atraintegratedsystems.utils.DateConverter;
import atraintegratedsystems.utils.SerialGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@Controller
public class ShortCodeDetailController {

    // ======================================================
    // SERVICES
    // ======================================================

    @Autowired
    private ShortCodeDetailService shortCodeDetailService;

    @Autowired
    private ShortCodeService shortCodeService;

    @Autowired
    private LicenseApplicantRepository licenseApplicantRepository;

    @Autowired
    private LicenseApplicantService licenseApplicantService;

    @Autowired
    private ShortCodeDetailRepository repository;


    // ======================================================
    // LIST PAGE
    // ======================================================

//    @GetMapping("/codes/standard/shortcodes_details")
//    public String telecomShortCodes(Model model) {
//
//        model.addAttribute("data", shortCodeDetailService.getAllDetailCodes());
//        model.addAttribute("codeDetailDTO", new ShortCodeDetailDTO());
//
//        model.addAttribute("codes", shortCodeService.getAvailableShortCodes());
//        model.addAttribute("serialNumbers", shortCodeSerialNumberService.findAllNullShortCode());
//
//        model.addAttribute(
//                "licenseApplicants",
//                licenseApplicantRepository.findAllApprovedApplicants()
//        );
//
//        return "codes/standard/shortcodes_details";
//    }
    // List File
    @GetMapping("/codes/standard/shortcodes_details")
    public String list(Model model) {

        model.addAttribute("data", shortCodeDetailService.getAllShortCodes());

        return "codes/standard/shortcodes_details";
    }

    // ======================================================
    // ADD PAGE
    // ======================================================

    @GetMapping("/codes/standard/shortcodes_details/add")
    public String addTelecomShortCode(Model model) {

        model.addAttribute("codeDetailDTO", new ShortCodeDetailDTO());

        model.addAttribute("codes", shortCodeService.getAvailableShortCodes());

        model.addAttribute(
                "licenseApplicants",
                licenseApplicantRepository.findAllApprovedApplicants()
        );

        return "codes/standard/shortcodes_detailsAdd";
    }

    // ======================================================
    // SAVE (ADD)
    // ======================================================

    @PostMapping("/codes/standard/shortcodes_detailsAdd/add")
    public String telecomShortCodePost(
            @Valid @ModelAttribute("codeDetailDTO") ShortCodeDetailDTO dto,
            BindingResult bindingResult,
            Model model
    ) throws IOException {

        if (bindingResult.hasErrors()) {

            model.addAttribute("codes", shortCodeService.getAvailableShortCodes());
            model.addAttribute("licenseApplicants",
                    licenseApplicantRepository.findAllApprovedApplicants());

            return "codes/standard/shortcodes_detailsAdd";
        }

        ShortCodeDetail codeDetail = new ShortCodeDetail();

        codeDetail.setId(dto.getId());

        codeDetail.setReleaseShortCode(dto.getReleaseShortCode());
        codeDetail.setCodeStatus(dto.getCodeStatus());
        codeDetail.setUniqueNameOfSignalingPoint(dto.getUniqueNameOfSignalingPoint());



        // =====================================================
        // SHORT CODE (STORE FK + UPDATE STATUS)
        // =====================================================
        if (dto.getShortCodeId() != null) {

            ShortCode shortCode = shortCodeService.getById(dto.getShortCodeId());

            if (shortCode != null) {

                // store FK
                codeDetail.setShortCode(shortCode);

                // update status
                shortCode.setAssignStatus("ASSIGN");

                shortCodeService.save(shortCode);
            }
        }



        // =====================================================
        // LICENSE APPLICANT
        // =====================================================
        if (dto.getLicenseApplicantId() != null) {

            Optional<LicenseApplicant> applicant =
                    licenseApplicantService.getApplicantById(dto.getLicenseApplicantId());

            codeDetail.setLicenseApplicant(applicant.orElse(null));
        }








        // =====================================================
        // SIMPLE FIELDS
        // =====================================================
        codeDetail.setSourceUsed(dto.getSourceUsed());
        codeDetail.setSourceUsedInDari(dto.getSourceUsedInDari());
        codeDetail.setLocation(dto.getLocation());
        codeDetail.setChanel(dto.getChanel());
        codeDetail.setServices(dto.getServices());
        codeDetail.setCategoryType(dto.getCategoryType());
        codeDetail.setCategory(dto.getCategory());
        codeDetail.setBackLongNumber(dto.getBackLongNumber());
        codeDetail.setNameOfResponsiblePerson(dto.getNameOfResponsiblePerson());
        codeDetail.setIdCardNumberOfResponsiblePerson(dto.getIdCardNumberOfResponsiblePerson());
        codeDetail.setMobileNumberOfResponsiblePerson(dto.getMobileNumberOfResponsiblePerson());
        codeDetail.setPhoneNumberOfResponsiblePerson(dto.getPhoneNumberOfResponsiblePerson());
        codeDetail.setEmailOfResponsiblePerson(dto.getEmailOfResponsiblePerson());
        codeDetail.setJob(dto.getJob());
        codeDetail.setCodeStatus("Active");



        // =====================================================
        // DATE CONVERSION
        // =====================================================
        DateConverter dc = new DateConverter();

        if (dto.getAssigningDate() != null) {
            codeDetail.setAssigningDate(
                    dc.jalaliToGregorian(
                            dto.getAssigningDate().getYear(),
                            dto.getAssigningDate().getMonthValue(),
                            dto.getAssigningDate().getDayOfMonth()
                    )
            );
        }

        if (dto.getExpirationDate() != null) {
            codeDetail.setExpirationDate(
                    dc.jalaliToGregorian(
                            dto.getExpirationDate().getYear(),
                            dto.getExpirationDate().getMonthValue(),
                            dto.getExpirationDate().getDayOfMonth()
                    )
            );
        }



        // =====================================================
        // FEES
        // =====================================================
        codeDetail.setApplicationFees(dto.getApplicationFees());
        codeDetail.setRegistrationFees(dto.getRegistrationFees());
        codeDetail.setRoyaltyFees(dto.getRoyaltyFees());
        codeDetail.setTotal(dto.getTotal());



        SerialGenerator serialGenerator = new SerialGenerator();

        if (codeDetail.getSerialNumber() == null ||
                codeDetail.getSerialNumber().trim().isEmpty()) {

            // Generate serial number using in-memory global counter
            String serial = serialGenerator.generateSerialNumber(
                    codeDetail.getSourceUsed(),
                    codeDetail.getExpirationDate(),
                    codeDetail.getShortCode().getShortCodeName()  // Assuming this returns Integer/String
            );

            codeDetail.setSerialNumber(serial);
        }












        // =====================================================
        // SAVE
        // =====================================================

        shortCodeDetailService.AddShort(codeDetail);

        return "redirect:/codes/standard/shortcodes_details";
    }

    // ======================================================
    // RELEASE SHORT CODE
    // ======================================================

//    @PostMapping("/codes/standard/shortcodes_details/release/{id}")
//    public String releaseShortCode(@PathVariable Long id) {
//
//        shortCodeDetailService.releaseShortCode(id);
//
//        return "redirect:/codes/standard/shortcodes_details";
//    }

    // ======================================================
    // EDIT PAGE
    // ======================================================

    @GetMapping("/codes/standard/shortcodes_details/update/{id}")
    public String updateShortCodeGet(@PathVariable Long id, Model model) {

        ShortCodeDetail codeDetail = shortCodeDetailService.getCodeDetailId(id)
                .orElseThrow(() ->
                        new RuntimeException("CodeDetail not found with id: " + id));

        ShortCodeDetailDTO dto = new ShortCodeDetailDTO();

        dto.setId(codeDetail.getId());
//        dto.setReleaseShortCode(codeDetail.getReleaseShortCode());
//        dto.setShortCodeId(codeDetail.getShortCode().getShortCodeName());
//        dto.setCodeStatus(codeDetail.getCodeStatus());

        dto.setShortCodeId(codeDetail.getShortCode() != null
                ? codeDetail.getShortCode().getId()
                : null);

        dto.setUniqueNameOfSignalingPoint(codeDetail.getUniqueNameOfSignalingPoint());

        if (codeDetail.getLicenseApplicant() != null) {
            dto.setLicenseApplicantId(codeDetail.getLicenseApplicant().getId());
        }

        dto.setSourceUsed(codeDetail.getSourceUsed());
        dto.setSourceUsedInDari(codeDetail.getSourceUsedInDari());
        dto.setLocation(codeDetail.getLocation());
        dto.setChanel(codeDetail.getChanel());
        dto.setServices(codeDetail.getServices());
        dto.setCategoryType(codeDetail.getCategoryType());
        dto.setCategory(codeDetail.getCategory());
        dto.setBackLongNumber(codeDetail.getBackLongNumber());
        dto.setNameOfResponsiblePerson(codeDetail.getNameOfResponsiblePerson());
        dto.setIdCardNumberOfResponsiblePerson(codeDetail.getIdCardNumberOfResponsiblePerson());
        dto.setPhoneNumberOfResponsiblePerson(codeDetail.getPhoneNumberOfResponsiblePerson());
        dto.setMobileNumberOfResponsiblePerson(codeDetail.getMobileNumberOfResponsiblePerson());
        dto.setEmailOfResponsiblePerson(codeDetail.getEmailOfResponsiblePerson());
        dto.setAssigningDate(codeDetail.getAssigningDate());
        dto.setExpirationDate(codeDetail.getExpirationDate());
        dto.setApplicationFees(codeDetail.getApplicationFees());
        dto.setRegistrationFees(codeDetail.getRegistrationFees());
        dto.setRoyaltyFees(codeDetail.getRoyaltyFees());
        dto.setJob(codeDetail.getJob());
        dto.setTotal(codeDetail.getTotal());

        model.addAttribute("codeDetailDTO", dto);
        model.addAttribute("codes", shortCodeService.getAvailableShortCodes());
        model.addAttribute("licenseApplicants",
                licenseApplicantRepository.findAllApprovedApplicants());

        return "codes/standard/shortcodes_detailsAdd";
    }

    // ======================================================
    // DELETE
    // ======================================================

    @GetMapping("/codes/standard/shortcodes_details/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {

        shortCodeDetailService.deleteCodeDetail(id);

        return "redirect:/codes/standard/shortcodes_details";
    }





















}
