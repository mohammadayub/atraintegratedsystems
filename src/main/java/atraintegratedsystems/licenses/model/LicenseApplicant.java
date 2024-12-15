package atraintegratedsystems.licenses.model;
import atraintegratedsystems.utils.DateConverter;
import atraintegratedsystems.utils.JalaliDate;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
@Entity
@Table(name = "license_applicants")
@Data
public class LicenseApplicant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "req_id", nullable = false)
    private String reqId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "req_date", nullable = false)
    private LocalDate reqDate;
    @ManyToOne
    @JoinColumn(name = "license_type_id")
    private LicenseType licenseType; // Link to LicenseType entity


    private String currencyType;

    private String financeType;

    @Column(name = "company_license_name")
    private String companyLicenseName;

    @Column(name = "license_no")
    private String licenseNo;

    @Lob
    @Column(name = "license_upload" , nullable = true)
    private byte[] licenseUpload;

    @Column(name = "tin_no")
    private String tinNo;

    @Lob
    @Column(name = "tin_upload", nullable = true)
    private byte[] tinUpload;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "year_of_establishment")
    private LocalDate yearOfEstablishment;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "application_fees")
    private BigDecimal applicationFees;

    @Column(name="validity", nullable = true)
    private Integer validity;

    @Column(name = "planned_activities_services")
    private String plannedActivitiesAndServices;

    @Column(name = "total_national_employees")
    private Integer totalNationalEmployees;

    @Column(name = "total_international_employees")
    private Integer totalInternationalEmployees;

    @Column(name = "expected_investment")
    private Double expectedInvestment;

    @Column(name = "cash")
    private Double cash;

    @Lob
    @Column(name = "bank_statement_upload" , nullable = true)
    private byte[] bankStatementUpload;

    @Column(name = "other_license_taken")
    private String otherLicenseTaken;

    @Column(name = "company_address")
    private String companyAddress;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "email")
    private String email;

    @Column(name = "website")
    private String website;

    @Column(name = "post_address")
    private String postAddress;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="entry_voucher_date" , nullable = true)
    private LocalDate entryApplicationFeeVoucherDate;
    @Column(name="bank_voucher")
    private String bankVoucher;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="application_fee_bank_voucher_submission_date")
    private LocalDate applicationFeeBankVoucherSubmissionDate;
    @Column(name="payment_status")
    private String paymentStatus;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="application_fee_expiry_date")
    private LocalDate applicationFeeExpiryDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="refer_to_board_date",nullable = true)
    private LocalDate referToBoardDate;
    @Column(name="is_send")
    private String isSend;


    public JalaliDate getRegDate() {
        if (reqDate == null) {
            return null; // Return null if issueLicenseDate is null
        }
        DateConverter dateConverter= new DateConverter();
        JalaliDate jalaliRequestDate=dateConverter.gregorianToJalali(reqDate.getYear(),reqDate.getMonthValue(),reqDate.getDayOfMonth());
        return jalaliRequestDate;
    }

    public JalaliDate getEstablishmentYear() {
        if (yearOfEstablishment == null) {
            return null;
        }
        DateConverter dateConverter= new DateConverter();
        JalaliDate jalaliYearofEstablishment=dateConverter.gregorianToJalali(yearOfEstablishment.getYear(),yearOfEstablishment.getMonthValue(),yearOfEstablishment.getDayOfMonth());
        return jalaliYearofEstablishment;
    }



    public JalaliDate getExpDate(){
        if (expiryDate == null) {
            return null; // Return null if issueLicenseDate is null
        }
        DateConverter dateConverter= new DateConverter();
        JalaliDate jalaliExpiryDate= dateConverter.gregorianToJalali(expiryDate.getYear(),expiryDate.getMonthValue(),expiryDate.getDayOfMonth()) ;
        return jalaliExpiryDate;
    }


    public JalaliDate getEntVocherDate(){
        if (entryApplicationFeeVoucherDate == null) {
            return null; // Return null if issueLicenseDate is null
        }
        DateConverter dateConverter= new DateConverter();
        JalaliDate jalaliEntryVoucherDate= dateConverter.gregorianToJalali(entryApplicationFeeVoucherDate.getYear(),entryApplicationFeeVoucherDate.getMonthValue(),entryApplicationFeeVoucherDate.getDayOfMonth()) ;
        return jalaliEntryVoucherDate;
    }


    public JalaliDate getRefToBoardDate(){
        if (referToBoardDate == null) {
            return null; // Return null if issueLicenseDate is null
        }
        DateConverter dateConverter= new DateConverter();
        JalaliDate jalalireferToBoardDate= dateConverter.gregorianToJalali(referToBoardDate.getYear(),referToBoardDate.getMonthValue(),referToBoardDate.getDayOfMonth()) ;
        return jalalireferToBoardDate;
    }


    public JalaliDate getApplicationFeeExpiryDate(){
        if (applicationFeeExpiryDate == null) {
            return null; // Return null if issueLicenseDate is null
        }
        DateConverter dateConverter= new DateConverter();
        JalaliDate jalaliApplicationFeeExpiry= dateConverter.gregorianToJalali(applicationFeeExpiryDate.getYear(),applicationFeeExpiryDate.getMonthValue(),applicationFeeExpiryDate.getDayOfMonth()) ;
        return jalaliApplicationFeeExpiry;
    }

//  Submission Section
public JalaliDate getapplicationFeeBankVoucherSubmissionDate(){
    if (applicationFeeBankVoucherSubmissionDate == null) {
        return null; // Return null if issueLicenseDate is null
    }
    DateConverter dateConverter= new DateConverter();
    JalaliDate jalaliapplicationFeeBankVoucherSubmissionDate= dateConverter.gregorianToJalali(applicationFeeBankVoucherSubmissionDate.getYear(),applicationFeeBankVoucherSubmissionDate.getMonthValue(),applicationFeeBankVoucherSubmissionDate.getDayOfMonth()) ;
    return jalaliapplicationFeeBankVoucherSubmissionDate;
}






}

