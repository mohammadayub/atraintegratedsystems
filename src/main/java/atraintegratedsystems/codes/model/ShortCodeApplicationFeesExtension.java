package atraintegratedsystems.codes.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class ShortCodeApplicationFeesExtension {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // Standard Department
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeeExtensionDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeeExtensionExpirationDate;
    @Column(name="extend_status")
    private String extendStatus;
    @Column(name="extend_Date")
    private String extendEntryDate;

    // For Finance Departpemtn
    // Bellow is Application Fee Extension Fields
    private double applicationFeeExtendedFees;
    private String applicationFeeExtensionBankVoucherNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeeExtensionEntryVoucherDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeeExtensionBankVoucherSubmissionDate;
    private String applicationFeeExtensionPaymentStatus;



    @ManyToOne
    @JoinColumn(name = "code_detail_id")
    private ShortCodeDetail shortCodeDetail;

}
