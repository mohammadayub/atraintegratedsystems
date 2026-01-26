package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.ShortCodeApplicationFeesExtensionDTO;
import atraintegratedsystems.codes.model.CodeDetail;
import atraintegratedsystems.codes.model.ShortCodeApplicationFeesExtension;
import atraintegratedsystems.codes.repository.CodeDetailRepository;
import atraintegratedsystems.codes.repository.ShortCodeApplicationFeesExtensionRepository;
import atraintegratedsystems.utils.PersianCalendarUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ShortCodeApplicationFeesExtensionService {

    @Autowired
    private CodeDetailRepository codeDetailRepository;

    @Autowired
    private ShortCodeApplicationFeesExtensionRepository extensionRepository;

    /* =========================
       LIST UNPAID EXTENSIONS
       ========================= */
    public List<Object[]> findUnPaidApplicationFeeForExtension() {
        return codeDetailRepository.findUnPaidApplicationFeeForExtension();
    }

    /* =========================
       GET CODE DETAIL
       ========================= */
    public CodeDetail getCodeDetailById(Long id) {
        return codeDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CodeDetail not found with id: " + id));
    }

    public void saveExtension(Long codeDetailId, ShortCodeApplicationFeesExtensionDTO dto) {

        CodeDetail codeDetail = getCodeDetailById(codeDetailId);

        ShortCodeApplicationFeesExtension entity = new ShortCodeApplicationFeesExtension();

        PersianCalendarUtils converter = new PersianCalendarUtils();

        // -------------------------------
        // 1️⃣ Extension Date
        // -------------------------------
        if (dto.getApplicationFeeExtensionDateJalali() != null &&
                !dto.getApplicationFeeExtensionDateJalali().isEmpty()) {

            String[] partsEntry = dto.getApplicationFeeExtensionDateJalali().split("-");
            if (partsEntry.length == 3) {
                int jYear = Integer.parseInt(partsEntry[0]);
                int jMonth = Integer.parseInt(partsEntry[1]);
                int jDay = Integer.parseInt(partsEntry[2]);
                LocalDate enterAppExtendDate = converter.jalaliToGregorian(jYear, jMonth, jDay);
                entity.setApplicationFeeExtensionDate(enterAppExtendDate);
            }
        }

        // -------------------------------
        // 2️⃣ Expiration Date
        // -------------------------------
        if (dto.getApplicationFeeExtensionExpirationDateJalali() != null &&
                !dto.getApplicationFeeExtensionExpirationDateJalali().isEmpty()) {

            String[] partsExpiry = dto.getApplicationFeeExtensionExpirationDateJalali().split("-");
            if (partsExpiry.length == 3) {
                int jExpiryYear = Integer.parseInt(partsExpiry[0]);
                int jExpiryMonth = Integer.parseInt(partsExpiry[1]);
                int jExpiryDay = Integer.parseInt(partsExpiry[2]);
                LocalDate enterAppExtendExpirationDate = converter.jalaliToGregorian(jExpiryYear, jExpiryMonth, jExpiryDay);
                entity.setApplicationFeeExtensionExpirationDate(enterAppExtendExpirationDate);
            }
        }

        // -------------------------------
        // 3️⃣ Entry Voucher Date
        // -------------------------------
        if (dto.getApplicationFeeExtensionEntryVoucherDateJalali() != null &&
                !dto.getApplicationFeeExtensionEntryVoucherDateJalali().isEmpty()) {

            String[] partsEntryVoucherDate = dto.getApplicationFeeExtensionEntryVoucherDateJalali().split("-");
            if (partsEntryVoucherDate.length == 3) {
                int jVoucherYear = Integer.parseInt(partsEntryVoucherDate[0]);
                int jVoucherMonth = Integer.parseInt(partsEntryVoucherDate[1]);
                int jVoucherDay = Integer.parseInt(partsEntryVoucherDate[2]);
                LocalDate entryVoucherDate = converter.jalaliToGregorian(jVoucherYear, jVoucherMonth, jVoucherDay);
                entity.setApplicationFeeExtensionEntryVoucherDate(entryVoucherDate);
            }
        }

        // -------------------------------
        // 4️⃣ Bank Voucher Submission Date
        // -------------------------------
        if (dto.getApplicationFeeExtensionBankVoucherSubmissionDateJalali() != null &&
                !dto.getApplicationFeeExtensionBankVoucherSubmissionDateJalali().isEmpty()) {

            String[] partsSubmissionDate = dto.getApplicationFeeExtensionBankVoucherSubmissionDateJalali().split("-");
            if (partsSubmissionDate.length == 3) {
                int jSubmissionYear = Integer.parseInt(partsSubmissionDate[0]);
                int jSubmissionMonth = Integer.parseInt(partsSubmissionDate[1]);
                int jSubmissionDay = Integer.parseInt(partsSubmissionDate[2]);
                LocalDate submissionDate = converter.jalaliToGregorian(jSubmissionYear, jSubmissionMonth, jSubmissionDay);
                entity.setApplicationFeeExtensionBankVoucherSubmissionDate(submissionDate);
            }
        }

        // -------------------------------
        // Other fields
        // -------------------------------
        entity.setApplicationFeeExtendedFees(dto.getApplicationFeeExtendedFees());
        entity.setApplicationFeeExtensionBankVoucherNo(dto.getApplicationFeeExtensionBankVoucherNo());
        entity.setApplicationFeeExtensionPaymentStatus(dto.getApplicationFeeExtensionPaymentStatus());

        // ✅ Automatic fields
        entity.setExtendStatus("EXTENDED");
        entity.setExtendEntryDate(LocalDate.now().toString());

        // Relation
        entity.setCodeDetail(codeDetail);

        // Save entity
        extensionRepository.save(entity);
    }

}
