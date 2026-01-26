package atraintegratedsystems.codes.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
@Getter
@Setter
public class ShortCodeApplicationFeesExtensionDTO {
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeeExtensionDate;
    private String applicationFeeExtensionDateJalali;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeeExtensionExpirationDate;
    private String applicationFeeExtensionExpirationDateJalali;

    // Bellow is Application Fee Extension Fields
    private double applicationFeeExtendedFees;
    private String applicationFeeExtensionBankVoucherNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeeExtensionEntryVoucherDate;
    private String applicationFeeExtensionEntryVoucherDateJalali;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeeExtensionBankVoucherSubmissionDate;
    private String applicationFeeExtensionBankVoucherSubmissionDateJalali;
    private String applicationFeeExtensionPaymentStatus;

    private String extendStatus;

    private String extendDate;
}
