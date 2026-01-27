package atraintegratedsystems.codes.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Getter
@Setter
public class ShortCodeRoyaltyFeesExtensionDTO {

    private Long id;

    private double royaltyFeeExtendedFees;
    private String royaltyFeeExtensionBankVoucherNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate royaltyFeeExtensionDate;
    private String royaltyFeeExtensionDateJalali;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate royaltyFeeExtensionExpirationDate;
    private String royaltyFeeExtensionExpirationDateJalali;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate royaltyFeeExtensionEntryVoucherDate;
    private String royaltyFeeExtensionEntryVoucherDateJalali;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate royaltyFeeExtensionBankVoucherSubmissionDate;
    private String royaltyFeeExtensionBankVoucherSubmissionDateJalali;
    private String royaltyFeeExtensionPaymentStatus;
    private String extendStatus;
}
