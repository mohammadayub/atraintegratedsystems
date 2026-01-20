package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.CodeDetailDTO;
import atraintegratedsystems.codes.model.CodeDetail;
import atraintegratedsystems.codes.repository.CodeDetailRepository;
import atraintegratedsystems.codes.repository.CodeRepository;
import atraintegratedsystems.utils.DateConverter;
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
    private CodeDetailRepository codeDetailRepository;

    @Autowired
    private CodeRepository codeRepository;

    // find uppaid application fees
    public List<Object[]> getunPaidApplicationFees() {
        return codeDetailRepository.findunPaidApplicationFee();
    }

// ✅ Load unpaid application fee for edit
    public Optional<CodeDetail> getUnpaidApplicationFeeForEdit(Long id) {
        return codeDetailRepository.findUnpaidApplicationFeeById(id);
    }



    @Transactional
    public boolean confirmApplicationFee(CodeDetailDTO dto) {
        // Retrieve CodeDetail by ID and throw an exception if not found
        CodeDetail codeDetail = codeDetailRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid code ID: " + dto.getId()));

        PersianCalendarUtils converter = new PersianCalendarUtils();
        LocalDate enterVoucherDate = null;
        LocalDate submissionDate = null;

        // Convert Entry Voucher Date (Jalali → Gregorian)
        if (dto.getApplicationFeeEnterVoucherDateJalali() != null &&
                !dto.getApplicationFeeEnterVoucherDateJalali().trim().isEmpty()) {
            try {
                String[] partsEntry = dto.getApplicationFeeEnterVoucherDateJalali().split("-");
                int jYear = Integer.parseInt(partsEntry[0]);
                int jMonth = Integer.parseInt(partsEntry[1]);
                int jDay = Integer.parseInt(partsEntry[2]);
                enterVoucherDate = converter.jalaliToGregorian(jYear, jMonth, jDay);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                throw new IllegalArgumentException("Invalid format for entry voucher date.");
            }
        }
        codeDetail.setApplicationFeeEnterVoucherDate(enterVoucherDate);

        // Convert Submission Date (Jalali → Gregorian)
        if (dto.getApplicationFeebankVoucherSubmissionDateJalali() != null &&
                !dto.getApplicationFeebankVoucherSubmissionDateJalali().trim().isEmpty()) {
            try {
                String[] partsSubmission = dto.getApplicationFeebankVoucherSubmissionDateJalali().split("-");
                int jYearSub = Integer.parseInt(partsSubmission[0]);
                int jMonthSub = Integer.parseInt(partsSubmission[1]);
                int jDaySub = Integer.parseInt(partsSubmission[2]);
                submissionDate = converter.jalaliToGregorian(jYearSub, jMonthSub, jDaySub);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                throw new IllegalArgumentException("Invalid format for submission date.");
            }
        }
        codeDetail.setApplicationFeebankVoucherSubmissionDate(submissionDate);

        // Set Voucher Number
        codeDetail.setApplicationFeebankVoucherNo(dto.getApplicationFeebankVoucherNo());

        // Save the updated code detail
        codeDetailRepository.save(codeDetail);

        return true;
    }


//    Royalty Fee Section

    public List<Object[]> getunPaidRyaltyFees() {
        return codeDetailRepository.findunPaidRoyaltyFee();
    }

    // Royalty Fee Print Tariffs Section

    public Optional<CodeDetail> getUnRoyaltyFeeForEdit(Long id) {
        return codeDetailRepository.findUnpaidRoyaltyFeeById(id);
    }

    // Royalty Fees Confirmation Section
    @Transactional
    public boolean confirmRoyaltyFee(CodeDetailDTO dto) {

        CodeDetail codeDetail = codeDetailRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid code ID: " + dto.getId()));

        PersianCalendarUtils converter = new PersianCalendarUtils();

            String[] partsEntry = dto.getRoyaltyFeeEnterVoucherDateJalali().split("-");
            int jYear = Integer.parseInt(partsEntry[0]);
            int jMonth = Integer.parseInt(partsEntry[1]);
            int jDay = Integer.parseInt(partsEntry[2]);

            LocalDate enterVoucherDate = converter.jalaliToGregorian(jYear, jMonth, jDay);
            codeDetail.setRoyaltyFeeEnterVoucherDate(enterVoucherDate);


        // ===== Submission Date (Jalali → Gregorian) =====


            String[] partsSubmission = dto.getRoyaltyFeeBankVoucherSubmissionDateJalali().split("-");
            int jYearSub = Integer.parseInt(partsSubmission[0]);
            int jMonthSub = Integer.parseInt(partsSubmission[1]);
            int jDaySub = Integer.parseInt(partsSubmission[2]);

            LocalDate submissionDate = converter.jalaliToGregorian(jYearSub, jMonthSub, jDaySub);
            codeDetail.setRoyaltyFeeBankVoucherSubmissionDate(submissionDate);


        // ===== Voucher Number =====
        codeDetail.setRoyaltyFeebankVoucherNo(dto.getRoyaltyFeebankVoucherNo());

        codeDetailRepository.save(codeDetail);

        return true;
    }



    // Royalty Fee Extension section
    public List<Object[]> getUnPaidRyaltyFeesForExtension() {
        return codeDetailRepository.findUnPaidRoyaltyFeeForExtension();
    }

    // Application Fee Extension Section
    public List<Object[]> getUnPaidApplicationFeesForExtension() {
        return codeDetailRepository.findUnPaidApplicationFeeForExtension();
    }
}
