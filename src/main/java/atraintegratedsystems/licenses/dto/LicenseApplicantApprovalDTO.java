package atraintegratedsystems.licenses.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LicenseApplicantApprovalDTO {

    private Long id;
    private String reqId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate reqDate;
    private String licenseTypeName; // Foreign key reference
    private String currencyType;
    private String financeType;
    private String companyLicenseName;
    private String licenseNo;
    private BigDecimal applicationFees;
    private int validity;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate entryVoucherDate;
    private String bankVoucher;
    private String paymentStatus;
    private String approvalStatus;

    // Constructor matching the query's selected fields
    public LicenseApplicantApprovalDTO(Long id, String reqId, LocalDate reqDate, String licenseTypeName, String currencyType,
                                       String financeType, String companyLicenseName, String licenseNo,
                                       BigDecimal applicationFees, int validity, LocalDate entryVoucherDate,
                                       String bankVoucher, String paymentStatus, String approvalStatus) {
        this.id = id;
        this.reqId = reqId;
        this.reqDate = reqDate;
        this.licenseTypeName = licenseTypeName;
        this.currencyType = currencyType;
        this.financeType = financeType;
        this.companyLicenseName = companyLicenseName;
        this.licenseNo = licenseNo;
        this.applicationFees = applicationFees;
        this.validity = validity;
        this.entryVoucherDate = entryVoucherDate;
        this.bankVoucher = bankVoucher;
        this.paymentStatus = paymentStatus;
        this.approvalStatus = approvalStatus;
    }
}

