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
    private LocalDate applicationFeeExtensionDate;
    private String applicationFeeExtensionDateJalali;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeeExtensionExpirationDate;
    private String applicationFeeExtensionExpirationDateJalali;

    private double applicationFeeExtendedFees;
    private String applicationFeeExtensionBankVoucherNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeeExtensionEntryVoucherDate;
    private String applicationFeeExtensionEntryVoucherDateJalali;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeeExtensionBankVoucherSubmissionDate;
    private String applicationFeeExtensionBankVoucherSubmissionDateJalali;

    private String applicationFeeExtensionPaymentStatus;

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
            LocalDate applicationFeeExtensionDate,
            LocalDate applicationFeeExtensionExpirationDate,
            double applicationFeeExtendedFees,
            String applicationFeeExtensionBankVoucherNo,
            LocalDate applicationFeeExtensionEntryVoucherDate,
            LocalDate applicationFeeExtensionBankVoucherSubmissionDate,
            String applicationFeeExtensionPaymentStatus
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
        this.applicationFeeExtensionDate = applicationFeeExtensionDate;
        this.applicationFeeExtensionExpirationDate = applicationFeeExtensionExpirationDate;
        this.applicationFeeExtendedFees = applicationFeeExtendedFees;
        this.applicationFeeExtensionBankVoucherNo = applicationFeeExtensionBankVoucherNo;
        this.applicationFeeExtensionEntryVoucherDate = applicationFeeExtensionEntryVoucherDate;
        this.applicationFeeExtensionBankVoucherSubmissionDate = applicationFeeExtensionBankVoucherSubmissionDate;
        this.applicationFeeExtensionPaymentStatus = applicationFeeExtensionPaymentStatus;
    }
}