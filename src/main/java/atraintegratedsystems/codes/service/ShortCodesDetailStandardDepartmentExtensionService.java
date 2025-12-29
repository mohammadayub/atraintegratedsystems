package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.model.CodeExtensionDetail;
import atraintegratedsystems.codes.repository.CodeExtensionDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShortCodesDetailStandardDepartmentExtensionService {

    @Autowired
    public CodeExtensionDetailRepository codeExtensionDetailRepository;

    public List<CodeExtensionDetail> getAllCodeExtensionDetails(){
        return codeExtensionDetailRepository.findAll();
    }

    public void AddCodeExtensionContract(CodeExtensionDetail codeExtensionDetail){
        codeExtensionDetailRepository.save(codeExtensionDetail);
    }

    public Optional<CodeExtensionDetail> getExtensionById(Long id){
        return codeExtensionDetailRepository.findById(id);
    }

    public void deleteContractExtension(Long id){
        codeExtensionDetailRepository.deleteById(id);
    }


    public List<CodeExtensionDetail> findUnpaid(){
        return codeExtensionDetailRepository.findUnpaid();
    }

}
