package atraintegratedsystems.licenses.dto;
import atraintegratedsystems.utils.JalaliDate;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class LicenseApprovalDTO {
    private Long id;
    private String approvalId;
    private String approvalDateJalali; // the raw input from form
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate approvalDate;
    private String approvalStatus;
    private String boardDecisions;
    private String boardDecisionNumber;
    private Long licenseTypeId; // Reference to LicenseType by ID
    private String licenseTypeName;
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
    private String applicantLicenseCompanyName;
    private int  licenseValidity;

    private String licenseFeeEntryVoucherDateJalaliDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate licenseFeeEntryVoucherDate;
    private String licenseFeeBankVoucherNo;
    private String licenseFeeBankVoucherSubmissionDateJalaliDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate licenseFeeBankVoucherSubmissionDate;
    private String licenseFeePaymentStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate licenseFeeExpiryDate;

    private String databaseMaintianenceFeeEntryVoucherDateJalaliDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate databaseMaintianenceFeeEntryVoucherDate;
    private String databaseMaintianenceFeeBankVoucherNo;
    private String databasemaintainanceFeeBankVoucherSubmissionDateJalaliDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate databasemaintainanceFeeBankVoucherSubmissionDate;
    private String databaseMaintianenceFeePaymentStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate databaseMaintianenceFeeExpiryDate;

    private String administrationFeeEntryVoucherDateJalaliDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate administrationFeeEntryVoucherDate;
    private String administrationFeeBankVoucherNo;
    private String administrationFeeBankVoucherSubmissionDateJalaliDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate administrationFeeBankVoucherSubmissionDate;
    private String administrationFeePaymentStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate administrationFeeExpiryDate;

    private String guaranteeFeeEntryVoucherDateJalaliDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate guaranteeFeeEntryVoucherDate;
    private String guaranteeFeeBankVoucherNo;

    private String guaranteeFeeBankVoucherSubmissionDateJalaliDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate guaranteeFeeBankVoucherSubmissionDate;
    private String guaranteeFeePaymentStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate guaranteeFeeExpiryDate;


    //Chart
    private String formattedApprovalDate; // For yyyy-MM-dd formatted dates
    private String formattedLicenseFeeExpiryDate; // For yyyy-MM-dd formatted dates



    //for Extension Section Show Jalali Date
    @DateTimeFormat(pattern = "yyyy-MM-dd")
     private JalaliDate appDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
     private JalaliDate dbFeesExpireDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
     private JalaliDate adFeesExpireDate;


    //Certificate Need Columns

    private String companyAddress;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate yearOfEstablishment;
    private String contactNo;
    private String licenseNo;


    //Audit for Approval
    private String approvalEnteredBy;
    private LocalDateTime approvalCreatedDate;



    //Audit for Administration Fees
    private String administrationFeesEnteredBy;
    private LocalDateTime administrationFeesCreatedDate;

    //Audit for Datebase Maintainance Fees
    private String databaseMaintainanceFeesEnteredBy;
    private LocalDateTime databaseMaintainanceFeesCreatedDate;


    //Audit For Guaranteee Fees
    private String guaranteeFeesEnteredBy;
    private LocalDateTime guaranteeFeesCreatedDate;

    //Audit for License Fees
    private String licenseFeesEnteredBy;
    private LocalDateTime licenseFeesCreatedDate;





}
