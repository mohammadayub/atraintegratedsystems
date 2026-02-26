package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.ShortCodeDTO;
import atraintegratedsystems.codes.model.ShortCode;
import atraintegratedsystems.codes.repository.ShortCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShortCodeService {

    @Autowired
    private ShortCodeRepository shortCodeRepository;

    public List<ShortCode> getAvailableShortCodes(){
        return shortCodeRepository.findByAssignStatusEqualsOrAssignStatusIsNullOrderByShortCodeNameAsc("UNASSIGN");
    }
    public ShortCode getById(Long id) {
        return shortCodeRepository.findById(id).orElse(null);
    }

    public ShortCode save(ShortCode code) {
        return shortCodeRepository.save(code);
    }

    // Get all short codes
    public List<ShortCode> getAllCodes(){
        return shortCodeRepository.findAll();
    }
    public List<Object[]> getCodeDetails() {
        return shortCodeRepository.getCodeDetails();
    }

    // Save new shortcode
    public void saveShortCode(ShortCodeDTO dto){

        if(shortCodeRepository.existsByShortCodeName(dto.getShortCodeName())){
            throw new RuntimeException("This Short Code already exists.");
        }

        ShortCode code = new ShortCode();
        code.setShortCodeName(dto.getShortCodeName());
        code.setAssignStatus("UNASSIGN");

        shortCodeRepository.save(code);
    }
}