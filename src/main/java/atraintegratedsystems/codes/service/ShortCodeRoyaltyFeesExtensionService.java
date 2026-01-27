package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.ShortCodeRoyaltyFeesExtensionDTO;
import atraintegratedsystems.codes.model.CodeDetail;
import atraintegratedsystems.codes.model.ShortCodeRoylatyFeesExtension;
import atraintegratedsystems.codes.repository.CodeDetailRepository;
import atraintegratedsystems.codes.repository.ShortCodeRoyaltyFeesExtensionRepository;
import atraintegratedsystems.utils.PersianCalendarUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ShortCodeRoyaltyFeesExtensionService {
    @Autowired
    private CodeDetailRepository codeDetailRepository;
    @Autowired
    private ShortCodeRoyaltyFeesExtensionRepository extensionRepository;


    public List<Object[]> findUnPaidRoyaltyFeeForExtension() {
        return codeDetailRepository.findUnPaidRoyaltyFeeForExtension();
    }

    public void saveRoyaltyFeeExtension(
            Long codeDetailId,
            ShortCodeRoyaltyFeesExtensionDTO dto
    ) {

        CodeDetail codeDetail = codeDetailRepository.findById(codeDetailId)
                .orElseThrow(() ->
                        new RuntimeException("Code Detail not found"));

        ShortCodeRoylatyFeesExtension extension =
                new ShortCodeRoylatyFeesExtension();

        // DTO → Entity
        extension.setRoyaltyFeeExtendedFees(dto.getRoyaltyFeeExtendedFees());
        extension.setRoyaltyFeeExtensionBankVoucherNo(
                dto.getRoyaltyFeeExtensionBankVoucherNo());


//        extension.setRoyaltyFeeExtensionDate(
//                dto.getRoyaltyFeeExtensionDate());

        PersianCalendarUtils converter = new PersianCalendarUtils();

        if (dto.getRoyaltyFeeExtensionDateJalali() != null &&
                !dto.getRoyaltyFeeExtensionDateJalali().isEmpty()) {

            String[] partsExtensionDate = dto.getRoyaltyFeeExtensionDateJalali().split("-");
            if (partsExtensionDate.length == 3) {
                int jExtensionYear = Integer.parseInt(partsExtensionDate[0]);
                int jExtensionMonth = Integer.parseInt(partsExtensionDate[1]);
                int jExtensionDay = Integer.parseInt(partsExtensionDate[2]);
                LocalDate extensionDate = converter.jalaliToGregorian(jExtensionYear, jExtensionMonth, jExtensionDay);
                extension.setRoyaltyFeeExtensionDate(extensionDate);
            }
        }



//        extension.setRoyaltyFeeExtentionExpirationDate(
//                dto.getRoyaltyFeeExtensionExpirationDate());



        if (dto.getRoyaltyFeeExtensionExpirationDateJalali() != null &&
                !dto.getRoyaltyFeeExtensionExpirationDateJalali().isEmpty()) {

            String[] parts = dto.getRoyaltyFeeExtensionExpirationDateJalali().split("-");
            if (parts.length == 3) {
                int year = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int day = Integer.parseInt(parts[2]);

                LocalDate gregorianDate = converter.jalaliToGregorian(year, month, day);
                extension.setRoyaltyFeeExtentionExpirationDate(gregorianDate);
            }
        }


//        extension.setRoyaltyFeeExtensionEnteryVoucherDate(
//                dto.getRoyaltyFeeExtensionEntryVoucherDate());

        if (dto.getRoyaltyFeeExtensionEntryVoucherDateJalali() != null &&
                !dto.getRoyaltyFeeExtensionEntryVoucherDateJalali().isEmpty()) {

            String[] parts = dto.getRoyaltyFeeExtensionEntryVoucherDateJalali().split("-");
            if (parts.length == 3) {
                int year = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int day = Integer.parseInt(parts[2]);

                LocalDate gregorianDate = converter.jalaliToGregorian(year, month, day);
                extension.setRoyaltyFeeExtensionEnteryVoucherDate(gregorianDate);
            }
        }











//        extension.setRoyaltyFeeExtensionBankVoucherSubmissionDate(
//                dto.getRoyaltyFeeExtensionBankVoucherSubmissionDate());

        if (dto.getRoyaltyFeeExtensionBankVoucherSubmissionDateJalali() != null &&
                !dto.getRoyaltyFeeExtensionBankVoucherSubmissionDateJalali().isEmpty()) {

            String[] parts = dto.getRoyaltyFeeExtensionBankVoucherSubmissionDateJalali().split("-");
            if (parts.length == 3) {
                int year = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int day = Integer.parseInt(parts[2]);

                LocalDate gregorianDate = converter.jalaliToGregorian(year, month, day);
                extension.setRoyaltyFeeExtensionBankVoucherSubmissionDate(gregorianDate);
            }
        }









        extension.setRoyaltyFeeExtensionPaymentStatus(
                dto.getRoyaltyFeeExtensionPaymentStatus());

        // 🔥 SYSTEM VALUES (AUTOMATIC)
        extension.setExtendStatus("EXTENDED");
        extension.setExtendDate(LocalDate.now().toString());

        // 🔥 AUTO LINK
        extension.setCodeDetail(codeDetail);

        extensionRepository.save(extension);
    }




}
