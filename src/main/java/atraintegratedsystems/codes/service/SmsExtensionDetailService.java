package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.model.SmsExtensionDetail;
import atraintegratedsystems.codes.repository.SmsExtensionDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SmsExtensionDetailService {

    @Autowired
    public SmsExtensionDetailRepository smsExtensionDetailRepository;

    public List<SmsExtensionDetail> getAllSmsExtensionDetails(){
        return smsExtensionDetailRepository.findAll();
    }

    public void AddSmsExtensionContract(SmsExtensionDetail smsExtensionDetail){
        smsExtensionDetailRepository.save(smsExtensionDetail);
    }

    public Optional<SmsExtensionDetail> getExtensionById(Long id){
        return smsExtensionDetailRepository.findById(id);
    }

    public void deleteContractExtension(Long id){
        smsExtensionDetailRepository.deleteById(id);
    }

    public List<SmsExtensionDetail> findUnpaid(){
        return smsExtensionDetailRepository.findUnpaid();
    }


}
