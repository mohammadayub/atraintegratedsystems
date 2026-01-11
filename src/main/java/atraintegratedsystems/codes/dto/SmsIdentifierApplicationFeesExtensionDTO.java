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
public class SmsIdentifierApplicationFeesExtensionDTO {
    private Long id;

    // Standard Department
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeeExtensionStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeeExtentionExpirationDate;
    @Column(name="extend_status")
    private String extendStatus;
    @Column(name="extend_Date")
    private String extendDate;

    // For Finance Departpemtn
    // Bellow is Application Fee Extension Fields
    private double applicationFeeExtendedFees;
    private String applicationFeeExtensionBankVoucherNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeeExtensionEnteryVoucherDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeeExtensionBankVoucherSubmissionDate;
    private String applicationFeeExtensionPaymentStatus;

}
