package atraintegratedsystems.codes.model;

import atraintegratedsystems.utils.DateConverter;
import atraintegratedsystems.utils.JalaliDate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class SmsIdentifierDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String serialNumber;
    private String companyName;
    private String companyNameInDari;
    private String enid;
    private String companyAddress;
    private String responsiblePerson;
    private String job;
    private String mobile;
    private String telephone;
    private String email;
    private String channel;
    private String serviceType;
    private String mnosCompanyHost;
    private String codeCategory="Golden";
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate assigningDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;

    // Fees Related Section
    // Application Fees Related
    private double applicationFees=5000;
    private String applicationFeesBankVoucherNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeesEnteryVoucherDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeesBankVoucherSubmissionDate;
    private String applicationFeesPaymentStatus;
    private String applicationFessPaymentOrganization="MCIT";

    // Royalty Fees Related
    private double royaltyFees=70000;
    private String royaltyFeesBankVoucherNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate royaltyFeesEnteryVoucherDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate royaltyFeesBankVoucherSubmissionDate;
    private String royaltyFeesPaymentStatus;
    private String royaltyFeesPaymentOrganization="ATRA";


    // smsIdentifier Code Rejection Section
    private String shortCodeRejectionStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate shortCodeRejectionDate;

    private String remark;


    // RelationShips

    @ManyToOne
    @JoinColumn(name = "sms_identifier_code_id")
    private SmsIdentifierCode smsIdentifierCode;

    @OneToMany(
            mappedBy = "smsIdentifierDetail",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<SmsIdentifierExtension> extensions = new ArrayList<>();

    public JalaliDate getAssigningDateJalali() {
        if (assigningDate == null) {
            return null; // Return null if issueLicenseDate is null
        }
        DateConverter dateConverter= new DateConverter();
        JalaliDate jalaliAssigningDate=dateConverter.gregorianToJalali(assigningDate.getYear(),assigningDate.getMonthValue(),assigningDate.getDayOfMonth());
        return jalaliAssigningDate;
    }


    public JalaliDate getExpirationDateJalali() {
        if (expirationDate == null) {
            return null; // Return null if issueLicenseDate is null
        }
        DateConverter dateConverter= new DateConverter();
        JalaliDate jalaliExpirationDate=dateConverter.gregorianToJalali(expirationDate.getYear(),expirationDate.getMonthValue(),expirationDate.getDayOfMonth());
        return jalaliExpirationDate;
    }


















}
