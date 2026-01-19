package atraintegratedsystems.codes.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class SmsIdentifierExtension {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @ManyToOne
    @JoinColumn(name = "smsidentifier_detail_id")
    private SmsIdentifierDetail smsIdentifierDetail;
}
