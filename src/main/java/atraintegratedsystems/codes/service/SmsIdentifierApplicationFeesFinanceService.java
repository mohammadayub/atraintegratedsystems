package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.SmsIdentifierDetailDTO;
import atraintegratedsystems.codes.repository.SmsIdentifierDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
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
    public void saveApplicationFees(SmsIdentifierDetailDTO dto) {

        repository.updateApplicationFees(
                dto.getId(),
                dto.getApplicationFees(),
                dto.getApplicationFeesBankVoucherNo(),
                dto.getApplicationFeesEnteryVoucherDate() == null
                        ? null
                        : Date.valueOf(dto.getApplicationFeesEnteryVoucherDate()),
                dto.getApplicationFeesBankVoucherSubmissionDate() == null
                        ? null
                        : Date.valueOf(dto.getApplicationFeesBankVoucherSubmissionDate()),
                dto.getApplicationFeesPaymentStatus()
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
