package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.IspcDetailDTO;
import atraintegratedsystems.codes.dto.IspcExtensionDetailDTO;
import atraintegratedsystems.codes.model.IspcDetail;
import atraintegratedsystems.codes.model.IspcExtensionDetail;
import atraintegratedsystems.codes.repository.IspcDetailRepository;
import atraintegratedsystems.codes.repository.IspcExtensionDetailRepository;
import atraintegratedsystems.utils.PersianCalendarUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IspcCodeExtensionService {

    private final IspcDetailRepository ispcDetailRepository;
    private final IspcExtensionDetailRepository ispcExtensionDetailRepository;

    /* ================= ACTIVE ISPC CODES ================= */
    public List<IspcDetailDTO> getActiveIspcDetailCode() {
        return ispcDetailRepository.findIspcDetailCodeForExtension();
    }

    /* ================= OPEN EXTENSION FORM ================= */
    public IspcExtensionDetailDTO prepareExtensionForm(Long detailId) {

        IspcDetail detail = ispcDetailRepository.findById(detailId)
                .orElseThrow(() -> new RuntimeException("ISPC Code not found"));

        IspcExtensionDetailDTO dto = new IspcExtensionDetailDTO();
        dto.setExtensionStartDate(LocalDate.now());
        dto.setExtentionExpirationDate(detail.getExpirationDate());

        return dto;
    }

    /* ================= SAVE EXTENSION ================= */
    @Transactional
    public void saveExtension(Long detailId, IspcExtensionDetailDTO dto) {

        IspcDetail detail = ispcDetailRepository.findById(detailId)
                .orElseThrow(() -> new RuntimeException("ISPC Code not found"));

        LocalDate startDate = null;
        LocalDate expirationDate = null;

        /* 🔹 Convert Start Date */
        if (dto.getExtensionStartDateJalali() != null &&
                !dto.getExtensionStartDateJalali().trim().isEmpty()) {

            String[] parts = dto.getExtensionStartDateJalali().split("-");

            startDate = PersianCalendarUtils.jalaliToGregorian(
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2])
            );

        } else if (dto.getExtensionStartDate() != null) {
            startDate = dto.getExtensionStartDate();
        }

        /* 🔹 Convert Expiration Date */
        if (dto.getExtentionExpirationDateJalali() != null &&
                !dto.getExtentionExpirationDateJalali().trim().isEmpty()) {

            String[] parts = dto.getExtentionExpirationDateJalali().split("-");

            expirationDate = PersianCalendarUtils.jalaliToGregorian(
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2])
            );

        } else if (dto.getExtentionExpirationDate() != null) {
            expirationDate = dto.getExtentionExpirationDate();
        }

        /* 🔹 Create Extension */
        IspcExtensionDetail extension = new IspcExtensionDetail();

        extension.setExtensionStartDate(startDate);
        extension.setExtentionExpirationDate(expirationDate);
        extension.setIspcDetail(detail);

        // Auto values
        extension.setExtendStatus("Extended");

        extension.setExtendedFees(dto.getExtendedFees());
        extension.setExtensionBankVoucherNo(dto.getExtensionBankVoucherNo());
        extension.setExtensionEnteryVoucherDate(dto.getExtensionEnteryVoucherDate());
        extension.setExtensionBankVoucherSubmissionDate(dto.getExtensionBankVoucherSubmissionDate());
        extension.setExtensionPaymentStatus(dto.getExtensionPaymentStatus());
        extension.setExtensionBy(dto.getExtensionBy());

        ispcExtensionDetailRepository.save(extension);

        /* 🔥 Update main expiration date */
        if (expirationDate != null) {
            detail.setExpirationDate(expirationDate);
            ispcDetailRepository.save(detail);
        }
    }
}