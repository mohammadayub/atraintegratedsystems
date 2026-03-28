package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.ShortCodeExtensionViewDTO;
import atraintegratedsystems.codes.repository.ShortCodeExtendedFeesExtensionRepository;
import atraintegratedsystems.utils.DateConverter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ShortCodeExtensionPaidService {

    private final ShortCodeExtendedFeesExtensionRepository repository;

    public ShortCodeExtensionPaidService(ShortCodeExtendedFeesExtensionRepository repository) {
        this.repository = repository;
    }

    public List<ShortCodeExtensionViewDTO> getPaidShortCodeExtensions() {

        List<ShortCodeExtensionViewDTO> list = repository.findPaidShortExtension();
        DateConverter converter = new DateConverter();

        for (ShortCodeExtensionViewDTO dto : list) {
            fillJalali(dto, converter);
        }

        return list;
    }

    public ShortCodeExtensionViewDTO getExtensionByCodeDetailId(Long id) {

        ShortCodeExtensionViewDTO dto = repository.findPaidShortExtensionById(id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not found"));

        fillJalali(dto, new DateConverter());
        return dto;
    }

    private void fillJalali(ShortCodeExtensionViewDTO dto, DateConverter converter) {

        dto.setAssigningDateJalali(toJalali(dto.getAssigningDate(), converter));
        dto.setExpirationDateJalali(toJalali(dto.getExpirationDate(), converter));

        dto.setExtendedFeeExtensionDateJalali(toJalali(dto.getExtendedFeeExtensionDate(), converter));
        dto.setExtendedFeeExtensionExpirationDateJalali(toJalali(dto.getExtendedFeeExtensionExpirationDate(), converter));
        dto.setExtendedFeeExtensionEntryVoucherDateJalali(toJalali(dto.getExtendedFeeExtensionEntryVoucherDate(), converter));
        dto.setExtendedFeeExtensionBankVoucherSubmissionDateJalali(toJalali(dto.getExtendedFeeExtensionBankVoucherSubmissionDate(), converter));
    }

    private String toJalali(LocalDate date, DateConverter converter) {
        if (date == null) return null;
        return converter.gregorianToJalali(
                date.getYear(),
                date.getMonthValue(),
                date.getDayOfMonth()
        ).toString();
    }
}