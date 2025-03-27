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
@Table(name = "license_admin_fees_extension")
@Data
public class LicenseAdminFeesExtension {

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

//    Add bank voucher date,submission,Fees

    @Column(name="extension_Administration_fees")
    private BigDecimal extensionAdministrationFees;
    @Column(name="extension_Administration_fee_bank_voucher_no")
    private String extensionAdministrationFeeBankVoucherNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="extension_Administration_fee_bank_voucher_date")
    private LocalDate extensionAdministrationFeeBankVoucherDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="extension_Administration_fee_bank_voucher_submission_date")
    private LocalDate extensionAdministrationFeeBankVoucherSubmissionDate;
    @Column(name="extension_Administration_payment_status")
    private String extensionAdministrationPaymentStatus;


    //Status
    @Column(name="extend_status")
    private String extendStatus;

    //Audit for Admin
    @Column(name="extend_status_created_by")
    private String extendStatusCreatedBy;
    @Column(name="extend_status_created_date")
    private LocalDateTime extendStatusCreatedDate;
    @Column(name="extension_administration_payment_status_created_by")
    private String extensionAdministrationPaymentStatusCreatedBy;
    @Column(name="extension_administration_payment_status_created_date")
    private LocalDateTime extensionAdministrationPaymentStatusCreatedDate;


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
    if (extensionAdministrationFeeBankVoucherDate == null) {
        return null; // Return null if issueLicenseDate is null
    }
    DateConverter dateConverter= new DateConverter();
    JalaliDate jalaliextensionAdministrationFeeBankVoucherDate=dateConverter.gregorianToJalali(extensionAdministrationFeeBankVoucherDate.getYear(),extensionAdministrationFeeBankVoucherDate.getMonthValue(),extensionAdministrationFeeBankVoucherDate.getDayOfMonth());
    return jalaliextensionAdministrationFeeBankVoucherDate;
}

    //    Extension Bank Voucher Submission
    public JalaliDate getextensionAdministrationFeeBankVoucherSubmissionDate() {
        if (extensionAdministrationFeeBankVoucherSubmissionDate == null) {
            return null; // Return null if issueLicenseDate is null
        }
        DateConverter dateConverter= new DateConverter();
        JalaliDate jalaliextensionAdministrationFeeBankVoucherSubmissionDate=dateConverter.gregorianToJalali(extensionAdministrationFeeBankVoucherSubmissionDate.getYear(),extensionAdministrationFeeBankVoucherSubmissionDate.getMonthValue(),extensionAdministrationFeeBankVoucherSubmissionDate.getDayOfMonth());
        return jalaliextensionAdministrationFeeBankVoucherSubmissionDate;
    }


}
