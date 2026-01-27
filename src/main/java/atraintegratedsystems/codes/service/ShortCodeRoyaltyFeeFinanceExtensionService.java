package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.ShortCodeRoyaltyFeesExtensionDTO;
import atraintegratedsystems.codes.model.ShortCodeRoylatyFeesExtension;
import atraintegratedsystems.codes.repository.ShortCodeRoyaltyFeesExtensionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShortCodeRoyaltyFeeFinanceExtensionService {

    private final ShortCodeRoyaltyFeesExtensionRepository repository;

    public ShortCodeRoyaltyFeeFinanceExtensionService(
            ShortCodeRoyaltyFeesExtensionRepository repository) {
        this.repository = repository;
    }

    public List<ShortCodeRoyaltyFeesExtensionDTO> getUnpaidRoyaltyExtensions() {

        return repository
                .findByRoyaltyFeeExtensionPaymentStatusIsNull()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ShortCodeRoyaltyFeesExtensionDTO toDTO(
            ShortCodeRoylatyFeesExtension entity) {

        ShortCodeRoyaltyFeesExtensionDTO dto =
                new ShortCodeRoyaltyFeesExtensionDTO();

        dto.setId(entity.getId());
        dto.setRoyaltyFeeExtendedFees(entity.getRoyaltyFeeExtendedFees());
        dto.setRoyaltyFeeExtensionBankVoucherNo(
                entity.getRoyaltyFeeExtensionBankVoucherNo());
        dto.setRoyaltyFeeExtensionDate(
                entity.getRoyaltyFeeExtensionDate());
        dto.setRoyaltyFeeExtensionExpirationDate(
                entity.getRoyaltyFeeExtentionExpirationDate());
        dto.setRoyaltyFeeExtensionEntryVoucherDate(
                entity.getRoyaltyFeeExtensionEnteryVoucherDate());
        dto.setRoyaltyFeeExtensionBankVoucherSubmissionDate(
                entity.getRoyaltyFeeExtensionBankVoucherSubmissionDate());
        dto.setRoyaltyFeeExtensionPaymentStatus(
                entity.getRoyaltyFeeExtensionPaymentStatus());
        dto.setExtendStatus(entity.getExtendStatus());

        return dto;
    }


    /* ===== LOAD FOR PAY FORM ===== */
    public ShortCodeRoyaltyFeesExtensionDTO getById(Long id) {

        ShortCodeRoylatyFeesExtension entity =
                repository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Record not found"));

        ShortCodeRoyaltyFeesExtensionDTO dto =
                new ShortCodeRoyaltyFeesExtensionDTO();

        dto.setId(entity.getId());
        dto.setRoyaltyFeeExtensionExpirationDate(
                entity.getRoyaltyFeeExtentionExpirationDate());
        dto.setRoyaltyFeeExtensionEntryVoucherDate(
                entity.getRoyaltyFeeExtensionEnteryVoucherDate());
        dto.setRoyaltyFeeExtensionBankVoucherSubmissionDate(
                entity.getRoyaltyFeeExtensionBankVoucherSubmissionDate());

        return dto;
    }

    /* ===== UPDATE PAYMENT ONLY ===== */
    public void updatePayment(ShortCodeRoyaltyFeesExtensionDTO dto) {

        ShortCodeRoylatyFeesExtension entity =
                repository.findById(dto.getId())
                        .orElseThrow(() -> new RuntimeException("Record not found"));

        entity.setRoyaltyFeeExtensionBankVoucherNo(
                dto.getRoyaltyFeeExtensionBankVoucherNo());
        entity.setRoyaltyFeeExtensionEnteryVoucherDate(
                dto.getRoyaltyFeeExtensionEntryVoucherDate());
        entity.setRoyaltyFeeExtensionBankVoucherSubmissionDate(
                dto.getRoyaltyFeeExtensionBankVoucherSubmissionDate());

        /* REQUIRED */
        entity.setRoyaltyFeeExtensionPaymentStatus("PAID");

        repository.save(entity);
    }
}
