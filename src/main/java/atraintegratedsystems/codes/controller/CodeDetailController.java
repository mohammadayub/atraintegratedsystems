package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.dto.CodeDetailDTO;
import atraintegratedsystems.codes.model.CodeDetail;
import atraintegratedsystems.codes.service.CodeDetailService;
import atraintegratedsystems.codes.service.CodeService;

import atraintegratedsystems.licenses.model.LicenseType;
import atraintegratedsystems.licenses.service.LicenseTypeService;
import atraintegratedsystems.utils.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;


@Controller
public class CodeDetailController {

    @Autowired
    public CodeDetailService codeDetailService;

    @Autowired
    public CodeService codeService;

    @Autowired
    public LicenseTypeService licenseTypeService;

    // -------------------------------------------------------------------
    // LIST PAGE
    // -------------------------------------------------------------------
    @GetMapping("/codes/standard/shortcodes_details")
    public String telecomShortCodes(Model model){
        model.addAttribute("data", codeDetailService.getAllDetailCodes());
        model.addAttribute("codeDetailDTO", new CodeDetailDTO());
        model.addAttribute("codes", codeService.getShortCodes());
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        return "codes/standard/shortcodes_details";
    }

    // -------------------------------------------------------------------
    // ADD PAGE
    // -------------------------------------------------------------------
    @GetMapping("/codes/standard/shortcodes_details/add")
    public String addTelecomShortCode(Model model){
        model.addAttribute("codeDetailDTO", new CodeDetailDTO());
        model.addAttribute("codes", codeService.getShortCodes());
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        return "codes/standard/shortcodes_detailsAdd";
    }

    // -------------------------------------------------------------------
    // SAVE (ADD)
    // -------------------------------------------------------------------
    @PostMapping("/codes/standard/shortcodes_detailsAdd/add")
    public String telecomShortCodePost(
            @Valid @ModelAttribute("codeDetailDTO") CodeDetailDTO dto,
            BindingResult bindingResult,
            Model model
    ) throws IOException {

        if(bindingResult.hasErrors()){
            model.addAttribute("codes", codeService.getShortCodes());
            model.addAttribute("licenseTypes", licenseTypeService.findAll());
            return "codes/standard/shortcodes_detailsAdd";
        }

        CodeDetail codeDetail = new CodeDetail();

        codeDetail.setId(dto.getId());
        codeDetail.setShortCode(dto.getShortCode());
        codeDetail.setCodeStatus(dto.getCodeStatus());
        codeDetail.setUnique_name_of_signaling_point(dto.getUnique_name_of_signaling_point());

        // -----------------------------
        // LOAD LICENSE TYPE (IMPORTANT)
        // -----------------------------
        Optional<LicenseType> licenseTypeOpt = licenseTypeService.getByLicenseTypeId(dto.getLicenseTypeId());

        if (licenseTypeOpt.isPresent()) {
            codeDetail.setLicenseType(licenseTypeOpt.get());
        } else {
            // Handle the case where the LicenseType is not found
            throw new RuntimeException("LicenseType not found with id: " + dto.getLicenseTypeId());
        }


        codeDetail.setLocation(dto.getLocation());
        codeDetail.setChanel(dto.getChanel());
        codeDetail.setServices(dto.getServices());
        codeDetail.setCategoryType(dto.getCategoryType());
        codeDetail.setCategory(dto.getCategory());
        codeDetail.setBack_long_number(dto.getBack_long_number());
        codeDetail.setName_of_responsible_person(dto.getName_of_responsible_person());
        codeDetail.setId_card_number_of_responsible_person(dto.getId_card_number_of_responsible_person());
        codeDetail.setMobile_number_of_responsible_person(dto.getMobile_number_of_responsible_person());
        codeDetail.setEmail_of_responsible_person(dto.getEmail_of_responsible_person());

        // Date conversion
        DateConverter dc = new DateConverter();
        codeDetail.setAssigning_date(
                dc.jalaliToGregorian(
                        dto.getAssigning_date().getYear(),
                        dto.getAssigning_date().getMonthValue(),
                        dto.getAssigning_date().getDayOfMonth()
                )
        );
        codeDetail.setExpiration_date(
                dc.jalaliToGregorian(
                        dto.getExpiration_date().getYear(),
                        dto.getExpiration_date().getMonthValue(),
                        dto.getExpiration_date().getDayOfMonth()
                )
        );

        codeDetail.setApplication_fees(dto.getApplication_fees());
        codeDetail.setRegistration_fees(dto.getRegistration_fees());
        codeDetail.setRoyalty_fees(dto.getRoyalty_fees());
        codeDetail.setTotal(dto.getTotal());

        codeDetailService.AddShort(codeDetail);

        return "redirect:/codes/standard/shortcodes_details";
    }

    // -------------------------------------------------------------------
    // EDIT PAGE
    // -------------------------------------------------------------------
    @GetMapping("/codes/standard/shortcodes_details/update/{id}")
    public String updateShortCodeGet(@PathVariable Long id, Model model){
        CodeDetail codeDetail = codeDetailService.getCodeDetailId(id).get();

        CodeDetailDTO dto = new CodeDetailDTO();

        dto.setId(codeDetail.getId());
        dto.setShortCode(codeDetail.getShortCode());
        dto.setCodeStatus(codeDetail.getCodeStatus());
        dto.setUnique_name_of_signaling_point(codeDetail.getUnique_name_of_signaling_point());

        // Load licenseTypeId into DTO
        dto.setLicenseTypeId(codeDetail.getLicenseType().getId());

        dto.setLocation(codeDetail.getLocation());
        dto.setChanel(codeDetail.getChanel());
        dto.setServices(codeDetail.getServices());
        dto.setCategoryType(codeDetail.getCategoryType());
        dto.setCategory(codeDetail.getCategory());
        dto.setBack_long_number(codeDetail.getBack_long_number());
        dto.setName_of_responsible_person(codeDetail.getName_of_responsible_person());
        dto.setId_card_number_of_responsible_person(codeDetail.getId_card_number_of_responsible_person());
        dto.setMobile_number_of_responsible_person(codeDetail.getMobile_number_of_responsible_person());
        dto.setEmail_of_responsible_person(codeDetail.getEmail_of_responsible_person());
        dto.setAssigning_date(codeDetail.getAssigning_date());
        dto.setExpiration_date(codeDetail.getExpiration_date());
        dto.setApplication_fees(codeDetail.getApplication_fees());
        dto.setRegistration_fees(codeDetail.getRegistration_fees());
        dto.setRoyalty_fees(codeDetail.getRoyalty_fees());
        dto.setTotal(codeDetail.getTotal());

        model.addAttribute("codes", codeService.getShortCodes());
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        model.addAttribute("codeDetailDTO", dto);

        return "codes/standard/shortcodes_detailsAdd";
    }

    // -------------------------------------------------------------------
    // DELETE
    // -------------------------------------------------------------------
    @GetMapping("/codes/standard/shortcodes_details/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        codeDetailService.deleteCodeDetail(id);
        return "redirect:/codes/standard/shortcodes_details";
    }
}
