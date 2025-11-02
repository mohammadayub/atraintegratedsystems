package atraintegratedsystems.codes.service;



import atra.erp.codes.model.IspcExtensionDetail;
import atra.erp.codes.repository.IspcExtensionDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IspcExtensionDetailService {

    @Autowired
    public IspcExtensionDetailRepository ispcExtensionDetailRepository;

    public List<IspcExtensionDetail> getAllIspcExtensionDetails(){
        return ispcExtensionDetailRepository.findAll();
    }

    public void AddIspcExtensionContract(IspcExtensionDetail ispcExtensionDetail){
        ispcExtensionDetailRepository.save(ispcExtensionDetail);
    }

    public Optional<IspcExtensionDetail> getExtensionById(Long id){
        return ispcExtensionDetailRepository.findById(id);
    }

    public void deleteContractExtension(Long id){
        ispcExtensionDetailRepository.deleteById(id);
    }

    public List<IspcExtensionDetail> findUnpaid(){
        return ispcExtensionDetailRepository.findUnpaid();
    }
}
