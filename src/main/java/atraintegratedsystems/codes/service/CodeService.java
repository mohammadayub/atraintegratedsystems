package atraintegratedsystems.codes.service;

import atra.erp.codes.model.Code;
import atra.erp.codes.repository.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CodeService {

    @Autowired
    public CodeRepository codeRepository;

    public Optional<Code> getCode(int id){
       return codeRepository.findById(id) ;
    }

    public List<Code> getAllCodes(){
        return codeRepository.findAll();
    }


    public List<Object[]> getCodeDetails() {
        return codeRepository.getCodeDetails();
    }

    public List<Object[]> getShortCodes(){
        return codeRepository.getShortCodes();
    }


    //Get Shortcodes Digits Allocation Table
    public List<Object[]> getCodeData() {
        return codeRepository.getCodeData();
    }

}
