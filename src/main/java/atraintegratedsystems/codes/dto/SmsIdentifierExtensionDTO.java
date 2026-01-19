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
public class SmsIdentifierExtensionDTO {
    private Long id;

    // Standard Department
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extensionStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extentionExpirationDate;
    @Column(name="extend_status")
    private String extendStatus;
    @Column(name="extend_Date")
    private String extendDate;

    // For Finance Departpemtn
    // Bellow is Application Fee Extension Fields
    private double extendedFees;
    private String extensionBankVoucherNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extensionEnteryVoucherDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extensionBankVoucherSubmissionDate;
    private String extensionPaymentStatus;

}
