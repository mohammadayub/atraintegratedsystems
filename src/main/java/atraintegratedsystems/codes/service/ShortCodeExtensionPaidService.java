package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.ShortCodeExtensionViewDTO;
import atraintegratedsystems.codes.repository.ShortCodeExtendedFeesExtensionRepository;
import atraintegratedsystems.utils.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ShortCodeExtensionPaidService {

    @Autowired
    private ShortCodeExtendedFeesExtensionRepository extensionRepository;

    // For Paid Extension List
    public List<ShortCodeExtensionViewDTO> getPaidShortCodeExtensions() {

        List<ShortCodeExtensionViewDTO> list = extensionRepository.findPaidShortExtension();
        DateConverter converter = new DateConverter();

        for (ShortCodeExtensionViewDTO dto : list) {

            dto.setExtendedFeeExtensionDateJalali(
                    toJalali(dto.getExtendedFeeExtensionDate(), converter)
            );

            dto.setExtendedFeeExtensionExpirationDateJalali(
                    toJalali(dto.getExtendedFeeExtensionExpirationDate(), converter)
            );

            dto.setExtendedFeeExtensionEntryVoucherDateJalali(
                    toJalali(dto.getExtendedFeeExtensionEntryVoucherDate(), converter)
            );

            dto.setExtendedFeeExtensionBankVoucherSubmissionDateJalali(
                    toJalali(dto.getExtendedFeeExtensionBankVoucherSubmissionDate(), converter)
            );
        }

        return list;
    }

    private String toJalali(LocalDate date, DateConverter converter) {
        if (date == null) return null;
        return converter
                .gregorianToJalali(date.getYear(), date.getMonthValue(), date.getDayOfMonth())
                .toString();
    }
}