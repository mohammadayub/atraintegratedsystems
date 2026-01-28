package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.ShortCodeDetailDTO;
import atraintegratedsystems.codes.model.ShortCodeDetail;
import atraintegratedsystems.codes.repository.ShortCodeDetailRepository;
import atraintegratedsystems.codes.repository.ShortCodeRepository;
import atraintegratedsystems.utils.PersianCalendarUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ShortCodesDetailPaymentsConfirmationFinanceService {
    @Autowired
    private ShortCodeDetailRepository shortCodeDetailRepository;

    @Autowired
    private ShortCodeRepository shortCodeRepository;

    // find uppaid application fees
    public List<Object[]> getunPaidApplicationFees() {
        return shortCodeDetailRepository.findunPaidApplicationFee();
    }

// ✅ Load unpaid application fee for edit
    public Optional<ShortCodeDetail> getUnpaidApplicationFeeForEdit(Long id) {
        return shortCodeDetailRepository.findUnpaidApplicationFeeById(id);
    }


    @Transactional
    public boolean confirmApplicationFee(ShortCodeDetailDTO dto) {

        ShortCodeDetail codeDetail = shortCodeDetailRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid code ID: " + dto.getId()));

        PersianCalendarUtils converter = new PersianCalendarUtils();

        // ===== Entry Voucher Date (Jalali → Gregorian) =====
        if (dto.getApplicationFeeEnterVoucherDateJalali() != null &&
                !dto.getApplicationFeeEnterVoucherDateJalali().isEmpty()) {

            String[] partsEntry = dto.getApplicationFeeEnterVoucherDateJalali().split("-");
            int jYear = Integer.parseInt(partsEntry[0]);
            int jMonth = Integer.parseInt(partsEntry[1]);
            int jDay = Integer.parseInt(partsEntry[2]);

            LocalDate enterVoucherDate =
                    converter.jalaliToGregorian(jYear, jMonth, jDay);

            codeDetail.setApplicationFeeEnterVoucherDate(enterVoucherDate);
        }

        // ===== Submission Date (Jalali → Gregorian) =====
        if (dto.getApplicationFeebankVoucherSubmissionDateJalali() != null &&
                !dto.getApplicationFeebankVoucherSubmissionDateJalali().isEmpty()) {

            String[] partsSubmission =
                    dto.getApplicationFeebankVoucherSubmissionDateJalali().split("-");
            int jYearSub = Integer.parseInt(partsSubmission[0]);
            int jMonthSub = Integer.parseInt(partsSubmission[1]);
            int jDaySub = Integer.parseInt(partsSubmission[2]);

            LocalDate submissionDate =
                    converter.jalaliToGregorian(jYearSub, jMonthSub, jDaySub);

            codeDetail.setApplicationFeebankVoucherSubmissionDate(submissionDate);
        }

        // ===== Voucher Number =====
        codeDetail.setApplicationFeebankVoucherNo(
                dto.getApplicationFeebankVoucherNo());

        // ✅ IMPORTANT PART — AUTO SET STATUS
        codeDetail.setApplicationFeesStatus("PAID");

        // (Optional) overall payment status
        codeDetail.setPaymentStatus("PAID");

        shortCodeDetailRepository.save(codeDetail);

        return true;
    }




//    Royalty Fee Section

    public List<Object[]> getunPaidRyaltyFees() {
        return shortCodeDetailRepository.findunPaidRoyaltyFee();
    }

    // Royalty Fee Print Tariffs Section

    public Optional<ShortCodeDetail> getUnRoyaltyFeeForEdit(Long id) {
        return shortCodeDetailRepository.findUnpaidRoyaltyFeeById(id);
    }

    @Transactional
    public boolean confirmRoyaltyFee(ShortCodeDetailDTO dto) {

        ShortCodeDetail codeDetail = shortCodeDetailRepository.findById(dto.getId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid code ID: " + dto.getId()));

        PersianCalendarUtils converter = new PersianCalendarUtils();

        // ===== Entry Voucher Date (Jalali → Gregorian) =====
        if (dto.getRoyaltyFeeEnterVoucherDateJalali() != null &&
                !dto.getRoyaltyFeeEnterVoucherDateJalali().isEmpty()) {

            String[] partsEntry = dto.getRoyaltyFeeEnterVoucherDateJalali().split("-");
            int jYear = Integer.parseInt(partsEntry[0]);
            int jMonth = Integer.parseInt(partsEntry[1]);
            int jDay = Integer.parseInt(partsEntry[2]);

            LocalDate enterVoucherDate =
                    converter.jalaliToGregorian(jYear, jMonth, jDay);

            codeDetail.setRoyaltyFeeEnterVoucherDate(enterVoucherDate);
        }

        // ===== Submission Date (Jalali → Gregorian) =====
        if (dto.getRoyaltyFeeBankVoucherSubmissionDateJalali() != null &&
                !dto.getRoyaltyFeeBankVoucherSubmissionDateJalali().isEmpty()) {

            String[] partsSubmission =
                    dto.getRoyaltyFeeBankVoucherSubmissionDateJalali().split("-");
            int jYearSub = Integer.parseInt(partsSubmission[0]);
            int jMonthSub = Integer.parseInt(partsSubmission[1]);
            int jDaySub = Integer.parseInt(partsSubmission[2]);

            LocalDate submissionDate =
                    converter.jalaliToGregorian(jYearSub, jMonthSub, jDaySub);

            codeDetail.setRoyaltyFeeBankVoucherSubmissionDate(submissionDate);
        }

        // ===== Voucher Number =====
        codeDetail.setRoyaltyFeebankVoucherNo(dto.getRoyaltyFeebankVoucherNo());

        // ✅ IMPORTANT PART — AUTO SET ROYALTY STATUS
        codeDetail.setRoyaltyFeesStatus("PAID");

        // (Optional but consistent with application fee)
        codeDetail.setPaymentStatus("PAID");

        shortCodeDetailRepository.save(codeDetail);

        return true;
    }




    // Royalty Fee Extension section
    public List<Object[]> getUnPaidRyaltyFeesForExtension() {
        return shortCodeDetailRepository.findUnPaidRoyaltyFeeForExtension();
    }

    // Application Fee Extension Section
    public List<Object[]> getUnPaidApplicationFeesForExtension() {
        return shortCodeDetailRepository.findUnPaidApplicationFeeForExtension();
    }
}
