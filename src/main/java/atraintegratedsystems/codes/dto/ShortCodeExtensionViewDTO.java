package atraintegratedsystems.codes.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class ShortCodeExtensionViewDTO {

    private Long codeDetailId;
    private Integer shortCodeName;
    private String codeStatus;
    private String sourceUsed;
    private String location;
    private String categoryType;
    private String category;
    private String chanel;
    private String emailOfResponsiblePerson;

    private String serialNumber;
    private Integer releaseShortCode;
    private String uniqueNameOfSignalingPoint;
    private Long licenseApplicantId;
    private String sourceUsedInDari;
    private String address;
    private String services;
    private String backLongNumber;
    private String nameOfResponsiblePerson;
    private String idCardNumberOfResponsiblePerson;
    private String mobileNumberOfResponsiblePerson;
    private String phoneNumberOfResponsiblePerson;
    private String job;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate assigningDate;
    private String assigningDateJalali;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;
    private String expirationDateJalali;

    private double applicationFees;
    private double royaltyFees;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extendedFeeExtensionDate;
    private String extendedFeeExtensionDateJalali;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extendedFeeExtensionExpirationDate;
    private String extendedFeeExtensionExpirationDateJalali;

    private Double extendedFees;
    private String extendedFeeExtensionBankVoucherNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extendedFeeExtensionEntryVoucherDate;
    private String extendedFeeExtensionEntryVoucherDateJalali;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extendedFeeExtensionBankVoucherSubmissionDate;
    private String extendedFeeExtensionBankVoucherSubmissionDateJalali;

    private String extendedFeeExtensionPaymentStatus;

    // ✅ FULL CONSTRUCTOR (IMPORTANT)
    public ShortCodeExtensionViewDTO(
            Long codeDetailId,
            Integer shortCodeName,
            String codeStatus,
            String sourceUsed,
            String location,
            String categoryType,
            String category,
            String chanel,
            String emailOfResponsiblePerson,

            String serialNumber,
            Integer releaseShortCode,
            String uniqueNameOfSignalingPoint,
            Long licenseApplicantId,
            String sourceUsedInDari,
            String address,
            String services,
            String backLongNumber,
            String nameOfResponsiblePerson,
            String idCardNumberOfResponsiblePerson,
            String mobileNumberOfResponsiblePerson,
            String phoneNumberOfResponsiblePerson,
            String job,
            LocalDate assigningDate,
            LocalDate expirationDate,
            double applicationFees,
            double royaltyFees,

            LocalDate extendedFeeExtensionDate,
            LocalDate extendedFeeExtensionExpirationDate,
            Double extendedFees,
            String extendedFeeExtensionBankVoucherNo,
            LocalDate extendedFeeExtensionEntryVoucherDate,
            LocalDate extendedFeeExtensionBankVoucherSubmissionDate,
            String extendedFeeExtensionPaymentStatus
    ) {
        this.codeDetailId = codeDetailId;
        this.shortCodeName = shortCodeName;
        this.codeStatus = codeStatus;
        this.sourceUsed = sourceUsed;
        this.location = location;
        this.categoryType = categoryType;
        this.category = category;
        this.chanel = chanel;
        this.emailOfResponsiblePerson = emailOfResponsiblePerson;

        this.serialNumber = serialNumber;
        this.releaseShortCode = releaseShortCode;
        this.uniqueNameOfSignalingPoint = uniqueNameOfSignalingPoint;
        this.licenseApplicantId = licenseApplicantId;
        this.sourceUsedInDari = sourceUsedInDari;
        this.address = address;
        this.services = services;
        this.backLongNumber = backLongNumber;
        this.nameOfResponsiblePerson = nameOfResponsiblePerson;
        this.idCardNumberOfResponsiblePerson = idCardNumberOfResponsiblePerson;
        this.mobileNumberOfResponsiblePerson = mobileNumberOfResponsiblePerson;
        this.phoneNumberOfResponsiblePerson = phoneNumberOfResponsiblePerson;
        this.job = job;
        this.assigningDate = assigningDate;
        this.expirationDate = expirationDate;
        this.applicationFees = applicationFees;
        this.royaltyFees = royaltyFees;

        this.extendedFeeExtensionDate = extendedFeeExtensionDate;
        this.extendedFeeExtensionExpirationDate = extendedFeeExtensionExpirationDate;
        this.extendedFees = extendedFees;
        this.extendedFeeExtensionBankVoucherNo = extendedFeeExtensionBankVoucherNo;
        this.extendedFeeExtensionEntryVoucherDate = extendedFeeExtensionEntryVoucherDate;
        this.extendedFeeExtensionBankVoucherSubmissionDate = extendedFeeExtensionBankVoucherSubmissionDate;
        this.extendedFeeExtensionPaymentStatus = extendedFeeExtensionPaymentStatus;
    }

    public ShortCodeExtensionViewDTO() {
    }
}