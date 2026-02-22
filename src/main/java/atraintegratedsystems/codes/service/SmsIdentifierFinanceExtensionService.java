package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.SmsIdentifierExtensionDTO;
import atraintegratedsystems.codes.dto.SmsIdentifierFinanceExtensionDTO;
import atraintegratedsystems.codes.model.SmsIdentifierExtension;
import atraintegratedsystems.codes.repository.SmsIdentifierDetailRepository;
import atraintegratedsystems.codes.repository.SmsIdentifierExtensionRepository;
import atraintegratedsystems.utils.PersianCalendarUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    @Transactional
    public void updateFinanceExtension(SmsIdentifierExtensionDTO dto, String currentUser) {

        SmsIdentifierExtension entity = smsIdentifierExtensionRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Extension not found"));

        LocalDate entryVoucherDate = null;
        LocalDate submissionDate = null;

        // 🔹 Convert Entry Voucher Date
        if (dto.getExtensionEnteryVoucherDateJalali() != null &&
                !dto.getExtensionEnteryVoucherDateJalali().trim().isEmpty()) {

            String[] parts = dto.getExtensionEnteryVoucherDateJalali().split("-");

            entryVoucherDate = PersianCalendarUtils.jalaliToGregorian(
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2])
            );

        } else if (dto.getExtensionEnteryVoucherDate() != null) {
            entryVoucherDate = dto.getExtensionEnteryVoucherDate();
        }

        // 🔹 Convert Submission Date
        if (dto.getExtensionBankVoucherSubmissionDateJalali() != null &&
                !dto.getExtensionBankVoucherSubmissionDateJalali().trim().isEmpty()) {

            String[] parts = dto.getExtensionBankVoucherSubmissionDateJalali().split("-");

            submissionDate = PersianCalendarUtils.jalaliToGregorian(
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2])
            );

        } else if (dto.getExtensionBankVoucherSubmissionDate() != null) {
            submissionDate = dto.getExtensionBankVoucherSubmissionDate();
        }

        // 🔹 USER ENTERED
        entity.setExtendedFees(dto.getExtendedFees());
        entity.setExtensionBankVoucherNo(dto.getExtensionBankVoucherNo());
        entity.setExtensionEnteryVoucherDate(entryVoucherDate);
        entity.setExtensionBankVoucherSubmissionDate(submissionDate);

        // 🔹 SYSTEM CONTROLLED
        entity.setExtensionPaymentStatus("PAID");
        entity.setExtensionEnteryDate(LocalDate.now());
        entity.setExtensionBy(currentUser);

        smsIdentifierExtensionRepository.save(entity);
    }

}
