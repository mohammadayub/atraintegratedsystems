package atraintegratedsystems.codes.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class ShortCodeDetailDTO {
    private Long id;
    private String serialNumber;
    private Integer releaseShortCode;
    private String codeStatus;
    private String uniqueNameOfSignalingPoint;
    private Long licenseApplicantId;
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
    private double applicationFees;

    // Short Code Rejection Section
    private String shortCodeRejectionStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate shortCodeRejectionDate;

    //Application fees
    private String applicationFeebankVoucherNo;
    private LocalDate applicationFeeEnterVoucherDate;
    private String applicationFeeEnterVoucherDateJalali;
    private LocalDate applicationFeebankVoucherSubmissionDate;
    private String applicationFeebankVoucherSubmissionDateJalali;

    private double registrationFees;

    // Royalty Fees
    private double royaltyFees;
    private String royaltyFeesStatus;
    private String royaltyFeebankVoucherNo;
    private String royaltyFeeEnterVoucherDate;
    private String royaltyFeeEnterVoucherDateJalali;
    private String royaltyFeeBankVoucherSubmissionDate;
    private String royaltyFeeBankVoucherSubmissionDateJalali;
    private double total;
    private String bankVoucherNo;
    private String paymentStatus;
    private Long serialNumberId;
    private Long shortCodeId;
    private Integer shortCodeName;
    private String remarks;





}
