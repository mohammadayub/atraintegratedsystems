package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.SmsIdentifierExtensionDTO;
import atraintegratedsystems.codes.dto.SmsIdentifierFinanceExtensionDTO;
import atraintegratedsystems.codes.model.SmsIdentifierExtension;
import atraintegratedsystems.codes.repository.SmsIdentifierDetailRepository;
import atraintegratedsystems.codes.repository.SmsIdentifierExtensionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SmsIdentifierFinanceExtensionService {

    @Autowired
    private  SmsIdentifierDetailRepository repository;
    @Autowired
    private SmsIdentifierExtensionRepository smsIdentifierExtensionRepository;


    public List<SmsIdentifierFinanceExtensionDTO> getFinanceExtensions() {
        return repository.findFinanceExtensions();
    }


    /* FETCH FOR UPDATE */
    public SmsIdentifierExtensionDTO getExtensionForUpdate(Long id) {
        SmsIdentifierExtension entity = smsIdentifierExtensionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Extension not found"));

        SmsIdentifierExtensionDTO dto = new SmsIdentifierExtensionDTO();
        dto.setId(entity.getId());
        dto.setExtensionBankVoucherNo(entity.getExtensionBankVoucherNo());
        dto.setExtensionEnteryVoucherDate(entity.getExtensionEnteryVoucherDate());
        dto.setExtensionBankVoucherSubmissionDate(entity.getExtensionBankVoucherSubmissionDate());

        return dto;
    }

    /* UPDATE */
    public void updateFinanceExtension(SmsIdentifierExtensionDTO dto, String currentUser) {

        SmsIdentifierExtension entity = smsIdentifierExtensionRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Extension not found"));

        // USER ENTERED
        entity.setExtensionBankVoucherNo(dto.getExtensionBankVoucherNo());
        entity.setExtensionEnteryVoucherDate(dto.getExtensionEnteryVoucherDate());
        entity.setExtensionBankVoucherSubmissionDate(dto.getExtensionBankVoucherSubmissionDate());

        // SYSTEM CONTROLLED
        entity.setExtensionPaymentStatus("PAID");
        entity.setExtensionEnteryDate(LocalDate.now());
        entity.setExtensionBy(currentUser);

        smsIdentifierExtensionRepository.save(entity);
    }
}
