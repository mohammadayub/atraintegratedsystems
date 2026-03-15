package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.IspcExtensionDetailDTO;
import atraintegratedsystems.codes.dto.IspcExtensionViewDTO;
import atraintegratedsystems.codes.model.IspcExtensionDetail;
import atraintegratedsystems.codes.repository.IspcExtensionDetailRepository;
import atraintegratedsystems.utils.PersianCalendarUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class IspcCodFinanceExtensionService {

    private final IspcExtensionDetailRepository repository;

    public IspcCodFinanceExtensionService(IspcExtensionDetailRepository repository) {
        this.repository = repository;
    }

    public List<IspcExtensionViewDTO> getAllExtensions() {
        return repository.findAllExtensionDetails();
    }

    /* FETCH FOR UPDATE */

    public IspcExtensionDetailDTO getExtensionForUpdate(Long id) {

        IspcExtensionDetail entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Extension not found"));

        IspcExtensionDetailDTO dto = new IspcExtensionDetailDTO();

        dto.setId(entity.getId());
        dto.setExtendedFees(entity.getExtendedFees());
        dto.setExtensionBankVoucherNo(entity.getExtensionBankVoucherNo());
        dto.setExtensionEnteryVoucherDate(entity.getExtensionEnteryVoucherDate());
        dto.setExtensionBankVoucherSubmissionDate(entity.getExtensionBankVoucherSubmissionDate());

        return dto;
    }

    /* UPDATE */

    @Transactional
    public void updateFinanceExtension(IspcExtensionDetailDTO dto, String currentUser) {

        IspcExtensionDetail entity = repository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Extension not found"));

        LocalDate entryVoucherDate = null;
        LocalDate submissionDate = null;

        // Convert Jalali Entry Voucher Date
        if (dto.getExtensionEnteryVoucherDateJalali() != null &&
                !dto.getExtensionEnteryVoucherDateJalali().trim().isEmpty()) {

            String[] parts = dto.getExtensionEnteryVoucherDateJalali().split("-");

            entryVoucherDate = PersianCalendarUtils.jalaliToGregorian(
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2])
            );

        } else if (dto.getExtensionEnteryVoucherDate() != null) {
            entryVoucherDate = dto.getExtensionEnteryVoucherDate();
        }

        // Convert Jalali Submission Date
        if (dto.getExtensionBankVoucherSubmissionDateJalali() != null &&
                !dto.getExtensionBankVoucherSubmissionDateJalali().trim().isEmpty()) {

            String[] parts = dto.getExtensionBankVoucherSubmissionDateJalali().split("-");

            submissionDate = PersianCalendarUtils.jalaliToGregorian(
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2])
            );

        } else if (dto.getExtensionBankVoucherSubmissionDate() != null) {
            submissionDate = dto.getExtensionBankVoucherSubmissionDate();
        }

        // USER INPUT
        entity.setExtendedFees(dto.getExtendedFees());
        entity.setExtensionBankVoucherNo(dto.getExtensionBankVoucherNo());
        entity.setExtensionEnteryVoucherDate(entryVoucherDate);
        entity.setExtensionBankVoucherSubmissionDate(submissionDate);

        // SYSTEM CONTROLLED
        entity.setExtensionPaymentStatus("PAID");
        entity.setExtensionBy(currentUser);

        repository.save(entity);
    }
}