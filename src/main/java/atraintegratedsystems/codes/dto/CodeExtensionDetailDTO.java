package atraintegratedsystems.codes.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
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
    private String applicationFeeExtensionEnterVoucherDate;
    private String applicationFeeExtensionBankVoucherSubmissionDate;

    // Bellow is Royalty Fee Extension Fields
    private double royaltyFeeExtendedFees;
    private String royaltyFeeExtensionBankVoucherNo;
    private String royaltyFeeExtensionDate;
    private String royaltyFeeExtentionExpirationDate;
    private String royaltyFeeExtensionEnterVoucherDate;
    private String royaltyFeeExtensionBankVoucherSubmissionDate;

    private String applicationFeeExtendedStatus;
    private String royaltyFeeExtendedStatus;
}
