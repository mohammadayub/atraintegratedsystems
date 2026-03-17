package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.ShortCodeExtendedFeesExtensionDTO;
import atraintegratedsystems.codes.model.ShortCodeExtendedFeesExtension;
import atraintegratedsystems.codes.repository.ShortCodeExtendedFeesExtensionRepository;
import atraintegratedsystems.utils.PersianCalendarUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShortCodeExtendedFeesFinanceExtensionService {

    private final ShortCodeExtendedFeesExtensionRepository repository;

    public ShortCodeExtendedFeesFinanceExtensionService(
            ShortCodeExtendedFeesExtensionRepository repository) {
        this.repository = repository;
    }

    public List<ShortCodeExtendedFeesExtensionDTO> getUnpaidExtensions() {
        return repository
                .findByExtendedFeeExtensionPaymentStatusIsNull()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ShortCodeExtendedFeesExtensionDTO toDTO(
            ShortCodeExtendedFeesExtension entity) {

        ShortCodeExtendedFeesExtensionDTO dto =
                new ShortCodeExtendedFeesExtensionDTO();

        dto.setId(entity.getId());
        dto.setExtendedFeeExtensionDate(
                entity.getExtendedFeeExtensionDate());
        dto.setExtendedFeeExtensionExpirationDate(
                entity.getExtendedFeeExtensionExpirationDate());
        dto.setExtendedFees(
                entity.getExtendedFees());
        dto.setExtendedFeeExtensionBankVoucherNo(
                entity.getExtendedFeeExtensionBankVoucherNo());
        dto.setExtendedFeeExtensionEntryVoucherDate(
                entity.getExtendedFeeExtensionEntryVoucherDate());
        dto.setExtendedFeeExtensionBankVoucherSubmissionDate(
                entity.getExtendedFeeExtensionBankVoucherSubmissionDate());
        dto.setExtendedFeeExtensionPaymentStatus(
                entity.getExtendedFeeExtensionPaymentStatus());
        dto.setExtendStatus(entity.getExtendStatus());
        dto.setExtendDate(entity.getExtendEntryDate());

        return dto;
    }

    /* ===== GET BY ID (FOR PAY FORM) ===== */
    public ShortCodeExtendedFeesExtensionDTO getById(Long id) {
        ShortCodeExtendedFeesExtension entity =
                repository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Record not found"));

        ShortCodeExtendedFeesExtensionDTO dto =
                new ShortCodeExtendedFeesExtensionDTO();

        dto.setId(entity.getId());
        dto.setExtendedFees(entity.getExtendedFees());
        dto.setExtendedFeeExtensionBankVoucherNo(
                entity.getExtendedFeeExtensionBankVoucherNo());
        dto.setExtendedFeeExtensionEntryVoucherDate(
                entity.getExtendedFeeExtensionEntryVoucherDate());
        dto.setExtendedFeeExtensionBankVoucherSubmissionDate(
                entity.getExtendedFeeExtensionBankVoucherSubmissionDate());

        return dto;
    }

    /* ===== UPDATE PAYMENT ONLY ===== */
    public void updatePayment(ShortCodeExtendedFeesExtensionDTO dto) {

        ShortCodeExtendedFeesExtension entity = repository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Record not found with ID: " + dto.getId()));

        PersianCalendarUtils converter = new PersianCalendarUtils();

        /* ================= FEES ================= */
        entity.setExtendedFees(dto.getExtendedFees());
        entity.setExtendedFeeExtensionBankVoucherNo(dto.getExtendedFeeExtensionBankVoucherNo());

        /* ================= ENTRY VOUCHER DATE (JALALI → GREGORIAN) ================= */
        if (dto.getExtendedFeeExtensionEntryVoucherDateJalali() != null &&
                !dto.getExtendedFeeExtensionEntryVoucherDateJalali().trim().isEmpty()) {

            try {
                String[] parts = dto.getExtendedFeeExtensionEntryVoucherDateJalali().split("-");

                if (parts.length == 3) {
                    int year = Integer.parseInt(parts[0]);
                    int month = Integer.parseInt(parts[1]);
                    int day = Integer.parseInt(parts[2]);

                    LocalDate voucherDate = converter.jalaliToGregorian(year, month, day);
                    entity.setExtendedFeeExtensionEntryVoucherDate(voucherDate);
                }
            } catch (Exception e) {
                throw new RuntimeException("Invalid Entry Voucher Jalali Date format. Expected yyyy-MM-dd");
            }
        }

        /* ================= SUBMISSION DATE (JALALI → GREGORIAN) ================= */
        if (dto.getExtendedFeeExtensionBankVoucherSubmissionDateJalali() != null &&
                !dto.getExtendedFeeExtensionBankVoucherSubmissionDateJalali().trim().isEmpty()) {

            try {
                String[] parts = dto.getExtendedFeeExtensionBankVoucherSubmissionDateJalali().split("-");

                if (parts.length == 3) {
                    int year = Integer.parseInt(parts[0]);
                    int month = Integer.parseInt(parts[1]);
                    int day = Integer.parseInt(parts[2]);

                    LocalDate submissionDate = converter.jalaliToGregorian(year, month, day);
                    entity.setExtendedFeeExtensionBankVoucherSubmissionDate(submissionDate);
                }
            } catch (Exception e) {
                throw new RuntimeException("Invalid Submission Jalali Date format. Expected yyyy-MM-dd");
            }
        }

        /* ================= PAYMENT STATUS ================= */
        entity.setExtendedFeeExtensionPaymentStatus("PAID");

        repository.save(entity);
    }



}
