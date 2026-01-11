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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate royaltyFeeExtentionExpirationDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate royaltyFeeExtensionEnteryVoucherDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate royaltyFeeExtensionBankVoucherSubmissionDate;
    private String royaltyFeeExtensionPaymentStatus;

    private String extendStatus;
    private String extendDate;
}
