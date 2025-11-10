package atraintegratedsystems.codes.controller;
import atraintegratedsystems.codes.dto.CodeDetailDTO;
import atraintegratedsystems.codes.model.CodeDetail;
import atraintegratedsystems.codes.repository.CodeDetailRepository;
import atraintegratedsystems.codes.service.CodeDetailService;
import atraintegratedsystems.codes.service.CodeService;
import atraintegratedsystems.utils.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
public class CodeDetailController {

    @Autowired
    public CodeDetailRepository codeDetailRepository;
    @Autowired
    public CodeDetailService codeDetailService;
    @Autowired
    public CodeService codeService;

    @GetMapping("/codes/standard/shortcodes_details")
    public String telecomShortCodes(Model model){
        model.addAttribute("data", codeDetailService.getAllDetailCodes());
        //new added for Modal
        model.addAttribute("codeDetailDTO",new CodeDetailDTO());
        List<Object[]> shortCodes = codeService.getShortCodes();
        model.addAttribute("codes", shortCodes);
        return "codes/standard/shortcodes_details";
    }

    @GetMapping("/codes/standard/shortcodes_details/add")
    public String addTelecomShortCode(Model model){
        model.addAttribute("codeDetailDTO",new CodeDetailDTO());
        List<Object[]> shortCodes = codeService.getShortCodes();
        model.addAttribute("codes", shortCodes);
        return "codes/standard/shortcodes_detailsAdd";
    }

    @PostMapping("/codes/standard/shortcodes_detailsAdd/add")
    public String telecomShortCodePost(@Valid @ModelAttribute("codeDetailDTO") CodeDetailDTO codeDetailDTO , BindingResult bindingResult, Model model)
            throws IOException {
        CodeDetail codeDetail= new CodeDetail();
        codeDetail.setId(codeDetailDTO.getId());
        codeDetail.setShortCode(codeDetailDTO.getShortCode());
        codeDetail.setCodeStatus(codeDetailDTO.getCodeStatus());
        codeDetail.setUnique_name_of_signaling_point(codeDetailDTO.getUnique_name_of_signaling_point());
        codeDetail.setSource_used(codeDetailDTO.getSource_used());
        codeDetail.setLocation(codeDetailDTO.getLocation());
        codeDetail.setChanel(codeDetailDTO.getChanel());
        codeDetail.setServices(codeDetailDTO.getServices());
        codeDetail.setCategoryType(codeDetailDTO.getCategoryType());
        codeDetail.setCategory(codeDetailDTO.getCategory());
        codeDetail.setBack_long_number(codeDetailDTO.getBack_long_number());
        codeDetail.setName_of_responsible_person(codeDetailDTO.getName_of_responsible_person());
        codeDetail.setId_card_number_of_responsible_person(codeDetailDTO.getId_card_number_of_responsible_person());
        codeDetail.setMobile_number_of_responsible_person(codeDetailDTO.getMobile_number_of_responsible_person());
        codeDetail.setEmail_of_responsible_person(codeDetailDTO.getEmail_of_responsible_person());
        DateConverter dateConverter = new DateConverter();
// Convert Jalali date to Gregorian
        LocalDate assigninDate = dateConverter.jalaliToGregorian(codeDetailDTO.getAssigning_date().getYear(),codeDetailDTO.getAssigning_date().getMonthValue(),codeDetailDTO.getAssigning_date().getDayOfMonth());
        codeDetail.setAssigning_date(assigninDate);
        LocalDate expiryDate = dateConverter.jalaliToGregorian(codeDetailDTO.getExpiration_date().getYear(),codeDetailDTO.getExpiration_date().getMonthValue(),codeDetailDTO.getExpiration_date().getDayOfMonth());
        codeDetail.setExpiration_date(expiryDate);
        codeDetail.setApplication_fees(codeDetailDTO.getApplication_fees());
        codeDetail.setRegistration_fees(codeDetailDTO.getRegistration_fees());
        codeDetail.setRoyalty_fees(codeDetailDTO.getRoyalty_fees());
        codeDetail.setTotal(codeDetailDTO.getTotal());
        if(bindingResult.hasErrors()){
            return "codes/standard/shortcodes_detailsAdd";
        }
        codeDetailService.AddShort(codeDetail);
        return "redirect:/codes/standard/shortcodes_details";
    }

    @GetMapping("/codes/standard/shortcodes_details/update/{id}")
    public String updateShortCodeGet(@PathVariable Long id, Model model){
        CodeDetail codeDetail= codeDetailService.getCodeDetailId(id).get();
        CodeDetailDTO codeDetailDTO= new CodeDetailDTO();
        codeDetailDTO.setId(codeDetail.getId());
        codeDetailDTO.setShortCode(codeDetail.getShortCode());
        codeDetailDTO.setCodeStatus(codeDetail.getCodeStatus());
        codeDetailDTO.setUnique_name_of_signaling_point(codeDetail.getUnique_name_of_signaling_point());
        codeDetailDTO.setSource_used(codeDetail.getSource_used());
        codeDetailDTO.setLocation(codeDetail.getLocation());
        codeDetailDTO.setChanel(codeDetail.getChanel());
        codeDetailDTO.setServices(codeDetail.getServices());
        codeDetailDTO.setCategoryType(codeDetail.getCategoryType());
        codeDetailDTO.setCategory(codeDetail.getCategory());
        codeDetailDTO.setBack_long_number(codeDetail.getBack_long_number());
        codeDetailDTO.setName_of_responsible_person(codeDetail.getName_of_responsible_person());
        codeDetailDTO.setId_card_number_of_responsible_person(codeDetail.getId_card_number_of_responsible_person());
        codeDetailDTO.setMobile_number_of_responsible_person(codeDetail.getMobile_number_of_responsible_person());
        codeDetailDTO.setEmail_of_responsible_person(codeDetail.getEmail_of_responsible_person());
        codeDetailDTO.setAssigning_date(codeDetail.getAssigning_date());
        codeDetailDTO.setExpiration_date(codeDetail.getExpiration_date());
        codeDetailDTO.setApplication_fees(codeDetail.getApplication_fees());
        codeDetailDTO.setRegistration_fees(codeDetail.getRegistration_fees());
        codeDetailDTO.setRoyalty_fees(codeDetail.getRoyalty_fees());
        codeDetailDTO.setTotal(codeDetail.getTotal());
        List<Object[]> shortCodes = codeService.getShortCodes();
        model.addAttribute("codes", shortCodes);
        model.addAttribute("codeDetailDTO",codeDetailDTO);
        return "codes/standard/shortcodes_detailsAdd";
    }

    @GetMapping("/codes/standard/shortcodes_details/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        codeDetailService.deleteCodeDetail(id);
        return "redirect:/codes/standard/shortcodes_details";
    }


}


