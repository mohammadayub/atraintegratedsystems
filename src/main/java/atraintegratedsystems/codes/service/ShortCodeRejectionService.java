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

        ShortCodeDetail detail = shortCodeDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Code Detail not found"));

        // 1️⃣ Update rejection info
        detail.setShortCodeRejectionStatus(dto.getShortCodeRejectionStatus());
        detail.setShortCodeRejectionDate(dto.getShortCodeRejectionDate());

        // 2️⃣ IMPORTANT: update ShortCode status
        if (detail.getShortCode() != null) {
            detail.getShortCode().setAssignStatus("UNASSIGN");
        }

        // 3️⃣ Save (Hibernate will flush both entities)
        shortCodeDetailRepository.save(detail);
    }


}
