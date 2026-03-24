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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extendedFeeExtensionDate;
    private String extendedFeeExtensionDateJalali;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extendedFeeExtensionExpirationDate;
    private String extendedFeeExtensionExpirationDateJalali;

    private double extendedFees;
    private String extendedFeeExtensionBankVoucherNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extendedFeeExtensionEntryVoucherDate;
    private String extendedFeeExtensionEntryVoucherDateJalali;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extendedFeeExtensionBankVoucherSubmissionDate;
    private String extendedFeeExtensionBankVoucherSubmissionDateJalali;

    private String extendedFeeExtensionPaymentStatus;

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
            LocalDate extendedFeeExtensionDate,
            LocalDate extendedFeeExtensionExpirationDate,
            double extendedFees,
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
        this.extendedFeeExtensionDate = extendedFeeExtensionDate;
        this.extendedFeeExtensionExpirationDate = extendedFeeExtensionExpirationDate;
        this.extendedFees = extendedFees;
        this.extendedFeeExtensionBankVoucherNo = extendedFeeExtensionBankVoucherNo;
        this.extendedFeeExtensionEntryVoucherDate = extendedFeeExtensionEntryVoucherDate;
        this.extendedFeeExtensionBankVoucherSubmissionDate = extendedFeeExtensionBankVoucherSubmissionDate;
        this.extendedFeeExtensionPaymentStatus = extendedFeeExtensionPaymentStatus;
    }
}