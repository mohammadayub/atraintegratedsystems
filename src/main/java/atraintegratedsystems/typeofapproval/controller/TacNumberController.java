package atraintegratedsystems.typeofapproval.controller;
import atraintegratedsystems.typeofapproval.model.TacNumber;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalManufacturerDetail;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalTechnicalDetail;
import atraintegratedsystems.typeofapproval.service.TacNumberService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/typeofapprovals/tacnumber")
public class TacNumberController {

    private final TacNumberService tacNumberService;
    public TacNumberController(TacNumberService tacNumberService) {
        this.tacNumberService = tacNumberService;
    }

    // Open form for entering TAC number
    @GetMapping("/add-tachnumber/edit/{id}")
    public String showAddTacNumberForm(@PathVariable("id") Long technicalDetailId, Model model) {
        TypeOfApprovalTechnicalDetail technicalDetail = tacNumberService.getTechnicalDetailById(technicalDetailId);

        // ✅ Fetch next TAC number (last + 1)
        Integer nextTacNo = tacNumberService.getNextTacNo();
        model.addAttribute("technicaldetails", technicalDetail);
        model.addAttribute("tacNumber", new TacNumber());
        model.addAttribute("tacNumbers", technicalDetail.getTacNumbers()); // list of existing TACs
        model.addAttribute("nextTacNo", nextTacNo); // pass to thymeleaf

        return "typeofapprovals/tacnumber/add-tachnumber-form";
    }
    @PostMapping("/save/{id}")
    public String saveTacNumbers(@PathVariable("id") Long technicalDetailId,
                                 @RequestParam("from") int from,
                                 @RequestParam("to") int to,
                                 Model model) {
        try {
            tacNumberService.saveTacNumberRange(technicalDetailId, from, to);
            return "redirect:/typeofapprovals/tacnumber/add-tachnumber/edit/" + technicalDetailId;
        } catch (RuntimeException e) {
            TypeOfApprovalTechnicalDetail technicalDetail = tacNumberService.getTechnicalDetailById(technicalDetailId);

            model.addAttribute("technicalDetail", technicalDetail);
            model.addAttribute("tacNumber", new TacNumber());
            model.addAttribute("tacNumbers", technicalDetail.getTacNumbers());

            // ✅ Fetch latest TAC number again (for error case too)
            Integer nextTacNo = tacNumberService.getNextTacNo();
            model.addAttribute("nextTacNo", nextTacNo);
            // Show clean error message
            if (e.getMessage().contains("Duplicate value")) {
                model.addAttribute("errorMessage", "This TAC number has already been taken.");
            } else {
                model.addAttribute("errorMessage", "These TAC numbers have already been taken");
            }
            return "typeofapprovals/tacnumber/add-tachnumber-form";
        }
    }


    @GetMapping("/tacnumbers-list")
    public String getAllTacNumbers(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "20") int size,
                                   @RequestParam(required = false) String keyword,
                                   Model model) {

        if (keyword != null && !keyword.trim().isEmpty()) {
            // Search mode: fetch all results (no pagination)
            List<TacNumber> results = tacNumberService.searchTacNumbers(keyword.trim());
            model.addAttribute("tacNumbers", results);
            model.addAttribute("isSearch", true);
            model.addAttribute("keyword", keyword);
        } else {
            // Normal paginated mode
            Page<TacNumber> tacNumbersPage = tacNumberService.getAllTacNumbersWithManufacturer(page, size);

            model.addAttribute("tacNumbers", tacNumbersPage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", tacNumbersPage.getTotalPages());
            model.addAttribute("isSearch", false);
            model.addAttribute("keyword", null); // <--- important
        }

        return "typeofapprovals/tacnumber/tacnumbers-list";
    }






}