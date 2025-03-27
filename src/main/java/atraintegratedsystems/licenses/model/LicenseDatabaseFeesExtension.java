package atraintegratedsystems.licenses.model;

import atraintegratedsystems.utils.DateConverter;
import atraintegratedsystems.utils.JalaliDate;
import lombok.Data;
import org.hibernate.annotations.Columns;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "license_database_fees_extension")
@Data
public class LicenseDatabaseFeesExtension {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "license_approval_id", nullable = false)
    private LicenseApproval licenseApproval;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="extension_start_date")
    private LocalDate extensionStartDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="extension_expire_date")
    private LocalDate extensionExpireDate;

    // Add bank voucher date,submission,Fees
    @Column(name="extension_Database_fees")
    private BigDecimal extensionDatabaseFees;
    @Column(name="extension_Database_fee_bank_voucher_no")
    private String extensionDatabaseFeeBankVoucherNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="extension_Database_fee_bank_voucher_date")
    private LocalDate extensionDatabaseFeeBankVoucherDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="extension_Database_fee_bank_voucher_submission_date")
    private LocalDate extensionDatabaseFeeBankVoucherSubmissionDate;
    private String extensionDatabasePaymentStatus;


    //Status
    @Column(name="extend_status")
    private String extendStatus;


    //Audit for Database
    @Column(name="extend_status_created_by")
    private String extendStatusCreatedBy;
    @Column(name="extend_status_created_date")
    private LocalDateTime extendStatusCreatedDate;
    @Column(name="extension_database_payment_status_created_by")
    private String extensionDatabasePaymentStatusCreatedBy;
    @Column(name="extension_database_payment_status_created_date")
    private LocalDateTime extensionDatabasePaymentStatusCreatedDate;

    public JalaliDate getExtentStartDate() {
        if (extensionStartDate == null) {
            return null; // Return null if issueLicenseDate is null
        }
        DateConverter dateConverter= new DateConverter();
        JalaliDate jalaliExtensionStartDate=dateConverter.gregorianToJalali(extensionStartDate.getYear(),extensionStartDate.getMonthValue(),extensionStartDate.getDayOfMonth());
        return jalaliExtensionStartDate;
    }

    public JalaliDate getExtentExpDate() {
        if (extensionExpireDate == null) {
            return null; // Return null if issueLicenseDate is null
        }
        DateConverter dateConverter= new DateConverter();
        JalaliDate jalaliExtensionExpireDate=dateConverter.gregorianToJalali(extensionExpireDate.getYear(),extensionExpireDate.getMonthValue(),extensionExpireDate.getDayOfMonth());
        return jalaliExtensionExpireDate;
    }
    //    Extension Bank Voucher Date
    public JalaliDate getextensionAdministrationFeeBankVoucherDate() {
        if (extensionDatabaseFeeBankVoucherDate == null) {
            return null; // Return null if issueLicenseDate is null
        }
        DateConverter dateConverter= new DateConverter();
        JalaliDate jalaliextensionDatabaseFeeBankVoucherDate=dateConverter.gregorianToJalali(extensionDatabaseFeeBankVoucherDate.getYear(),extensionDatabaseFeeBankVoucherDate.getMonthValue(),extensionDatabaseFeeBankVoucherDate.getDayOfMonth());
        return jalaliextensionDatabaseFeeBankVoucherDate;
    }

    //    Extension Bank Voucher Submission
    public JalaliDate getextensionDatabaseFeeBankVoucherSubmissionDate() {
        if (extensionDatabaseFeeBankVoucherSubmissionDate == null) {
            return null; // Return null if issueLicenseDate is null
        }
        DateConverter dateConverter= new DateConverter();
        JalaliDate jalaliextensionDatabaseFeeBankVoucherSubmissionDate=dateConverter.gregorianToJalali(extensionDatabaseFeeBankVoucherSubmissionDate.getYear(),extensionDatabaseFeeBankVoucherSubmissionDate.getMonthValue(),extensionDatabaseFeeBankVoucherSubmissionDate.getDayOfMonth());
        return jalaliextensionDatabaseFeeBankVoucherSubmissionDate;
    }

}
