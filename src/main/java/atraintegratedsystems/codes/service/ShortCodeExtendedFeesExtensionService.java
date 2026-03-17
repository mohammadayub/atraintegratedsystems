package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.ShortCodeExtendedFeesExtensionDTO;
import atraintegratedsystems.codes.model.ShortCodeExtendedFeesExtension;
import atraintegratedsystems.codes.model.ShortCodeDetail;
import atraintegratedsystems.codes.repository.ShortCodeDetailRepository;
import atraintegratedsystems.codes.repository.ShortCodeExtendedFeesExtensionRepository;
import atraintegratedsystems.utils.PersianCalendarUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ShortCodeExtendedFeesExtensionService {

    @Autowired
    private ShortCodeDetailRepository shortCodeDetailRepository;

    @Autowired
    private ShortCodeExtendedFeesExtensionRepository extensionRepository;

    /* =========================
       LIST UNPAID EXTENSIONS
       ========================= */
    public List<Object[]> findUnPaidApplicationFeeForExtension() {
        return shortCodeDetailRepository.findUnPaidApplicationFeeForExtension();
    }

    /* =========================
       GET CODE DETAIL
       ========================= */
    public ShortCodeDetail getCodeDetailById(Long id) {
        return shortCodeDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CodeDetail not found with id: " + id));
    }

    public void saveExtension(Long codeDetailId, ShortCodeExtendedFeesExtensionDTO dto) {

        ShortCodeDetail codeDetail = getCodeDetailById(codeDetailId);

        ShortCodeExtendedFeesExtension entity = new ShortCodeExtendedFeesExtension();

        PersianCalendarUtils converter = new PersianCalendarUtils();

        // -------------------------------
        // 1️⃣ Extension Date
        // -------------------------------
        if (dto.getExtendedFeeExtensionDateJalali() != null &&
                !dto.getExtendedFeeExtensionDateJalali().isEmpty()) {

            String[] partsEntry = dto.getExtendedFeeExtensionDateJalali().split("-");
            if (partsEntry.length == 3) {
                int jYear = Integer.parseInt(partsEntry[0]);
                int jMonth = Integer.parseInt(partsEntry[1]);
                int jDay = Integer.parseInt(partsEntry[2]);
                LocalDate enterAppExtendDate = converter.jalaliToGregorian(jYear, jMonth, jDay);
                entity.setExtendedFeeExtensionDate(enterAppExtendDate);
            }
        }

        // -------------------------------
        // 2️⃣ Expiration Date
        // -------------------------------
        if (dto.getExtendedFeeExtensionExpirationDateJalali() != null &&
                !dto.getExtendedFeeExtensionExpirationDateJalali().isEmpty()) {

            String[] partsExpiry = dto.getExtendedFeeExtensionExpirationDateJalali().split("-");
            if (partsExpiry.length == 3) {
                int jExpiryYear = Integer.parseInt(partsExpiry[0]);
                int jExpiryMonth = Integer.parseInt(partsExpiry[1]);
                int jExpiryDay = Integer.parseInt(partsExpiry[2]);
                LocalDate enterAppExtendExpirationDate = converter.jalaliToGregorian(jExpiryYear, jExpiryMonth, jExpiryDay);
                entity.setExtendedFeeExtensionExpirationDate(enterAppExtendExpirationDate);
            }
        }

        // -------------------------------
        // 3️⃣ Entry Voucher Date
        // -------------------------------
        if (dto.getExtendedFeeExtensionEntryVoucherDateJalali() != null &&
                !dto.getExtendedFeeExtensionEntryVoucherDateJalali().isEmpty()) {

            String[] partsEntryVoucherDate = dto.getExtendedFeeExtensionEntryVoucherDateJalali().split("-");
            if (partsEntryVoucherDate.length == 3) {
                int jVoucherYear = Integer.parseInt(partsEntryVoucherDate[0]);
                int jVoucherMonth = Integer.parseInt(partsEntryVoucherDate[1]);
                int jVoucherDay = Integer.parseInt(partsEntryVoucherDate[2]);
                LocalDate entryVoucherDate = converter.jalaliToGregorian(jVoucherYear, jVoucherMonth, jVoucherDay);
                entity.setExtendedFeeExtensionEntryVoucherDate(entryVoucherDate);
            }
        }

        // -------------------------------
        // 4️⃣ Bank Voucher Submission Date
        // -------------------------------
        if (dto.getExtendedFeeExtensionBankVoucherSubmissionDateJalali() != null &&
                !dto.getExtendedFeeExtensionBankVoucherSubmissionDateJalali().isEmpty()) {

            String[] partsSubmissionDate = dto.getExtendedFeeExtensionBankVoucherSubmissionDateJalali().split("-");
            if (partsSubmissionDate.length == 3) {
                int jSubmissionYear = Integer.parseInt(partsSubmissionDate[0]);
                int jSubmissionMonth = Integer.parseInt(partsSubmissionDate[1]);
                int jSubmissionDay = Integer.parseInt(partsSubmissionDate[2]);
                LocalDate submissionDate = converter.jalaliToGregorian(jSubmissionYear, jSubmissionMonth, jSubmissionDay);
                entity.setExtendedFeeExtensionBankVoucherSubmissionDate(submissionDate);
            }
        }

        // -------------------------------
        // Other fields
        // -------------------------------
        entity.setExtendedFees(dto.getExtendedFees());
        entity.setExtendedFeeExtensionBankVoucherNo(dto.getExtendedFeeExtensionBankVoucherNo());
        entity.setExtendedFeeExtensionPaymentStatus(dto.getExtendedFeeExtensionPaymentStatus());

        // ✅ Automatic fields
        entity.setExtendStatus("EXTENDED");
        entity.setExtendEntryDate(LocalDate.now().toString());

        // Relation
        entity.setShortCodeDetail(codeDetail);

        // Save entity
        extensionRepository.save(entity);
    }


}
