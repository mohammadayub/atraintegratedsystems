package atraintegratedsystems.codes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class SmsIdentifierDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String companyName;
    private String enid;
    private String companyAddress;
    private String mobile;
    private String telephone;
    private String email;
    private String channel;
    private String serviceType;
    private String mnosCompanyHost;
    private String codeCategory="Golden";
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate assigningDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;

    // Fees Related Section
    // Application Fees Related
    private double applicationFees=5000;
    private String applicationFeesBankVoucherNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeesEnteryVoucherDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeesBankVoucherSubmissionDate;
    private String applicationFeesPaymentStatus;

    // Royalty Fees Related
    private double royaltyFees=70000;
    private String royaltyFeesBankVoucherNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate royaltyFeesEnteryVoucherDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate royaltyFeesBankVoucherSubmissionDate;
    private String royaltyFeesPaymentStatus;


    // smsIdentifier Code Rejection Section
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

    @OneToMany(
            mappedBy = "smsIdentifierDetail",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<SmsIdentifierExtension> extensions = new ArrayList<>();




    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "smsidentifier_serial_number_id",
            unique = true
    )
    private SmsIdentifierSerialNumber smsIdentifierSerialNumber;


}
