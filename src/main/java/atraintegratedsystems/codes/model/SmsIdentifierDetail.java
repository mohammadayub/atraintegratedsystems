package atraintegratedsystems.codes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class SmsIdentifierDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String companyName;
    private String eid;
    private String companyAddress;
    private String mobile;
    private String telephone;
    private String email;
    private String channel;
    private String serviceType;
    private String MNOsCompanyHost;
    private String codeCategory="Golden";
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate assigningDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;

    // Fees Related Section
    // Application Fees Related
    private double applicationFees;
    private String applicationFeesBankVoucherNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeesEnteryVoucherDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeesBankVoucherSubmissionDate;
    private String applicationFeesPaymentStatus;

    // Royalty Fees Related
    private double royaltyFees;
    private String royaltyFeesBankVoucherNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate royaltyFeesEnteryVoucherDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate royaltyFeesBankVoucherSubmissionDate;
    private String royaltyFeesPaymentStatus;


    // Short Code Rejection Section
    private String shortCodeRejectionStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate shortCodeRejectionDate;

    // RelationShips

    // One-to-One relationship (OWNING SIDE)
    @OneToOne
    @JoinColumn(
            name = "sms_identifier_code_id",
            nullable = false,
            unique = true
    )
    @JsonIgnore
    private SmsIdentifierCode smsIdentifierCode;




}
