package atraintegratedsystems.codes.dto;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
public class IspcExtensionDetailDTO {

    private Long id;

    // Standard Department
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extensionStartDate;
    private String extensionStartDateJalali;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extentionExpirationDate;
    private String extentionExpirationDateJalali;
    @Column(name="extend_status")
    private String extendStatus;


    // For Finance Departpemtn
    // Bellow is Registration Fee Extension Fields
    private double extendedFees;
    private String extensionBankVoucherNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extensionEnteryVoucherDate;
    private String extensionEnteryVoucherDateJalali;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extensionBankVoucherSubmissionDate;
    private String extensionBankVoucherSubmissionDateJalali;
    private String extensionPaymentStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extensionEnteryDate;
    private String extensionEnteryDateJalali;
    private String extensionBy;

}