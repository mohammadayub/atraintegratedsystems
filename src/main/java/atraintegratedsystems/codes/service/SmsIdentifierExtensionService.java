package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.SmsIdentifierDetailDTO;
import atraintegratedsystems.codes.dto.SmsIdentifierExtensionDTO;
import atraintegratedsystems.codes.model.SmsIdentifierDetail;
import atraintegratedsystems.codes.model.SmsIdentifierExtension;
import atraintegratedsystems.codes.repository.SmsIdentifierDetailRepository;
import atraintegratedsystems.codes.repository.SmsIdentifierExtensionRepository;
import atraintegratedsystems.utils.PersianCalendarUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SmsIdentifierExtensionService {

    private final SmsIdentifierDetailRepository smsIdentifierDetailRepository;
    private final SmsIdentifierExtensionRepository smsIdentifierExtensionRepository;

    public List<SmsIdentifierDetailDTO> getActiveSmsIdentifierDetails() {
        return smsIdentifierDetailRepository.findAllWhereShortCodeNotRejected();
    }

    /* ================= OPEN EXTENSION FORM ================= */
    public SmsIdentifierExtensionDTO prepareExtensionForm(Long detailId) {

        SmsIdentifierDetail detail = smsIdentifierDetailRepository.findById(detailId)
                .orElseThrow(() -> new RuntimeException("SMS Identifier not found"));

        SmsIdentifierExtensionDTO dto = new SmsIdentifierExtensionDTO();
        dto.setExtensionStartDate(LocalDate.now());
        dto.setExtentionExpirationDate(detail.getExpirationDate());
//        dto.setExtendStatus("EXTENDED");
//        dto.setExtendDate(LocalDate.now().toString());

        return dto;
    }

    /* ================= SAVE EXTENSION ================= */
    @Transactional
    public void saveExtension(Long detailId, SmsIdentifierExtensionDTO dto) {

        SmsIdentifierDetail detail = smsIdentifierDetailRepository.findById(detailId)
                .orElseThrow(() -> new RuntimeException("SMS Identifier not found"));

        LocalDate startDate = null;
        LocalDate expirationDate = null;

        // 🔹 Convert Extension Start Date
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

        // 🔹 Convert Extension Expiration Date
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

        SmsIdentifierExtension extension = new SmsIdentifierExtension();

        extension.setExtensionStartDate(startDate);
        extension.setExtentionExpirationDate(expirationDate);
        extension.setExtendStatus(dto.getExtendStatus());
        extension.setExtendDate(dto.getExtendDate());
        extension.setSmsIdentifierDetail(detail);
        // ✅ Automatically set status
        extension.setExtendStatus("Extended");

        // ✅ Automatically set current date
        extension.setExtendDate(LocalDate.now().toString());
        smsIdentifierExtensionRepository.save(extension);

        // 🔥 Update main expiration date
        if (expirationDate != null) {
            detail.setExpirationDate(expirationDate);
            smsIdentifierDetailRepository.save(detail);
        }
    }


}
