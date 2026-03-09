package atraintegratedsystems.codes.model;
import atraintegratedsystems.utils.DateConverter;
import atraintegratedsystems.utils.JalaliDate;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class IspcDetail {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serialNumber;


    private String companyName;
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
    private double applicationFees;
    private String applicationFeesBankVoucherNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeesEntryVoucherDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeesBankVoucherSubmissionDate;
    private String applicationFeesPaymentStatus;
    private String applicationFessPaymentOrganization="MCIT";

    // Royalty Fees Related
    private double royaltyFees;
    private String royaltyFeesBankVoucherNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate royaltyFeesEntryVoucherDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate royaltyFeesBankVoucherSubmissionDate;
    private String royaltyFeesPaymentStatus;
    private String royaltyFeesPaymentOrganization="ATRA";


    // ispc Code Rejection Section
    private String ispcCodeRejectionStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate ispcCodeRejectionDate;

    @ManyToOne
    @JoinColumn(name = "ispc_code_id")
    private IspcCode ispcCode;

    public JalaliDate getAssigningDateJalali() {

        if(assigningDate == null){
            return null;
        }

        DateConverter converter = new DateConverter();

        return converter.gregorianToJalali(
                assigningDate.getYear(),
                assigningDate.getMonthValue(),
                assigningDate.getDayOfMonth()
        );
    }


    public JalaliDate getExpirationDateJalali() {

        if(expirationDate == null){
            return null;
        }

        DateConverter converter = new DateConverter();

        return converter.gregorianToJalali(
                expirationDate.getYear(),
                expirationDate.getMonthValue(),
                expirationDate.getDayOfMonth()
        );
    }

}