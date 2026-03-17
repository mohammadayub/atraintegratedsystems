package atraintegratedsystems.codes.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Getter
@Setter
public class ShortCodeExtendedFeesExtensionDTO {
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extendedFeeExtensionDate;
    private String extendedFeeExtensionDateJalali;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extendedFeeExtensionExpirationDate;
    private String extendedFeeExtensionExpirationDateJalali;

    // Bellow is Application Fee Extension Fields
    private double extendedFees;
    private String extendedFeeExtensionBankVoucherNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extendedFeeExtensionEntryVoucherDate;
    private String extendedFeeExtensionEntryVoucherDateJalali;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extendedFeeExtensionBankVoucherSubmissionDate;
    private String extendedFeeExtensionBankVoucherSubmissionDateJalali;
    private String extendedFeeExtensionPaymentStatus;

    private String extendStatus;

    private String extendDate;
}
