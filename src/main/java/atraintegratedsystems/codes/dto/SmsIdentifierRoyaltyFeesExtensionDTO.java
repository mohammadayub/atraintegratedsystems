package atraintegratedsystems.codes.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.time.LocalDate;

@Getter
@Setter
@Data

public class SmsIdentifierRoyaltyFeesExtensionDTO {
    private Long id;

    // For Standard Department
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate royaltyFeeExtensionStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate royaltyFeeExtentionExpirationDate;
    @Column(name="extend_status")
    private String extendStatus;
    @Column(name="extend_Date")
    private String extendDate;

    // For Finance Department
    private double royaltyFeeExtendedFees;
    private String royaltyFeeExtensionBankVoucherNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate royaltyFeeExtensionEnteryVoucherDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate royaltyFeeExtensionBankVoucherSubmissionDate;
    private String royaltyFeeExtensionPaymentStatus;
}
