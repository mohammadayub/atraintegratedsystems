package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.SmsIdentifierDetailDTO;
import atraintegratedsystems.codes.model.SmsIdentifierDetail;
import atraintegratedsystems.codes.repository.SmsIdentifierDetailRepository;
import atraintegratedsystems.utils.PersianCalendarUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SmsIdentifierRejectionService {

    private final SmsIdentifierDetailRepository repository;

    public List<SmsIdentifierDetailDTO> getActiveSmsIdentifierDetails() {
        return repository.findAllWhereShortCodeNotRejected();
    }

    public SmsIdentifierDetailDTO getById(Long id) {
        return repository.findById(id)
                .map(d -> {
                    SmsIdentifierDetailDTO dto = new SmsIdentifierDetailDTO();
                    dto.setId(d.getId());
                    dto.setCompanyName(d.getCompanyName());
                    dto.setSmsIdentifierCodeName(
                            d.getSmsIdentifierCode().getSmsIdentifierCodeName()
                    );
                    return dto;
                })
                .orElseThrow(() -> new RuntimeException("Record not found"));
    }


    @Transactional
    public void rejectUsingDto(SmsIdentifierDetailDTO dto) {

        SmsIdentifierDetail detail = repository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Record not found"));

        LocalDate rejectionDate = null;

        if (dto.getShortCodeRejectionDateJalali() != null &&
                !dto.getShortCodeRejectionDateJalali().trim().isEmpty()) {

            String[] parts = dto.getShortCodeRejectionDateJalali().split("-");

            rejectionDate = PersianCalendarUtils.jalaliToGregorian(
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2])
            );
        } else if (dto.getShortCodeRejectionDate() != null) {
            rejectionDate = dto.getShortCodeRejectionDate();
        }

        // Update Detail
        detail.setShortCodeRejectionStatus(dto.getShortCodeRejectionStatus());
        detail.setShortCodeRejectionDate(rejectionDate);

        // 🔴 IMPORTANT PART
        if (detail.getSmsIdentifierCode() != null) {
            detail.getSmsIdentifierCode().setAssignStatus(null);
        }

        repository.save(detail);
    }
}