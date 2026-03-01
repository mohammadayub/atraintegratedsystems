package atraintegratedsystems.codes.model;

import atraintegratedsystems.licenses.model.LicenseApplicant;
import atraintegratedsystems.utils.DateConverter;
import atraintegratedsystems.utils.JalaliDate;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class ShortCodeDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "code_detail_id")
    private Long id;
    private String serialNumber;
    private Integer releaseShortCode;
    private String codeStatus;
    private String uniqueNameOfSignalingPoint;
    @ManyToOne
    @JoinColumn(name = "license_applicant_id")
    private LicenseApplicant licenseApplicant;
    private String sourceUsed;
    private String sourceUsedInDari;
    private String location;
    private String chanel;
    private String services;
    private String categoryType;
    private String category;
    private String backLongNumber;
    private String nameOfResponsiblePerson;
    private String idCardNumberOfResponsiblePerson;
    private String mobileNumberOfResponsiblePerson;
    private String phoneNumberOfResponsiblePerson;
    private String emailOfResponsiblePerson;
    private String job;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate assigningDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;


    // Short Code Rejection Section
    private String shortCodeRejectionStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate shortCodeRejectionDate;


    // Finance Fees Section
    private double applicationFees;
    private String applicationFeesOrganization = "ATRA";
    private String applicationFeesStatus;
    private double registrationFees;

    // Royalty Fees
    private double royaltyFees;
    private String royaltyFeesOrganization = "MCIT";
    private String royaltyFeesStatus;
    private String royaltyFeebankVoucherNo;
    private LocalDate royaltyFeeEnterVoucherDate;
    private LocalDate royaltyFeeBankVoucherSubmissionDate;
    private double total;

    //Application fees
    private String applicationFeebankVoucherNo;
    private LocalDate applicationFeeEnterVoucherDate;
    private LocalDate applicationFeebankVoucherSubmissionDate;
    private String paymentStatus;


    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "shortcode_id", nullable = false)
    private ShortCode shortCode;
























}
