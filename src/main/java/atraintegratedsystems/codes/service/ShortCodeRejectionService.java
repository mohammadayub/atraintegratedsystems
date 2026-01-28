package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.ShortCodeDetailDTO;
import atraintegratedsystems.codes.model.ShortCodeDetail;
import atraintegratedsystems.codes.repository.ShortCodeDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShortCodeRejectionService {
    @Autowired
    private ShortCodeDetailRepository shortCodeDetailRepository;


    public ShortCodeDetail getById(Long id) {
        return shortCodeDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Code Detail not found"));
    }

    public void rejectShortCode(Long id, ShortCodeDetailDTO dto) {

        shortCodeDetailRepository.rejectShortCode(
                id,
                dto.getShortCodeRejectionStatus(),
                dto.getShortCodeRejectionDate()
        );
    }

}
