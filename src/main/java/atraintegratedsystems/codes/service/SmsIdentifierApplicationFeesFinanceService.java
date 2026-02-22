package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.SmsIdentifierDetailDTO;
import atraintegratedsystems.codes.repository.SmsIdentifierDetailRepository;
import atraintegratedsystems.utils.PersianCalendarUtils;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SmsIdentifierApplicationFeesFinanceService {

    @Autowired
    private SmsIdentifierDetailRepository repository;

    public List<SmsIdentifierDetailDTO> getPendingSmsIdentifiers() {

        List<Object[]> rows = repository.findPendingDetailsNative();
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
    public void saveApplicationFees(SmsIdentifierDetailDTO dto) {

        LocalDate entryVoucherDate = null;
        LocalDate submissionDate = null;

        if (dto.getApplicationFeesEnteryVoucherDateJalali() != null &&
                !dto.getApplicationFeesEnteryVoucherDateJalali().trim().isEmpty()) {

            String[] parts = dto.getApplicationFeesEnteryVoucherDateJalali().split("-");
            entryVoucherDate = PersianCalendarUtils.jalaliToGregorian(
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2])
            );
        }

        if (dto.getApplicationFeesBankVoucherSubmissionDateJalali() != null &&
                !dto.getApplicationFeesBankVoucherSubmissionDateJalali().trim().isEmpty()) {

            String[] parts = dto.getApplicationFeesBankVoucherSubmissionDateJalali().split("-");
            submissionDate = PersianCalendarUtils.jalaliToGregorian(
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2])
            );
        }

        java.sql.Date entrySqlDate = null;
        java.sql.Date submissionSqlDate = null;

        if (entryVoucherDate != null) {
            entrySqlDate = java.sql.Date.valueOf(entryVoucherDate);
        }

        if (submissionDate != null) {
            submissionSqlDate = java.sql.Date.valueOf(submissionDate);
        }

        repository.updateApplicationFees(
                dto.getId(),
                dto.getApplicationFees(),
                dto.getApplicationFeesBankVoucherNo(),
                entrySqlDate,
                submissionSqlDate,
                "PAID"
        );


    }


    // Tariff Section
    public SmsIdentifierDetailDTO getTariffById(Long id) {

        List<Object[]> rows = repository.findTariffById(id);

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
