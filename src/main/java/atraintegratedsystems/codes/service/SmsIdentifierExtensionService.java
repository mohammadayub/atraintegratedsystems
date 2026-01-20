package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.SmsIdentifierDetailDTO;
import atraintegratedsystems.codes.dto.SmsIdentifierExtensionDTO;
import atraintegratedsystems.codes.model.SmsIdentifierDetail;
import atraintegratedsystems.codes.model.SmsIdentifierExtension;
import atraintegratedsystems.codes.repository.SmsIdentifierDetailRepository;
import atraintegratedsystems.codes.repository.SmsIdentifierExtensionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        dto.setExtendStatus("PENDING");
        dto.setExtendDate(LocalDate.now().toString());

        return dto;
    }

    /* ================= SAVE EXTENSION ================= */
    public void saveExtension(Long detailId, SmsIdentifierExtensionDTO dto) {

        SmsIdentifierDetail detail = smsIdentifierDetailRepository.findById(detailId)
                .orElseThrow(() -> new RuntimeException("SMS Identifier not found"));

        SmsIdentifierExtension extension = new SmsIdentifierExtension();

        extension.setExtensionStartDate(dto.getExtensionStartDate());
        extension.setExtentionExpirationDate(dto.getExtentionExpirationDate());
        extension.setExtendStatus(dto.getExtendStatus());
        extension.setExtendDate(dto.getExtendDate());
        extension.setSmsIdentifierDetail(detail);

        smsIdentifierExtensionRepository.save(extension);

        // 🔥 Update main expiration date
        detail.setExpirationDate(dto.getExtentionExpirationDate());
        smsIdentifierDetailRepository.save(detail);
    }

}
