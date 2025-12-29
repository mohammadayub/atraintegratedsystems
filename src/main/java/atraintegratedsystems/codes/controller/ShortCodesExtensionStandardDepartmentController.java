package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.model.CodeDetail;
import atraintegratedsystems.codes.model.CodeExtensionDetail;
import atraintegratedsystems.codes.repository.CodeDetailRepository;
import atraintegratedsystems.codes.service.CodeDetailService;
import atraintegratedsystems.codes.service.CodeExtensionDetailService;
import atraintegratedsystems.codes.service.ShortCodesDetailPaymentsConfirmationFinanceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/codes")
public class ShortCodesExtensionStandardDepartmentController {

    private final CodeDetailService codeDetailService;
    private final CodeExtensionDetailService extensionService;
    private final CodeDetailRepository codeDetailRepository;
    private final ShortCodesDetailPaymentsConfirmationFinanceService paymentsConfirmationService;

    // ✅ Constructor Injection (BEST PRACTICE)
    public ShortCodesExtensionStandardDepartmentController(
            CodeDetailService codeDetailService,
            CodeExtensionDetailService extensionService,
            CodeDetailRepository codeDetailRepository,
            ShortCodesDetailPaymentsConfirmationFinanceService paymentsConfirmationService) {

        this.codeDetailService = codeDetailService;
        this.extensionService = extensionService;
        this.codeDetailRepository = codeDetailRepository;
        this.paymentsConfirmationService = paymentsConfirmationService;
    }

    /* =========================================================
       ROYALTY FEE EXTENSION SECTION
       ========================================================= */

    // 🔹 Royalty Fee Extension List
    @GetMapping("/standard/extension/royaltyFeelist")
    public String royaltyFeesExtensionList(Model model) {

        List<Object[]> codes =
                paymentsConfirmationService.getUnPaidRyaltyFeesForExtension();

        model.addAttribute("codes", codes);
        return "codes/standard/extension/shortcode/royaltyfee_extensionlist";
    }

    // 🔹 Open Royalty Fee Extension Form
    @GetMapping("/extension/create/{codeDetailId}")
    public String openRoyaltyExtensionForm(
            @PathVariable Long codeDetailId,
            Model model) {

        CodeDetail codeDetail = codeDetailRepository.findById(codeDetailId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid Code Detail ID: " + codeDetailId));

        CodeExtensionDetail extension = new CodeExtensionDetail();
        extension.setCodeDetail(codeDetail);
        extension.setExtendingDate(LocalDate.now());
        extension.setRoyaltyFeeExtendedStatus("EXTENDED");

        model.addAttribute("extension", extension);
        return "codes/standard/extension/shortcode/code-extension-form";
    }

    // 🔹 Save Royalty Fee Extension
    @PostMapping("/extension/save")
    public String saveRoyaltyExtension(
            @ModelAttribute("extension") CodeExtensionDetail extension) {

        // 🔒 Enforce system value
        extension.setRoyaltyFeeExtendedStatus("EXTENDED");

        extensionService.AddCodeExtensionContract(extension);

        return "redirect:/codes/standard/extension/royaltyFeelist";
    }

    /* =========================================================
       APPLICATION FEE EXTENSION SECTION
       ========================================================= */

    // 🔹 Application Fee Extension List
    @GetMapping("/standard/extension/applicationfeelist")
    public String applicationFeesExtensionList(Model model) {

        List<Object[]> codes =
                paymentsConfirmationService.getUnPaidRyaltyFeesForExtension();

        model.addAttribute("codes", codes);
        return "codes/standard/extension/shortcode/applicationfee_extensionlist";
    }

    // 🔹 Open Application Fee Extension Form
    @GetMapping("/applicationfee/extension/create/{codeDetailId}")
    public String openApplicationFeeExtensionForm(
            @PathVariable Long codeDetailId,
            Model model) {

        CodeDetail codeDetail = codeDetailRepository.findById(codeDetailId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid Code Detail ID: " + codeDetailId));

        CodeExtensionDetail extension = new CodeExtensionDetail();
        extension.setCodeDetail(codeDetail);
        extension.setExtendingDate(LocalDate.now());
        extension.setApplicationFeeExtendedStatus("EXTENDED");

        model.addAttribute("extension", extension);
        return "codes/standard/extension/shortcode/applicationfee-extension-form";
    }

    // 🔹 Save Application Fee Extension
    @PostMapping("/application/extension/save")
    public String saveApplicationFeeExtension(
            @ModelAttribute("extension") CodeExtensionDetail extension) {

        // 🔒 Enforce system value
        extension.setApplicationFeeExtendedStatus("EXTENDED");

        extensionService.AddCodeExtensionContract(extension);

        return "redirect:/codes/standard/extension/applicationfeelist";
    }
}
