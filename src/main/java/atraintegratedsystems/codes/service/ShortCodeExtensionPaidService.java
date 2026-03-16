package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.ShortCodeExtensionViewDTO;
import atraintegratedsystems.codes.repository.ShortCodeApplicationFeesExtensionRepository;
import atraintegratedsystems.utils.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ShortCodeExtensionPaidService {

    @Autowired
    private ShortCodeApplicationFeesExtensionRepository extensionRepository;

    // Bellow is For Paid Extension List
    public List<ShortCodeExtensionViewDTO> getPaidShortCodeExtensions() {

        List<ShortCodeExtensionViewDTO> list = extensionRepository.findPaidShortExtension();
        DateConverter converter = new DateConverter();

        for (ShortCodeExtensionViewDTO dto : list) {
            dto.setApplicationFeeExtensionDateJalali(toJalali(dto.getApplicationFeeExtensionDate(), converter));
            dto.setApplicationFeeExtensionExpirationDateJalali(toJalali(dto.getApplicationFeeExtensionExpirationDate(), converter));
            dto.setApplicationFeeExtensionEntryVoucherDateJalali(toJalali(dto.getApplicationFeeExtensionEntryVoucherDate(), converter));
            dto.setApplicationFeeExtensionBankVoucherSubmissionDateJalali(toJalali(dto.getApplicationFeeExtensionBankVoucherSubmissionDate(), converter));
        }

        return list;
    }

    private String toJalali(LocalDate date, DateConverter converter) {
        if (date == null) return null;
        return converter.gregorianToJalali(date.getYear(), date.getMonthValue(), date.getDayOfMonth()).toString();
    }
}
