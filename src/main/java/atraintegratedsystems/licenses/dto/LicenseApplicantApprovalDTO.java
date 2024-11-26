package atraintegratedsystems.licenses.dto;

import atraintegratedsystems.utils.DateConverter;
import atraintegratedsystems.utils.JalaliDate;
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
    private String financeType;
    private String companyLicenseName;
    private String licenseNo;
    private BigDecimal applicationFees;
    private int validity;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate entryVoucherDate;
    private String bankVoucher;
    private String paymentStatus;


    // LicenseApproval Model
    private Long appId;
    private String approvalId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate approvalDate;
    private String approvalStatus;
    private String boardDecisions;
    private String boardDecisionNumber;
    private Long licenseTypeId; // Reference to LicenseType by ID
    private String currencyType;
    private BigDecimal licenseFees;
    private String licensePaymentOffice;
    private BigDecimal administrativeYearlyFees;
    private String adminstrivateYearlyFeesPaymentOffice;
    private String guaranteeFeesType;
    private BigDecimal guaranteeFees;
    private String guaranteeFeesPaymentOffice;
    private BigDecimal databaseYearlyMaintainanceFees;
    private String databaseYearlyMaintainanceFeesPaymentOffice;
    private Long licenseApplicantId;


    public LicenseApplicantApprovalDTO()
    {

    }

    // Constructor matching the query's selected fields
    public LicenseApplicantApprovalDTO(Long id, String reqId, LocalDate reqDate, Long licenseTypeId, String licenseTypeName, String currencyType,
                                       String financeType, String companyLicenseName, String licenseNo,
                                       BigDecimal applicationFees, int validity, LocalDate entryVoucherDate,
                                       String bankVoucher, String paymentStatus, String approvalStatus) {
        this.id = id;
        this.reqId = reqId;
        this.reqDate = reqDate;
        this.licenseTypeId=licenseTypeId;
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

    public JalaliDate getRegDate() {
        if (reqDate == null) {
            return null; // Return null if issueLicenseDate is null
        }
        DateConverter dateConverter= new DateConverter();
        JalaliDate jalaliRequestDate=dateConverter.gregorianToJalali(reqDate.getYear(),reqDate.getMonthValue(),reqDate.getDayOfMonth());
        return jalaliRequestDate;
    }

    public JalaliDate getEntVocherDate(){
        if (entryVoucherDate == null) {
            return null; // Return null if issueLicenseDate is null
        }
        DateConverter dateConverter= new DateConverter();
        JalaliDate jalaliEntryVoucherDate= dateConverter.gregorianToJalali(entryVoucherDate.getYear(),entryVoucherDate.getMonthValue(),entryVoucherDate.getDayOfMonth()) ;
        return jalaliEntryVoucherDate;
    }




}

