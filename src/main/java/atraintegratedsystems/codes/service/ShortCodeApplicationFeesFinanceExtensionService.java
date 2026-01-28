package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.ShortCodeApplicationFeesExtensionDTO;
import atraintegratedsystems.codes.model.ShortCodeApplicationFeesExtension;
import atraintegratedsystems.codes.repository.ShortCodeApplicationFeesExtensionRepository;
import atraintegratedsystems.utils.PersianCalendarUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        dto.setApplicationFeeExtensionExpirationDate(
                entity.getApplicationFeeExtensionExpirationDate());
        dto.setApplicationFeeExtendedFees(
                entity.getApplicationFeeExtendedFees());
        dto.setApplicationFeeExtensionBankVoucherNo(
                entity.getApplicationFeeExtensionBankVoucherNo());
        dto.setApplicationFeeExtensionEntryVoucherDate(
                entity.getApplicationFeeExtensionEntryVoucherDate());
        dto.setApplicationFeeExtensionBankVoucherSubmissionDate(
                entity.getApplicationFeeExtensionBankVoucherSubmissionDate());
        dto.setApplicationFeeExtensionPaymentStatus(
                entity.getApplicationFeeExtensionPaymentStatus());
        dto.setExtendStatus(entity.getExtendStatus());
        dto.setExtendDate(entity.getExtendEntryDate());

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
        dto.setApplicationFeeExtensionEntryVoucherDate(
                entity.getApplicationFeeExtensionEntryVoucherDate());
        dto.setApplicationFeeExtensionBankVoucherSubmissionDate(
                entity.getApplicationFeeExtensionBankVoucherSubmissionDate());

        return dto;
    }

    /* ===== UPDATE PAYMENT ONLY ===== */
    public void updatePayment(ShortCodeApplicationFeesExtensionDTO dto) {

        ShortCodeApplicationFeesExtension entity = repository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Record not found with ID: " + dto.getId()));

        PersianCalendarUtils converter = new PersianCalendarUtils();

        /* ================= FEES ================= */
        entity.setApplicationFeeExtendedFees(dto.getApplicationFeeExtendedFees());
        entity.setApplicationFeeExtensionBankVoucherNo(dto.getApplicationFeeExtensionBankVoucherNo());

        /* ================= ENTRY VOUCHER DATE (JALALI → GREGORIAN) ================= */
        if (dto.getApplicationFeeExtensionEntryVoucherDateJalali() != null &&
                !dto.getApplicationFeeExtensionEntryVoucherDateJalali().trim().isEmpty()) {

            try {
                String[] parts = dto.getApplicationFeeExtensionEntryVoucherDateJalali().split("-");

                if (parts.length == 3) {
                    int year = Integer.parseInt(parts[0]);
                    int month = Integer.parseInt(parts[1]);
                    int day = Integer.parseInt(parts[2]);

                    LocalDate voucherDate = converter.jalaliToGregorian(year, month, day);
                    entity.setApplicationFeeExtensionEntryVoucherDate(voucherDate);
                }
            } catch (Exception e) {
                throw new RuntimeException("Invalid Entry Voucher Jalali Date format. Expected yyyy-MM-dd");
            }
        }

        /* ================= SUBMISSION DATE (JALALI → GREGORIAN) ================= */
        if (dto.getApplicationFeeExtensionBankVoucherSubmissionDateJalali() != null &&
                !dto.getApplicationFeeExtensionBankVoucherSubmissionDateJalali().trim().isEmpty()) {

            try {
                String[] parts = dto.getApplicationFeeExtensionBankVoucherSubmissionDateJalali().split("-");

                if (parts.length == 3) {
                    int year = Integer.parseInt(parts[0]);
                    int month = Integer.parseInt(parts[1]);
                    int day = Integer.parseInt(parts[2]);

                    LocalDate submissionDate = converter.jalaliToGregorian(year, month, day);
                    entity.setApplicationFeeExtensionBankVoucherSubmissionDate(submissionDate);
                }
            } catch (Exception e) {
                throw new RuntimeException("Invalid Submission Jalali Date format. Expected yyyy-MM-dd");
            }
        }

        /* ================= PAYMENT STATUS ================= */
        entity.setApplicationFeeExtensionPaymentStatus("PAID");

        repository.save(entity);
    }

}
