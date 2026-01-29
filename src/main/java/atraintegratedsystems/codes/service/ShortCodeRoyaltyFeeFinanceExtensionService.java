package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.ShortCodeRoyaltyFeesExtensionDTO;
import atraintegratedsystems.codes.model.ShortCodeRoylatyFeesExtension;
import atraintegratedsystems.codes.repository.ShortCodeRoyaltyFeesExtensionRepository;
import atraintegratedsystems.utils.PersianCalendarUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
                entity.getRoyaltyFeeExtensionExpirationDate());
        dto.setRoyaltyFeeExtensionEntryVoucherDate(
                entity.getRoyaltyFeeExtensionEntryVoucherDate());
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
                entity.getRoyaltyFeeExtensionExpirationDate());
        dto.setRoyaltyFeeExtensionEntryVoucherDate(
                entity.getRoyaltyFeeExtensionEntryVoucherDate());
        dto.setRoyaltyFeeExtensionBankVoucherSubmissionDate(
                entity.getRoyaltyFeeExtensionBankVoucherSubmissionDate());

        return dto;
    }

    /* ===== UPDATE PAYMENT ONLY ===== */
    public void updatePayment(ShortCodeRoyaltyFeesExtensionDTO dto) {

        ShortCodeRoylatyFeesExtension entity = repository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Record not found with ID: " + dto.getId()));

        PersianCalendarUtils converter = new PersianCalendarUtils();

        /* ================= VOUCHER NUMBER ================= */
        entity.setRoyaltyFeeExtensionBankVoucherNo(dto.getRoyaltyFeeExtensionBankVoucherNo());

        /* ================= ENTRY VOUCHER DATE ================= */
        if (dto.getRoyaltyFeeExtensionEntryVoucherDateJalali() != null &&
                !dto.getRoyaltyFeeExtensionEntryVoucherDateJalali().trim().isEmpty()) {

            try {
                String[] parts = dto.getRoyaltyFeeExtensionEntryVoucherDateJalali().trim().split("-");

                if (parts.length == 3) {
                    int year = Integer.parseInt(parts[0]);
                    int month = Integer.parseInt(parts[1]);
                    int day = Integer.parseInt(parts[2]);

                    LocalDate voucherDate = converter.jalaliToGregorian(year, month, day);
                    entity.setRoyaltyFeeExtensionEntryVoucherDate(voucherDate); // ✅ FIXED
                }
            } catch (Exception e) {
                throw new RuntimeException("Invalid Entry Voucher Jalali Date format. Expected yyyy-MM-dd");
            }
        }

        /* ================= SUBMISSION DATE ================= */
        if (dto.getRoyaltyFeeExtensionBankVoucherSubmissionDateJalali() != null &&
                !dto.getRoyaltyFeeExtensionBankVoucherSubmissionDateJalali().trim().isEmpty()) {

            try {
                String[] parts = dto.getRoyaltyFeeExtensionBankVoucherSubmissionDateJalali().trim().split("-");

                if (parts.length == 3) {
                    int year = Integer.parseInt(parts[0]);
                    int month = Integer.parseInt(parts[1]);
                    int day = Integer.parseInt(parts[2]);

                    LocalDate submissionDate = converter.jalaliToGregorian(year, month, day);
                    entity.setRoyaltyFeeExtensionBankVoucherSubmissionDate(submissionDate); // ✅ FIXED
                }
            } catch (Exception e) {
                throw new RuntimeException("Invalid Submission Jalali Date format. Expected yyyy-MM-dd");
            }
        }

        /* ================= PAYMENT STATUS ================= */
        entity.setRoyaltyFeeExtensionPaymentStatus("PAID");

        repository.save(entity);
    }

}
