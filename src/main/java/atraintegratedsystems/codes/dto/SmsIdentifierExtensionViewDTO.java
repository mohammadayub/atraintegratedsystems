package atraintegratedsystems.codes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsIdentifierExtensionViewDTO {

    private Long detailId;
    private String smsIdentifierCodeName;
    private String companyName;
    private String mobile;
    private String email;

    private LocalDate assigningDate;
    private LocalDate expirationDate;

    // Extension fields
    private LocalDate extensionStartDate;
    private LocalDate extentionExpirationDate;
    private Double extendedFees;
    private String extensionBankVoucherNo;
    private LocalDate extensionEnteryVoucherDate;
    private LocalDate extensionBankVoucherSubmissionDate;
    private String extensionPaymentStatus;
}