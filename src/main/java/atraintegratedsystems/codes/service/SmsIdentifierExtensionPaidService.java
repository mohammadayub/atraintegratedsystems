package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.SmsIdentifierExtensionViewDTO;
import atraintegratedsystems.codes.repository.SmsIdentifierExtensionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SmsIdentifierExtensionPaidService {

    @Autowired
    private SmsIdentifierExtensionRepository repository;

    public List<SmsIdentifierExtensionViewDTO> getAllExtensions() {
        return repository.findAllWithExtensions();
    }

    public List<SmsIdentifierExtensionViewDTO> getPaidExtensions() {
        return repository.findAllPaidExtensions();
    }

}
