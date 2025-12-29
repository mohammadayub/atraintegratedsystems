package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.model.CodeDetail;
import atraintegratedsystems.codes.model.CodeExtensionDetail;
import atraintegratedsystems.codes.repository.CodeDetailRepository;
import atraintegratedsystems.codes.service.CodeDetailService;
import atraintegratedsystems.codes.service.CodeExtensionDetailService;
import atraintegratedsystems.codes.service.CodesDetailPaymentsConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class CodesExtensionStandardDepartmentController {
    @Autowired
    private CodeDetailService codeDetailService;

    @Autowired
    private CodesDetailPaymentsConfirmationService codesDetailPaymentsConfirmationService;


    private final CodeExtensionDetailService extensionService;
    private final CodeDetailRepository codeDetailRepository;

    public CodesExtensionStandardDepartmentController(CodeExtensionDetailService extensionService,
                                                      CodeDetailRepository codeDetailRepository) {
        this.extensionService = extensionService;
        this.codeDetailRepository = codeDetailRepository;
    }
    // Royalty Fees Extension codes

    @GetMapping("/codes/standard/extension/royaltyFeelist")
    public String royaltyFeesExtensionList(Model model) {
        List<Object[]> codes = codesDetailPaymentsConfirmationService.getUnPaidRyaltyFeesForExtension();
        model.addAttribute("codes", codes);
        return "codes/standard/extension/shortcode/royaltyfee_extensionlist";
    }

    // ✅ OPEN EXTENSION FORM
    @GetMapping("/codes/extension/create/{codeDetailId}")
    public String openExtensionForm(@PathVariable Long codeDetailId, Model model) {

        CodeDetail codeDetail = codeDetailRepository.findById(codeDetailId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Code Detail ID"));

        CodeExtensionDetail extension = new CodeExtensionDetail();
        extension.setCodeDetail(codeDetail);
        extension.setExtendingDate(LocalDate.now());
        extension.setRoyaltyFeeExtendedStatus("EXTENDED");

        model.addAttribute("extension", extension);

//        return "codes/code-extension-form";
        return "codes/standard/extension/shortcode/code-extension-form";
    }

    // ✅ SAVE EXTENSION
    @PostMapping("/codes/extension/save")
    public String saveExtension(@ModelAttribute("extension") CodeExtensionDetail extension) {

        // 🔒 enforce system value
        extension.setRoyaltyFeeExtendedStatus("EXTENDED");

        extensionService.AddCodeExtensionContract(extension);

        return "redirect:/codes/standard/extension/royaltyFeelist";
    }


    // Application Fee Section

    @GetMapping("/codes/standard/extension/applicationfeelist")
    public String applicationFeesExtensionList(Model model) {
        List<Object[]> codes = codesDetailPaymentsConfirmationService.getUnPaidRyaltyFeesForExtension();
        model.addAttribute("codes", codes);
        return "codes/standard/extension/shortcode/applicationfee_extensionlist";
    }

    // ✅ OPEN EXTENSION FORM
    @GetMapping("/codes/applicationfee/extension/create/{codeDetailId}")
    public String openApplicationFeeExtensionForm(@PathVariable Long codeDetailId, Model model) {

        CodeDetail codeDetail = codeDetailRepository.findById(codeDetailId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Code Detail ID"));

        CodeExtensionDetail extension = new CodeExtensionDetail();
        extension.setCodeDetail(codeDetail);
        extension.setExtendingDate(LocalDate.now());
        extension.setApplicationFeeExtendedStatus("EXTENDED");

        model.addAttribute("extension", extension);

//        return "codes/code-extension-form";
        return "codes/standard/extension/shortcode/applicationfee-extension-form";
    }

    // ✅ SAVE EXTENSION
    @PostMapping("/codes/application/extension/save")
    public String saveApplicationFeeExtension(@ModelAttribute("extension") CodeExtensionDetail extension) {

        // 🔒 enforce system value
        extension.setApplicationFeeExtendedStatus("EXTENDED");

        extensionService.AddCodeExtensionContract(extension);

        return "redirect:/codes/standard/extension/applicationfeelist";
    }
}
