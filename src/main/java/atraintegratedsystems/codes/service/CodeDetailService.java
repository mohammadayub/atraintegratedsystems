package atraintegratedsystems.codes.service;
import atraintegratedsystems.codes.model.Code;
import atraintegratedsystems.codes.model.CodeDetail;
import atraintegratedsystems.codes.repository.CodeDetailRepository;
import atraintegratedsystems.codes.repository.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CodeDetailService {

    @Autowired
    public CodeDetailRepository codeDetailRepository;

    @Autowired
    public CodeRepository codeRepository;
    public void AddShort(CodeDetail code){
        codeDetailRepository.save(code);
    }
    public List<CodeDetail> getAllDetailCodes(){
        return codeDetailRepository.findAll();
    }

    public void deleteCodeDetail(Long id){
        codeDetailRepository.deleteById(id);
    }

    public Optional<Code> getShortCode(int shortCode){
        return codeRepository.findById(shortCode);
    }

    public Optional<CodeDetail> getCodeDetailId(Long id){
        return codeDetailRepository.findById(id);
    }

    public Optional<CodeDetail> getShortByCode(int shortCode){
        return codeDetailRepository.findByShortCode(shortCode);
    }


    public List<Object[]> getCodeDetailWithCompanyNameAndCode() {
        return codeDetailRepository.getCodeDetailWithCompanyNameAndCode();
    }


    public List<CodeDetail> findUnpaid() {
        return codeDetailRepository.findUnpaid();
    }



}
