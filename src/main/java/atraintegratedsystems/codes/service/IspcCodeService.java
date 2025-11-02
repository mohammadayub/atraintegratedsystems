package atraintegratedsystems.codes.service;

import atra.erp.codes.repository.IspcCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IspcCodeService {

    @Autowired
    private IspcCodeRepository ispcCodeRepository;
    public List<Object[]> getIspcCodes(){
        return ispcCodeRepository.getIspcCodes();
    }
}
