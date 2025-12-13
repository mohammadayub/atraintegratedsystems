package atraintegratedsystems.licenses.dto;
import atraintegratedsystems.utils.JalaliDate;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class LicenseDatabaseFeesExtensionDTO {
    private Long id;
    private String licenseAppId;
    private String licenseCompanyName;
    private String licenseTypeName;
    private Long licenseApprovalId;

    private String licenseApprovalDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extensionStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extensionExpireDate;
    //    Add bank voucher date,submission,Fees
    private BigDecimal extensionDatabaseFees;
    private String extensionDatabaseFeeBankVoucherNo;
    private String extensionDatabaseFeeBankVoucherJalaliDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extensionDatabaseFeeBankVoucherDate;
    private String extensionDatabaseFeeBankVoucehrSubmissionJalaliDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extensionDatabaseFeeBankVoucherSubmissionDate;
    //for Extension Section Show Jalali Date
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private JalaliDate extStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private JalaliDate extExpireDate;
    private String extensionDatabasePaymentStatus;
    //Status
    private String extendStatus;
    //Audit for Database
    private String extendStatusCreatedBy;
    private LocalDateTime extendStatusCreatedDate;
    private String extensionDatabasePaymentStatusCreatedBy;
    private LocalDateTime extensionDatabasePaymentStatusCreatedDate;
}
