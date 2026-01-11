package atraintegratedsystems.codes.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Getter
@Setter
public class SmsIdentifierDetailDTO {
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

    /* 🔥 IMPORTANT */
    private Long smsIdentifierCodeId;
}