package atraintegratedsystems.licenses.model;

import atraintegratedsystems.utils.DateConverter;
import atraintegratedsystems.utils.JalaliDate;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "license_approvals")
@Data
public class LicenseApproval {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "approval_id", nullable = false)
    private String approvalId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "approval_date", nullable = false)
    private LocalDate approvalDate;
    @Column(name="approval_status")
    private String approvalStatus;
    @Column(name="board_decisions")
    private String boardDecisions;
    private String boardDecisionNumber;
    @ManyToOne
    @JoinColumn(name = "license_type_id")
    private LicenseType licenseType; // Link to LicenseType entity
    private String currencyType;
    @Column(name="license_fees")
    private BigDecimal licenseFees;
    @Column(name="license_payment_office")
    private String licensePaymentOffice;
    @Column(name="adminstrative_yearly_fees")
    private BigDecimal administrativeYearlyFees;
    @Column(name="administrative_yearly_fees_payment_office")
    private String adminstrivateYearlyFeesPaymentOffice;
    @Column(name="guarantee_fees_type")
    private String guaranteeFeesType;
    @Column(name="guarantee_fees")
    private BigDecimal guaranteeFees;
    @Column(name="gurantee_fees_payment_office")
    private String guaranteeFeesPaymentOffice;
    @Column(name="database_yearly_maintainance_fees")
    private BigDecimal databaseYearlyMaintainanceFees;
    @Column(name="database_yearly_maintainance_fees_payment_office")
    private String databaseYearlyMaintainanceFeesPaymentOffice;
    @ManyToOne
    @JoinColumn(name = "license_applicant_id", nullable = false)
    private LicenseApplicant licenseApplicant;

    @Column(name="license_fee_mcit_entry_voucher_date")
    private LocalDate licenseFeeEntryVoucherDate;
    @Column(name="license_fee_mcit_bank_voucher_No")
    private String licenseFeeBankVoucherNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="license_fee_bank_voucher_submission_date")
    private LocalDate licenseFeeBankVoucherSubmissionDate;

    @Column(name="license_fee_mcit_payment_status")
    private String licenseFeePaymentStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="license_fee_mcit_expiry_date")
    private LocalDate licenseFeeExpiryDate;

    @Column(name="database_maintainance_fee_entry_voucher_date")
    private LocalDate databaseMaintianenceFeeEntryVoucherDate;
    @Column(name="database_maintainance_fee_bank_voucher_no")
    private String databaseMaintianenceFeeBankVoucherNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="database_maintainance_fee_bank_voucher_submission_date")
    private LocalDate databasemaintainanceFeeBankVoucherSubmissionDate;

    @Column(name="database_maintainance_fee_payment_status")
    private String databaseMaintianenceFeePaymentStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="database_maintainance_fee_expiry_date")
    private LocalDate databaseMaintianenceFeeExpiryDate;


    @Column(name="administration_fee_entry_voucher_date")
    private LocalDate administrationFeeEntryVoucherDate;
    @Column(name="administration_fee_bank_voucher_No")
    private String administrationFeeBankVoucherNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="administration_fee_bank_voucher_submission_date")
    private LocalDate administrationFeeBankVoucherSubmissionDate;

    @Column(name="administration_fee_payment_status")
    private String administrationFeePaymentStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="administration_fee_expiry_date")
    private LocalDate administrationFeeExpiryDate;

    @Column(name="guarantee_fee_entry_voucher_date")
    private LocalDate guaranteeFeeEntryVoucherDate;
    @Column(name="guarantee_fee_bank_voucher_No")
    private String guaranteeFeeBankVoucherNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="guarantee_fee_bank_voucher_submission_date")
    private LocalDate guaranteeFeeBankVoucherSubmissionDate;

    @Column(name="guarantee_fee_payment_status")
    private String guaranteeFeePaymentStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="guarantee_fee_expiry_date")
    private LocalDate guaranteeFeeExpiryDate;



    //Chart
    @Transient
    private String formattedApprovalDate; // For yyyy-MM-dd formatted dates
    @Transient
    private String formattedLicenseFeeExpiryDate; // For yyyy-MM-dd formatted dates


    //Audit for Approval
    @Column(name="approval_entered_by")
    private String approvalEnteredBy;
    @Column(name="approval_created_date")
    private LocalDateTime approvalCreatedDate;


    //Audit for Administration Fees
    @Column(name="administration_fees_entered_by")
    private String administrationFeesEnteredBy;
    @Column(name="administration_fees_created_date")
    private LocalDateTime administrationFeesCreatedDate;

    //Audit for Datebase Maintainance Fees

    @Column(name="database_maintainance_fees_entered_by")
    private String databaseMaintainanceFeesEnteredBy;
    @Column(name="database_maintainance_fees_created_date")
    private LocalDateTime databaseMaintainanceFeesCreatedDate;


    //Audit For Guaranteee Fees
    @Column(name="guarantee_fees_entered_by")
    private String guaranteeFeesEnteredBy;
    @Column(name="guarantee_fees_created_date")
    private LocalDateTime guaranteeFeesCreatedDate;

    //Audit for License Fees
    @Column(name="license_fees_entered_by")
    private String licenseFeesEnteredBy;
    @Column(name="license_fees_created_date")
    private LocalDateTime licenseFeesCreatedDate;



    public JalaliDate getlFeeExpiryDate() {
        if (licenseFeeExpiryDate == null) {
            return null; // Return null if issueLicenseDate is null
        }
        DateConverter dateConverter= new DateConverter();
        JalaliDate jalaliLicenseFeeExpiryDate=dateConverter.gregorianToJalali(licenseFeeExpiryDate.getYear(),licenseFeeExpiryDate.getMonthValue(),licenseFeeExpiryDate.getDayOfMonth());
        return jalaliLicenseFeeExpiryDate;
    }

