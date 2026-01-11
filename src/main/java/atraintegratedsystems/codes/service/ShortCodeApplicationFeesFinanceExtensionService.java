package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.ShortCodeApplicationFeesExtensionDTO;
import atraintegratedsystems.codes.model.ShortCodeApplicationFeesExtension;
import atraintegratedsystems.codes.repository.ShortCodeApplicationFeesExtensionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShortCodeApplicationFeesFinanceExtensionService {

    private final ShortCodeApplicationFeesExtensionRepository repository;

    public ShortCodeApplicationFeesFinanceExtensionService(
            ShortCodeApplicationFeesExtensionRepository repository) {
        this.repository = repository;
    }

    public List<ShortCodeApplicationFeesExtensionDTO> getUnpaidExtensions() {
        return repository
                .findByApplicationFeeExtensionPaymentStatusIsNull()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ShortCodeApplicationFeesExtensionDTO toDTO(
            ShortCodeApplicationFeesExtension entity) {

        ShortCodeApplicationFeesExtensionDTO dto =
                new ShortCodeApplicationFeesExtensionDTO();

        dto.setId(entity.getId());
        dto.setApplicationFeeExtensionDate(
                entity.getApplicationFeeExtensionDate());
        dto.setApplicationFeeExtentionExpirationDate(
                entity.getApplicationFeeExtentionExpirationDate());
        dto.setApplicationFeeExtendedFees(
                entity.getApplicationFeeExtendedFees());
        dto.setApplicationFeeExtensionBankVoucherNo(
                entity.getApplicationFeeExtensionBankVoucherNo());
        dto.setApplicationFeeExtensionEnteryVoucherDate(
                entity.getApplicationFeeExtensionEnteryVoucherDate());
        dto.setApplicationFeeExtensionBankVoucherSubmissionDate(
                entity.getApplicationFeeExtensionBankVoucherSubmissionDate());
        dto.setApplicationFeeExtensionPaymentStatus(
                entity.getApplicationFeeExtensionPaymentStatus());
        dto.setExtendStatus(entity.getExtendStatus());
        dto.setExtendDate(entity.getExtendDate());

        return dto;
    }

    /* ===== GET BY ID (FOR PAY FORM) ===== */
    public ShortCodeApplicationFeesExtensionDTO getById(Long id) {
        ShortCodeApplicationFeesExtension entity =
                repository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Record not found"));

        ShortCodeApplicationFeesExtensionDTO dto =
                new ShortCodeApplicationFeesExtensionDTO();

        dto.setId(entity.getId());
        dto.setApplicationFeeExtendedFees(entity.getApplicationFeeExtendedFees());
        dto.setApplicationFeeExtensionBankVoucherNo(
                entity.getApplicationFeeExtensionBankVoucherNo());
        dto.setApplicationFeeExtensionEnteryVoucherDate(
                entity.getApplicationFeeExtensionEnteryVoucherDate());
        dto.setApplicationFeeExtensionBankVoucherSubmissionDate(
                entity.getApplicationFeeExtensionBankVoucherSubmissionDate());

        return dto;
    }

    /* ===== UPDATE PAYMENT ONLY ===== */
    public void updatePayment(ShortCodeApplicationFeesExtensionDTO dto) {

        ShortCodeApplicationFeesExtension entity =
                repository.findById(dto.getId())
                        .orElseThrow(() -> new RuntimeException("Record not found"));

        entity.setApplicationFeeExtendedFees(
                dto.getApplicationFeeExtendedFees());
        entity.setApplicationFeeExtensionBankVoucherNo(
                dto.getApplicationFeeExtensionBankVoucherNo());
        entity.setApplicationFeeExtensionEnteryVoucherDate(
                dto.getApplicationFeeExtensionEnteryVoucherDate());
        entity.setApplicationFeeExtensionBankVoucherSubmissionDate(
                dto.getApplicationFeeExtensionBankVoucherSubmissionDate());

        /* IMPORTANT */
        entity.setApplicationFeeExtensionPaymentStatus("PAID");

        repository.save(entity);
    }
}
