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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeeExtentionExpirationDate;

    // Bellow is Application Fee Extension Fields
    private double applicationFeeExtendedFees;
    private String applicationFeeExtensionBankVoucherNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeeExtensionEnteryVoucherDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeeExtensionBankVoucherSubmissionDate;
    private String applicationFeeExtensionPaymentStatus;

    private String extendStatus;

    private String extendDate;
}