//    Start My Code
    public JalaliDate getdatabaseFeesExpiryDate() {
        if (databaseMaintianenceFeeExpiryDate == null) {
            return null; // Return null if issueLicenseDate is null
        }
        DateConverter dateConverter= new DateConverter();
        JalaliDate jalalidatabaseMaintianenceFeeExpiryDate=dateConverter.gregorianToJalali(databaseMaintianenceFeeExpiryDate.getYear(),databaseMaintianenceFeeExpiryDate.getMonthValue(),databaseMaintianenceFeeExpiryDate.getDayOfMonth());
        return jalalidatabaseMaintianenceFeeExpiryDate;
    }


    public JalaliDate getadminFeeExpiryDate() {
        if (administrationFeeExpiryDate == null) {
            return null; // Return null if issueLicenseDate is null
        }
        DateConverter dateConverter= new DateConverter();
        JalaliDate jalaliadministrationFeeExpiryDate=dateConverter.gregorianToJalali(administrationFeeExpiryDate.getYear(),administrationFeeExpiryDate.getMonthValue(),administrationFeeExpiryDate.getDayOfMonth());
        return jalaliadministrationFeeExpiryDate;
    }

    public JalaliDate getguaranteeFeeExpiryDate() {
        if (guaranteeFeeExpiryDate == null) {
            return null; // Return null if issueLicenseDate is null
        }
        DateConverter dateConverter= new DateConverter();
        JalaliDate jalaliguaranteeFeeExpiryDate=dateConverter.gregorianToJalali(guaranteeFeeExpiryDate.getYear(),guaranteeFeeExpiryDate.getMonthValue(),guaranteeFeeExpiryDate.getDayOfMonth());
        return jalaliguaranteeFeeExpiryDate;
    }

//    End of My Code


    public JalaliDate getLicFeeEntryVoucherDate() {
        if (licenseFeeEntryVoucherDate == null) {
            return null; // Return null if issueLicenseDate is null
        }
        DateConverter dateConverter= new DateConverter();
        JalaliDate jalaliLicenseFeeEntryVoucherDate=dateConverter.gregorianToJalali(licenseFeeEntryVoucherDate.getYear(),licenseFeeEntryVoucherDate.getMonthValue(),licenseFeeEntryVoucherDate.getDayOfMonth());
        return jalaliLicenseFeeEntryVoucherDate;
    }



    public JalaliDate getAppDate() {
        if (approvalDate == null) {
            return null; // Return null if issueLicenseDate is null
        }
        DateConverter dateConverter= new DateConverter();
        JalaliDate jalaliApprovalDate=dateConverter.gregorianToJalali(approvalDate.getYear(),approvalDate.getMonthValue(),approvalDate.getDayOfMonth());
        return jalaliApprovalDate;
    }



//    Submission section
public JalaliDate getlicFeeBankVoucherSubmissionDate() {
    if (licenseFeeBankVoucherSubmissionDate == null) {
        return null; // Return null if issueLicenseDate is null
    }
    DateConverter dateConverter= new DateConverter();
    JalaliDate jalalilicenseFeeBankVoucherSubmissionDate=dateConverter.gregorianToJalali(licenseFeeBankVoucherSubmissionDate.getYear(),licenseFeeBankVoucherSubmissionDate.getMonthValue(),licenseFeeBankVoucherSubmissionDate.getDayOfMonth());
    return jalalilicenseFeeBankVoucherSubmissionDate;
}

    public JalaliDate getdbmaintainanceFeeBankVoucherSubmissionDate() {
        if (databasemaintainanceFeeBankVoucherSubmissionDate == null) {
            return null; // Return null if issueLicenseDate is null
        }
        DateConverter dateConverter= new DateConverter();
        JalaliDate jalalidatabasemaintainanceFeeBankVoucherSubmissionDate=dateConverter.gregorianToJalali(databasemaintainanceFeeBankVoucherSubmissionDate.getYear(),databasemaintainanceFeeBankVoucherSubmissionDate.getMonthValue(),databasemaintainanceFeeBankVoucherSubmissionDate.getDayOfMonth());
        return jalalidatabasemaintainanceFeeBankVoucherSubmissionDate;
    }

    public JalaliDate getguaranteFeeBankVoucherSubmissionDate() {
        if (guaranteeFeeBankVoucherSubmissionDate == null) {
            return null; // Return null if issueLicenseDate is null
        }
        DateConverter dateConverter= new DateConverter();
        JalaliDate jalaliguaranteeFeeBankVoucherSubmissionDate=dateConverter.gregorianToJalali(guaranteeFeeBankVoucherSubmissionDate.getYear(),guaranteeFeeBankVoucherSubmissionDate.getMonthValue(),guaranteeFeeBankVoucherSubmissionDate.getDayOfMonth());
        return jalaliguaranteeFeeBankVoucherSubmissionDate;
    }
    public JalaliDate getadminFeeBankVoucherSubmissionDate() {
        if (administrationFeeBankVoucherSubmissionDate == null) {
            return null; // Return null if issueLicenseDate is null
        }
        DateConverter dateConverter= new DateConverter();
        JalaliDate jalaliadministrationFeeBankVoucherSubmissionDate=dateConverter.gregorianToJalali(administrationFeeBankVoucherSubmissionDate.getYear(),administrationFeeBankVoucherSubmissionDate.getMonthValue(),administrationFeeBankVoucherSubmissionDate.getDayOfMonth());
        return jalaliadministrationFeeBankVoucherSubmissionDate;
    }

}
