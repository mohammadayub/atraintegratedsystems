package atraintegratedsystems.codes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class IspcExtensionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Standard Department
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extensionStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extentionExpirationDate;

    @Column(name="extend_status")
    private String extendStatus;


    // For Finance Departpemtn
    // Bellow is Application Fee Extension Fields
    private double extendedFees;
    private String extensionBankVoucherNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extensionEnteryVoucherDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extensionBankVoucherSubmissionDate;
    private String extensionPaymentStatus;


    private String extensionBy;

    @ManyToOne
    @JoinColumn(name = "ispc_detail_id")
    private IspcDetail ispcDetail;

}

