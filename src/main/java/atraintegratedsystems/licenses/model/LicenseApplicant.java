package atraintegratedsystems.licenses.model;
import atraintegratedsystems.utils.DateConverter;
import atraintegratedsystems.utils.JalaliDate;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
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
    @Column(name = "license_upload")
    private byte[] licenseUpload;

    @Column(name = "tin_no")
    private String tinNo;

    @Lob
    @Column(name = "tin_upload")
    private byte[] tinUpload;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "year_of_establishment")
    private LocalDate yearOfEstablishment;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "application_fees")
    private Double applicationFees;

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
    @Column(name = "bank_statement_upload")
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



    public JalaliDate getRegDate() {
        if (reqDate == null) {
            return null; // Return null if issueLicenseDate is null
        }
        DateConverter dateConverter= new DateConverter();
        JalaliDate jalaliRequestDate=dateConverter.gregorianToJalali(reqDate.getYear(),reqDate.getMonthValue(),reqDate.getDayOfMonth());
        return jalaliRequestDate;
    }

    public JalaliDate getyearofEstablishment(){
        if (yearOfEstablishment == null) {
            return null; // Return null if issueLicenseDate is null
        }
        DateConverter dateConverter= new DateConverter();
        JalaliDate jalaliYearofEstablishment=dateConverter.gregorianToJalali(yearOfEstablishment.getYear(),yearOfEstablishment.getMonthValue(),yearOfEstablishment.getDayOfMonth());
        return jalaliYearofEstablishment;
    }

    public JalaliDate getExpiryDate(){
        if (expiryDate == null) {
            return null; // Return null if issueLicenseDate is null
        }
        DateConverter dateConverter= new DateConverter();
        JalaliDate jalaliExpiryDate= dateConverter.gregorianToJalali(expiryDate.getYear(),expiryDate.getMonthValue(),expiryDate.getDayOfMonth()) ;
        return jalaliExpiryDate;
    }
}

