package atraintegratedsystems.codes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmsIdentifierExtensionViewDTO {

    private Long detailId;
    private String smsIdentifierCodeName;
    private String serialNumber;
    private String companyName;
    private String companyNameInDari;
    private String enid;
    private String companyAddress;
    private String responsiblePerson;
    private String job;
    private String mobile;
    private String telephone;
    private String email;
    private String channel;
    private String serviceType;
    private String mnosCompanyHost;
    private String codeCategory;

    private LocalDate assigningDate;
    private String assigningDateJalali;

    private LocalDate expirationDate;
    private String expirationDateJalali;

    private double applicationFees;
    private String applicationFeesBankVoucherNo;
    private LocalDate applicationFeesEnteryVoucherDate;
    private String applicationFeesEnteryVoucherDateJalali;
    private LocalDate applicationFeesBankVoucherSubmissionDate;
    private String applicationFeesBankVoucherSubmissionDateJalali;
    private String applicationFeesPaymentStatus;

    private double royaltyFees;
    private String royaltyFeesBankVoucherNo;
    private LocalDate royaltyFeesEnteryVoucherDate;
    private String royaltyFeesEnteryVoucherDateJalali;
    private LocalDate royaltyFeesBankVoucherSubmissionDate;
    private String royaltyFeesBankVoucherSubmissionDateJalali;
    private String royaltyFeesPaymentStatus;

    private LocalDate extensionStartDate;
    private String extensionStartDateJalali;
    private LocalDate extentionExpirationDate;
    private String extentionExpirationDateJalali;
    private String extendStatus;
    private String extendDate;

    private double extendedFees=4000;
    private String extensionBankVoucherNo;
    private LocalDate extensionEnteryVoucherDate;
    private String extensionEnteryVoucherDateJalali;
    private LocalDate extensionBankVoucherSubmissionDate;
    private String extensionBankVoucherSubmissionDateJalali;
    private String extensionPaymentStatus;
    private LocalDate extensionEnteryDate;
    private String extensionBy;
}