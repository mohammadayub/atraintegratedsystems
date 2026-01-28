package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.model.ShortCode;
import atraintegratedsystems.codes.repository.ShortCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ShortCodeService {

    @Autowired
    public ShortCodeRepository shortCodeRepository;

    public Optional<ShortCode> getCode(int id){
       return shortCodeRepository.findById(id) ;
    }

    public List<ShortCode> getAllCodes(){
        return shortCodeRepository.findAll();
    }


    public List<Object[]> getCodeDetails() {
        return shortCodeRepository.getCodeDetails();
    }

    public List<Object[]> getShortCodes(){
        return shortCodeRepository.getShortCodes();
    }


    //Get Shortcodes Digits Allocation Table
    public List<Object[]> getCodeData() {
        return shortCodeRepository.getCodeData();
    }

}
