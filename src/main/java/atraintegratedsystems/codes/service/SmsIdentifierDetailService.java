package atraintegratedsystems.codes.service;
import atraintegratedsystems.codes.model.SmsIdentifierDetail;
import atraintegratedsystems.codes.repository.SmsIdentifierDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SmsIdentifierDetailService {

    @Autowired
    private SmsIdentifierDetailRepository smsIdentifierDetailRepository;

    public void AddSmsIdentifier(SmsIdentifierDetail code){
        smsIdentifierDetailRepository.save(code);
    }
    public List<SmsIdentifierDetail> getAllDetailCodes(){
        return smsIdentifierDetailRepository.findAll();
    }

    public Optional<SmsIdentifierDetail> getSMSDetailId(Long id){
        return smsIdentifierDetailRepository.findById(id);
    }

    public void deleteSmsIdentifier(Long id){
        smsIdentifierDetailRepository.deleteById(id);
    }

    public List<Object[]> getCompanyWithCodes(){
        return smsIdentifierDetailRepository.getCompanyNamesWithCodes();
    }


    public List<SmsIdentifierDetail> findSmsUnPaid(){
        return smsIdentifierDetailRepository.findUnpaid();
    }
}
