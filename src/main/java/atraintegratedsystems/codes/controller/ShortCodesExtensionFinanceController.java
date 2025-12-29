package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.dto.CodeExtensionDetailDTO;
import atraintegratedsystems.codes.model.CodeExtensionDetail;
import atraintegratedsystems.codes.repository.CodeExtensionDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class ShortCodesExtensionFinanceController {

    private final CodeExtensionDetailRepository repository;

    @GetMapping("/codes/finance/extension/home")
    public String codeExtensionFinanceHome()
    {
        return "codes/finance/extension/home";
    }
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
            @ModelAttribute CodeExtensionDetailDTO dto
    ) {

        CodeExtensionDetail detail = repository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Record not found"));

        // UPDATE ONLY APPLICATION FEE FIELDS
        detail.setApplicationFeeExtensionBankVoucherNo(
                dto.getApplicationFeeExtensionBankVoucherNo()
        );
        detail.setApplicationFeeExtensionEnterVoucherDate(
                dto.getApplicationFeeExtensionEnterVoucherDate()
        );
        detail.setApplicationFeeExtensionBankVoucherSubmissionDate(
                dto.getApplicationFeeExtensionBankVoucherSubmissionDate()
        );

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
    public String updateRoyaltyFee(
            @ModelAttribute CodeExtensionDetailDTO dto
    ) {

        CodeExtensionDetail detail = repository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Record not found"));

        // UPDATE ONLY ROYALTY FEE FIELDS
        detail.setRoyaltyFeeExtensionBankVoucherNo(
                dto.getRoyaltyFeeExtensionBankVoucherNo()
        );
        detail.setRoyaltyFeeExtensionEnterVoucherDate(
                dto.getRoyaltyFeeExtensionEnterVoucherDate()
        );
        detail.setRoyaltyFeeExtensionBankVoucherSubmissionDate(
                dto.getRoyaltyFeeExtensionBankVoucherSubmissionDate()
        );

        repository.save(detail);

        return "redirect:/finance/code-extension/royalty-fee-extended";
    }




}
