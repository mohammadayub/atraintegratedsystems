package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.SmsIdentifierDetailDTO;
import atraintegratedsystems.codes.repository.SmsIdentifierDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


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

    public void rejectUsingDto(SmsIdentifierDetailDTO dto) {
        repository.rejectSmsIdentifier(
                dto.getId(),
                dto.getShortCodeRejectionStatus(),
                dto.getShortCodeRejectionDate()
        );
    }
}