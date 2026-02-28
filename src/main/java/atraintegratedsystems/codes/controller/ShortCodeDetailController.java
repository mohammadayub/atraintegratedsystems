package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.dto.ShortCodeDetailDTO;
import atraintegratedsystems.codes.model.ShortCode;
import atraintegratedsystems.codes.model.ShortCodeDetail;
import atraintegratedsystems.codes.service.ShortCodeDetailService;
import atraintegratedsystems.codes.service.ShortCodeService;
import atraintegratedsystems.licenses.model.LicenseApplicant;
import atraintegratedsystems.licenses.repository.LicenseApplicantRepository;
import atraintegratedsystems.licenses.service.LicenseApplicantService;
import atraintegratedsystems.utils.DateConverter;
import atraintegratedsystems.utils.PersianCalendarUtils;
import atraintegratedsystems.utils.SerialGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        codeDetail.setUnique_name_of_signaling_point(dto.getUnique_name_of_signaling_point());



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
        codeDetail.setLocation(dto.getLocation());
        codeDetail.setChanel(dto.getChanel());
        codeDetail.setServices(dto.getServices());
        codeDetail.setCategoryType(dto.getCategoryType());
        codeDetail.setCategory(dto.getCategory());
        codeDetail.setBack_long_number(dto.getBack_long_number());
        codeDetail.setName_of_responsible_person(dto.getName_of_responsible_person());
        codeDetail.setId_card_number_of_responsible_person(dto.getId_card_number_of_responsible_person());
        codeDetail.setMobile_number_of_responsible_person(dto.getMobile_number_of_responsible_person());
        codeDetail.setPhone_number_of_responsible_person(dto.getPhone_number_of_responsible_person());
        codeDetail.setEmail_of_responsible_person(dto.getEmail_of_responsible_person());
        codeDetail.setJob(dto.getJob());



        // =====================================================
        // DATE CONVERSION
        // =====================================================
        DateConverter dc = new DateConverter();

        if (dto.getAssigning_date() != null) {
            codeDetail.setAssigning_date(
                    dc.jalaliToGregorian(
                            dto.getAssigning_date().getYear(),
                            dto.getAssigning_date().getMonthValue(),
                            dto.getAssigning_date().getDayOfMonth()
                    )
            );
        }

        if (dto.getExpiration_date() != null) {
            codeDetail.setExpiration_date(
                    dc.jalaliToGregorian(
                            dto.getExpiration_date().getYear(),
                            dto.getExpiration_date().getMonthValue(),
                            dto.getExpiration_date().getDayOfMonth()
                    )
            );
        }



        // =====================================================
        // FEES
        // =====================================================
        codeDetail.setApplication_fees(dto.getApplication_fees());
        codeDetail.setRegistration_fees(dto.getRegistration_fees());
        codeDetail.setRoyalty_fees(dto.getRoyalty_fees());
        codeDetail.setTotal(dto.getTotal());



        SerialGenerator serialGenerator = new SerialGenerator();

        if (codeDetail.getSerialNumber() == null ||
                codeDetail.getSerialNumber().trim().isEmpty()) {

            String serialNumber = serialGenerator.generateSerialNumber(
                    codeDetail.getSourceUsed(),
                    codeDetail.getExpiration_date(),
                    codeDetail.getShortCode().getShortCodeName() // must be String
            );

            codeDetail.setSerialNumber(serialNumber);
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

    @PostMapping("/codes/standard/shortcodes_details/release/{id}")
    public String releaseShortCode(@PathVariable Long id) {

        shortCodeDetailService.releaseShortCode(id);

        return "redirect:/codes/standard/shortcodes_details";
    }

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
        dto.setReleaseShortCode(codeDetail.getReleaseShortCode());
        dto.setCodeStatus(codeDetail.getCodeStatus());
        dto.setUnique_name_of_signaling_point(codeDetail.getUnique_name_of_signaling_point());

        if (codeDetail.getLicenseApplicant() != null) {
            dto.setLicenseApplicantId(codeDetail.getLicenseApplicant().getId());
        }

        dto.setSourceUsed(codeDetail.getSourceUsed());
        dto.setLocation(codeDetail.getLocation());
        dto.setChanel(codeDetail.getChanel());
        dto.setServices(codeDetail.getServices());
        dto.setCategoryType(codeDetail.getCategoryType());
        dto.setCategory(codeDetail.getCategory());
        dto.setBack_long_number(codeDetail.getBack_long_number());
        dto.setName_of_responsible_person(codeDetail.getName_of_responsible_person());
        dto.setId_card_number_of_responsible_person(codeDetail.getId_card_number_of_responsible_person());
        dto.setPhone_number_of_responsible_person(codeDetail.getPhone_number_of_responsible_person());
        dto.setMobile_number_of_responsible_person(codeDetail.getMobile_number_of_responsible_person());
        dto.setEmail_of_responsible_person(codeDetail.getEmail_of_responsible_person());
        dto.setAssigning_date(codeDetail.getAssigning_date());
        dto.setExpiration_date(codeDetail.getExpiration_date());
        dto.setApplication_fees(codeDetail.getApplication_fees());
        dto.setRegistration_fees(codeDetail.getRegistration_fees());
        dto.setRoyalty_fees(codeDetail.getRoyalty_fees());
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
