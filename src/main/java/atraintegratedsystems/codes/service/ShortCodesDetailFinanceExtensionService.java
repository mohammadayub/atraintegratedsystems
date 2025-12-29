package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.CodeExtensionDetailDTO;
import atraintegratedsystems.codes.model.CodeExtensionDetail;
import atraintegratedsystems.codes.repository.CodeExtensionDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ShortCodesDetailFinanceExtensionService {

    private final CodeExtensionDetailRepository repository;

    /* ======================
       LIST METHODS
       ====================== */

    public List<CodeExtensionDetail> getApplicationFeeExtendedList() {
        return repository.findApplicationFeeExtended();
    }

    public List<CodeExtensionDetail> getRoyaltyFeeExtendedList() {
        return repository.findRoyaltyFeeExtended();
    }

    /* ======================
       FIND BY ID
       ====================== */

    public CodeExtensionDetail getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found with id: " + id));
    }

    /* ======================
       UPDATE APPLICATION FEE
       ====================== */

    public void updateApplicationFee(CodeExtensionDetailDTO dto) {

        CodeExtensionDetail detail = getById(dto.getId());

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
    }

    /* ======================
       UPDATE ROYALTY FEE
       ====================== */

    public void updateRoyaltyFee(CodeExtensionDetailDTO dto) {

        CodeExtensionDetail detail = getById(dto.getId());

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
    }
}
