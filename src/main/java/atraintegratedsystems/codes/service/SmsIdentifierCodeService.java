package atraintegratedsystems.codes.service;
import atraintegratedsystems.codes.repository.SmsIdentifierCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SmsIdentifierCodeService {

    @Autowired
    private SmsIdentifierCodeRepository smsIdentifierCodeRepository;
    public List<Object[]> getSMSIdentifierCodes(){
        return smsIdentifierCodeRepository.getSMSIdentifiersWithStatus();
    }
}
