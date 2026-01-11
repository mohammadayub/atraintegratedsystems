package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.CodeDetailDTO;
import atraintegratedsystems.codes.model.CodeDetail;
import atraintegratedsystems.codes.repository.CodeDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShortCodeRejectionService {
    @Autowired
    private CodeDetailRepository codeDetailRepository;


    public CodeDetail getById(Long id) {
        return codeDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Code Detail not found"));
    }

    public void rejectShortCode(Long id, CodeDetailDTO dto) {

        codeDetailRepository.rejectShortCode(
                id,
                dto.getShortCodeRejectionStatus(),
                dto.getShortCodeRejectionDate()
        );
    }

}
