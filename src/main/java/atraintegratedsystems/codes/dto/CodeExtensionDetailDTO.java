package atraintegratedsystems.codes.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Setter
@Getter
public class CodeExtensionDetailDTO {

    private Long id;
    private Long codeDetailId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extendingDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeeExtensionDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeeExtentionExpirationDate;

    // Bellow is Application Fee Extension Fields
    private double applicationFeeExtendedFees;
    private String applicationFeeExtensionBankVoucherNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeeExtensionEnterVoucherDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeeExtensionBankVoucherSubmissionDate;
    private String applicationFeeExtensionPaymentStatus;

    // Bellow is Royalty Fee Extension Fields
    private double royaltyFeeExtendedFees;
    private String royaltyFeeExtensionBankVoucherNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate royaltyFeeExtensionDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate royaltyFeeExtentionExpirationDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate royaltyFeeExtensionEnterVoucherDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate royaltyFeeExtensionBankVoucherSubmissionDate;
    private String royaltyFeeExtensionPaymentStatus;

    private String applicationFeeExtendedStatus;
    private String royaltyFeeExtendedStatus;
}
