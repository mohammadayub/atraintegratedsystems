package atraintegratedsystems.codes.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class SmsIdentifierApplicationFeesExtension {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @ManyToOne
    @JoinColumn(name = "smsidentifier_detail_id")
    private SmsIdentifierDetail smsIdentifierDetail;
}
