package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.SmsIdentifierDetailDTO;
import atraintegratedsystems.codes.repository.SmsIdentifierDetailRepository;
import atraintegratedsystems.utils.PersianCalendarUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SmsIdentifierRoyaltyFeesFinanceService {
    @Autowired
    private SmsIdentifierDetailRepository repository;

    public List<SmsIdentifierDetailDTO> getPendingSmsIdentifiers() {

        List<Object[]> rows = repository.findUnPaidRoyaltyFees();
        List<SmsIdentifierDetailDTO> list = new ArrayList<>();

        for (Object[] row : rows) {

            SmsIdentifierDetailDTO dto = new SmsIdentifierDetailDTO();

            dto.setId(((Number) row[0]).longValue());
            dto.setCompanyName((String) row[1]);
            dto.setEnid((String) row[2]);
            dto.setCompanyAddress((String) row[3]);
            dto.setMobile((String) row[4]);
            dto.setTelephone((String) row[5]);
            dto.setEmail((String) row[6]);
            dto.setChannel((String) row[7]);
            dto.setServiceType((String) row[8]);
            dto.setMnosCompanyHost((String) row[9]);
            dto.setCodeCategory((String) row[10]);

            // ✅ FIX: sql.Date → LocalDate
            Date assigningDate = (Date) row[11];
            Date expirationDate = (Date) row[12];

            if (assigningDate != null) {
                dto.setAssigningDate(assigningDate.toLocalDate());
            }

            if (expirationDate != null) {
                dto.setExpirationDate(expirationDate.toLocalDate());
            }

            // ✅ VERY IMPORTANT: DO NOT overwrite enid
            dto.setSmsIdentifierCodeName((String) row[13]);

            list.add(dto);
        }
        return list;
    }

    // Confirm Section
    @Transactional
    public void saveRoyaltyFees(SmsIdentifierDetailDTO dto) {

        LocalDate entryVoucherDate = null;
        LocalDate submissionDate = null;

        // Entry Date Conversion
        if (dto.getRoyaltyFeesEnteryVoucherDateJalali() != null &&
                !dto.getRoyaltyFeesEnteryVoucherDateJalali().trim().isEmpty()) {

            String[] parts = dto.getRoyaltyFeesEnteryVoucherDateJalali().split("-");
            entryVoucherDate = PersianCalendarUtils.jalaliToGregorian(
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2])
            );
        }

        // Submission Date Conversion
        if (dto.getRoyaltyFeesBankVoucherSubmissionDateJalali() != null &&
                !dto.getRoyaltyFeesBankVoucherSubmissionDateJalali().trim().isEmpty()) {

            String[] parts = dto.getRoyaltyFeesBankVoucherSubmissionDateJalali().split("-");
            submissionDate = PersianCalendarUtils.jalaliToGregorian(
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2])
            );
        }

        java.sql.Date entrySqlDate =
                (entryVoucherDate != null) ? java.sql.Date.valueOf(entryVoucherDate) : null;

        java.sql.Date submissionSqlDate =
                (submissionDate != null) ? java.sql.Date.valueOf(submissionDate) : null;

        // ✅ Automatically set PAID
        String paymentStatus = "PAID";
        repository.updateRoyaltyFees(
                dto.getId(),
                dto.getRoyaltyFees(),
                dto.getRoyaltyFeesBankVoucherNo(),
                entrySqlDate,
                submissionSqlDate,
                paymentStatus
        );
    }



    // Tariff Section
    public SmsIdentifierDetailDTO getTariffById(Long id) {

        List<Object[]> rows = repository.findRoyaltyTariff(id);

        if (rows.isEmpty()) {
            throw new RuntimeException("Record not found");
        }

        Object[] row = rows.get(0);

        SmsIdentifierDetailDTO dto = new SmsIdentifierDetailDTO();

        dto.setId(((Number) row[0]).longValue());
        dto.setCompanyName((String) row[1]);
        dto.setEnid((String) row[2]);
        dto.setCompanyAddress((String) row[3]);
        dto.setMobile((String) row[4]);
        dto.setEmail((String) row[5]);
        dto.setChannel((String) row[6]);
        dto.setServiceType((String) row[7]);
        dto.setCodeCategory((String) row[8]);
        dto.setApplicationFees(row[9] == null ? 0 : ((Number) row[9]).doubleValue());
        dto.setApplicationFeesPaymentStatus((String) row[10]);
        dto.setSmsIdentifierCodeName((String) row[11]);

        return dto;
    }
}
