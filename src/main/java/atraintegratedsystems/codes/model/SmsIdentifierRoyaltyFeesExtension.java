package atraintegratedsystems.codes.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class SmsIdentifierRoyaltyFeesExtension {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @ManyToOne
    @JoinColumn(name = "smsidentifier_detail_id")
    private SmsIdentifierDetail smsIdentifierDetail;
}
