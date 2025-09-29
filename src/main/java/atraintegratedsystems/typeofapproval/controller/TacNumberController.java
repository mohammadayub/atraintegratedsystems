package atraintegratedsystems.typeofapproval.controller;

import atraintegratedsystems.typeofapproval.model.TacNumber;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalApplicant;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalTechnicalDetail;
import atraintegratedsystems.typeofapproval.service.TacNumberService;
import atraintegratedsystems.typeofapproval.service.TypeOfApprovalApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/typeofapprovals/tacnumber")
public class TacNumberController {

    private final TacNumberService tacNumberService;
    @Autowired
    private TypeOfApprovalApplicantService typeOfApprovalApplicantService;

    public TacNumberController(TacNumberService tacNumberService) {
        this.tacNumberService = tacNumberService;
    }

    // ✅ Open form for entering TAC number
    @GetMapping("/add-tachnumber/edit/{id}")
    public String showAddTacNumberForm(@PathVariable("id") Long technicalDetailId, Model model) {
        TypeOfApprovalTechnicalDetail technicalDetail = tacNumberService.getTechnicalDetailById(technicalDetailId);

        // ✅ Fetch next TAC number (last + 1)
        Integer nextTacNo = tacNumberService.getNextTacNo();
        model.addAttribute("detail", technicalDetail);
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

            // ✅ Show error message from exception
            model.addAttribute("errorMessage", e.getMessage());

            return "typeofapprovals/tacnumber/add-tachnumber-form";
        }
    }

    // ✅ TAC numbers list (with search + pagination)
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
            Page<TacNumber> tacNumbersPage = tacNumberService.getAllTacNumbersWithTechnicalDetail(page, size);

            model.addAttribute("tacNumbers", tacNumbersPage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", tacNumbersPage.getTotalPages());
            model.addAttribute("isSearch", false);
            model.addAttribute("keyword", null);
        }

        return "typeofapprovals/tacnumber/tacnumbers-list";
    }
    @GetMapping("/tacnumbers-list-print-search")
    public String getAllTacNumbersPrintSearch(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "20") int size,
                                   @RequestParam(required = false) String keyword,
                                   Model model) {

        if (keyword != null && !keyword.trim().isEmpty()) {
            // Search mode: fetch all results (no pagination)
            List<TacNumber> results = tacNumberService.searchTacNumbersPrint(keyword.trim());
            model.addAttribute("tacNumbers", results);
            model.addAttribute("isSearch", true);
            model.addAttribute("keyword", keyword);
        } else {
            // Normal paginated mode
            Page<TacNumber> tacNumbersPage = tacNumberService.getAllTacNumbersWithTechnicalDetail(page, size);

            model.addAttribute("tacNumbers", tacNumbersPage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", tacNumbersPage.getTotalPages());
            model.addAttribute("isSearch", false);
            model.addAttribute("keyword", null);
        }

        return "typeofapprovals/tacnumber/print-tac-numbers";
    }

    // Certificate Section
    @GetMapping("/paid-companies")
    public String viewPaidCompanies(Model model) {
        List<TypeOfApprovalApplicant> companies = typeOfApprovalApplicantService.getAllPaidCompanies();
        model.addAttribute("companies", companies);
        return "typeofapprovals/certificate/paid_companies";
    }

    // ✅ TAC numbers list (with search + pagination)
    @GetMapping("/tacnumbers-list-print")
    public String getAllTacNumbersForPrint(@RequestParam(defaultValue = "0") int page,
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
            Page<TacNumber> tacNumbersPage = tacNumberService.getAllTacNumbersWithTechnicalDetail(page, size);

            model.addAttribute("tacNumbers", tacNumbersPage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", tacNumbersPage.getTotalPages());
            model.addAttribute("isSearch", false);
            model.addAttribute("keyword", null);
        }

        return "typeofapprovals/tacnumber/print-tac-numbers";
    }

}
