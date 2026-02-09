package atraintegratedsystems.codes.dto;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SmsIdentifierDetailDTO {
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
    private String smsIdentifierCodeName;

    private Long smsIdentifierSerialNumberId;
    private int serialNumber;

    public SmsIdentifierDetailDTO(
            long id,
            String companyName,
            String enid,
            String companyAddress,
            String mobile,
            String telephone,
            String email,
            String channel,
            String serviceType,
            String mnosCompanyHost,
            String codeCategory,
            LocalDate assigningDate,
            LocalDate expirationDate,
            double applicationFees,
            String applicationFeesBankVoucherNo,
            LocalDate applicationFeesEnteryVoucherDate,
            LocalDate applicationFeesBankVoucherSubmissionDate,
            String applicationFeesPaymentStatus,
            double royaltyFees,
            String royaltyFeesBankVoucherNo,
            LocalDate royaltyFeesEnteryVoucherDate,
            LocalDate royaltyFeesBankVoucherSubmissionDate,
            String royaltyFeesPaymentStatus,
            String shortCodeRejectionStatus,
            LocalDate shortCodeRejectionDate,
            long smsIdentifierCodeId,
            String smsIdentifierCodeName
    ) {
        this.id = id;
        this.companyName = companyName;
        this.enid = enid;
        this.companyAddress = companyAddress;
        this.mobile = mobile;
        this.telephone = telephone;
        this.email = email;
        this.channel = channel;
        this.serviceType = serviceType;
        this.mnosCompanyHost = mnosCompanyHost;
        this.codeCategory = codeCategory;
        this.assigningDate = assigningDate;
        this.expirationDate = expirationDate;
        this.applicationFees = applicationFees;
        this.applicationFeesBankVoucherNo = applicationFeesBankVoucherNo;
        this.applicationFeesEnteryVoucherDate = applicationFeesEnteryVoucherDate;
        this.applicationFeesBankVoucherSubmissionDate = applicationFeesBankVoucherSubmissionDate;
        this.applicationFeesPaymentStatus = applicationFeesPaymentStatus;
        this.royaltyFees = royaltyFees;
        this.royaltyFeesBankVoucherNo = royaltyFeesBankVoucherNo;
        this.royaltyFeesEnteryVoucherDate = royaltyFeesEnteryVoucherDate;
        this.royaltyFeesBankVoucherSubmissionDate = royaltyFeesBankVoucherSubmissionDate;
        this.royaltyFeesPaymentStatus = royaltyFeesPaymentStatus;
        this.shortCodeRejectionStatus = shortCodeRejectionStatus;
        this.shortCodeRejectionDate = shortCodeRejectionDate;
        this.smsIdentifierCodeId = smsIdentifierCodeId;
        this.smsIdentifierCodeName = smsIdentifierCodeName;
    }

}