package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.model.CodeExtensionDetail;
import atraintegratedsystems.codes.repository.CodeExtensionDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CodeExtensionFinanceController {

    private final CodeExtensionDetailRepository repository;

    @GetMapping("/finance/code-extension/application-fee-extended")
    public String applicationFeeExtendedList(Model model) {
        model.addAttribute(
                "extensions",
                repository.findApplicationFeeExtended()
        );
        return "codes/finance/extension/shortcode/application-fee-extended-list";
    }

    @GetMapping("/finance/code-extension/application-fee/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {

        CodeExtensionDetail detail = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        model.addAttribute("detail", detail);
        return "codes/finance/extension/shortcode/application-fee-edit";
    }


    /* ======================
       UPDATE ONLY 3 FIELDS
       ====================== */
    @PostMapping("/finance/code-extension/application-fee/update")
    public String updateApplicationFee(
            @RequestParam Long id,
            @RequestParam String applicationFeeExtensionBankVoucherNo,
            @RequestParam String applicationFeeExtensionEnterVoucherDate,
            @RequestParam String applicationFeeExtensionBankVoucherSubmissionDate
    ) {

        CodeExtensionDetail detail = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        detail.setApplicationFeeExtensionBankVoucherNo(applicationFeeExtensionBankVoucherNo);
        detail.setApplicationFeeExtensionEnterVoucherDate(applicationFeeExtensionEnterVoucherDate);
        detail.setApplicationFeeExtensionBankVoucherSubmissionDate(applicationFeeExtensionBankVoucherSubmissionDate);

        repository.save(detail);

        return "redirect:/finance/code-extension/application-fee-extended";
    }


//   Bellow is For Royalty Fee Extension   findRoyaltyFeeExtended

    @GetMapping("/finance/code-extension/royalty-fee-extended")
    public String royaltyFeeExtendedList(Model model) {
        model.addAttribute(
                "extensions",
                repository.findRoyaltyFeeExtended()
        );
        return "codes/finance/extension/shortcode/royalty-fee-extended-list";
    }

    @GetMapping("/finance/code-extension/royalty-fee/edit/{id}")
    public String editRoyaltyForm(@PathVariable Long id, Model model) {

        CodeExtensionDetail detail = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        model.addAttribute("detail", detail);
        return "codes/finance/extension/shortcode/royalty-fee-edit";
    }


    /* ======================
       UPDATE ONLY 3 FIELDS
       ====================== */
    @PostMapping("/finance/code-extension/royalty-fee/update")
    public String updateroyaltyFee(
            @RequestParam Long id,
            @RequestParam String royaltyFeeExtensionBankVoucherNo,
            @RequestParam String royaltyFeeExtensionEnterVoucherDate,
            @RequestParam String royaltyFeeExtensionBankVoucherSubmissionDate
    ) {

        CodeExtensionDetail detail = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        detail.setApplicationFeeExtensionBankVoucherNo(royaltyFeeExtensionBankVoucherNo);
        detail.setApplicationFeeExtensionEnterVoucherDate(royaltyFeeExtensionEnterVoucherDate);
        detail.setApplicationFeeExtensionBankVoucherSubmissionDate(royaltyFeeExtensionBankVoucherSubmissionDate);

        repository.save(detail);

        return "redirect:/finance/code-extension/royalty-fee-extended";
    }



}
