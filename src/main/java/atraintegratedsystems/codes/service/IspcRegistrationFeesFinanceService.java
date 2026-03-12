package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.IspcDetailDTO;
import atraintegratedsystems.codes.dto.SmsIdentifierDetailDTO;
import atraintegratedsystems.codes.model.IspcDetail;
import atraintegratedsystems.codes.repository.IspcDetailRepository;
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
@Transactional
public class IspcRegistrationFeesFinanceService {

    @Autowired
    private IspcDetailRepository ispcDetailRepository;

    public List<IspcDetailDTO> getPendingIspcCodes() {

        List<Object[]> rows = ispcDetailRepository.findPendingDetailsNative();
        List<IspcDetailDTO> list = new ArrayList<>();

        for (Object[] row : rows) {

            IspcDetailDTO dto = new IspcDetailDTO();

            dto.setId(((Number) row[0]).longValue());
            dto.setCompanyName((String) row[1]);
            dto.setEnid((String) row[2]);
            dto.setCompanyAddress((String) row[3]);
            dto.setMobile((String) row[4]);
            dto.setTelephone((String) row[5]);
            dto.setEmail((String) row[6]);
            // ✅ FIX: sql.Date → LocalDate
            Date assigningDate = (Date) row[7];
            Date expirationDate = (Date) row[8];

            if (assigningDate != null) {
                dto.setAssigningDate(assigningDate.toLocalDate());
            }

            if (expirationDate != null) {
                dto.setExpirationDate(expirationDate.toLocalDate());
            }

            // ✅ VERY IMPORTANT: DO NOT overwrite enid
            dto.setIspcCodeName((String) row[9]);

            list.add(dto);
        }
        return list;
    }

    // Confirm Section
    @Transactional
    public void saveRegistrationFees(IspcDetailDTO dto) {

        LocalDate entryVoucherDate = null;
        LocalDate submissionDate = null;

        if (dto.getRegistrationFeesEntryVoucherDateJalali() != null &&
                !dto.getRegistrationFeesEntryVoucherDateJalali().trim().isEmpty()) {

            String[] parts = dto.getRegistrationFeesEntryVoucherDateJalali().split("-");
            entryVoucherDate = PersianCalendarUtils.jalaliToGregorian(
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2])
            );
        }

        if (dto.getRegistrationFeesBankVoucherSubmissionDateJalali() != null &&
                !dto.getRegistrationFeesBankVoucherSubmissionDateJalali().trim().isEmpty()) {

            String[] parts = dto.getRegistrationFeesBankVoucherSubmissionDateJalali().split("-");
            submissionDate = PersianCalendarUtils.jalaliToGregorian(
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2])
            );
        }

        Date entrySqlDate = null;
        Date submissionSqlDate = null;

        if (entryVoucherDate != null) {
            entrySqlDate = Date.valueOf(entryVoucherDate);
        }

        if (submissionDate != null) {
            submissionSqlDate = Date.valueOf(submissionDate);
        }

        ispcDetailRepository.updateRegistrationFees(
                dto.getId(),
                dto.getRegistrationFees(),
                dto.getRegistrationFeesBankVoucherNo(),
                entrySqlDate,
                submissionSqlDate,
                "PAID"
        );


    }


    // Tariff Section
    public IspcDetailDTO getTariffById(Long id) {

        List<Object[]> rows =ispcDetailRepository.findTariffById(id);

        if (rows.isEmpty()) {
            throw new RuntimeException("Record not found");
        }

        Object[] row = rows.get(0);
        IspcDetailDTO dto = new IspcDetailDTO();
        dto.setId(((Number) row[0]).longValue());
        dto.setCompanyName((String) row[1]);
        dto.setEnid((String) row[2]);
        dto.setCompanyAddress((String) row[3]);
        dto.setMobile((String) row[4]);
        dto.setEmail((String) row[5]);
        dto.setRegistrationFees(row[6] == null ? 0 : ((Number) row[6]).doubleValue());
        dto.setRegistrationFeesPaymentStatus((String) row[7]);
        dto.setIspcCodeName((String) row[8]);

        return dto;
    }


    public IspcDetailDTO getById(Long id) {

        IspcDetail detail = ispcDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found with id: " + id));

        return mapToDTO(detail);
    }


    private IspcDetailDTO mapToDTO(IspcDetail detail) {

        IspcDetailDTO dto = new IspcDetailDTO();
        dto.setId(detail.getId());
        dto.setRegistrationFees(detail.getRegistrationFees());
        return dto;
    }

}
