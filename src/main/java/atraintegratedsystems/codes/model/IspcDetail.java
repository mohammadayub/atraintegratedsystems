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
    private String ispcNumber;
    private String signalingPoint;
    private String companyName;
    private String enid;
    private String location;
    private String companyAddress;
    private String responsiblePerson;
    private String job;
    private String mobile;
    private String telephone;
    private String email;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate assigningDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;

    // Fees Related Section
    // Registration Fees Related
    private double registrationFees;
    private String registrationFeesBankVoucherNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate registrationFeesEntryVoucherDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate registrationFeesBankVoucherSubmissionDate;
    private String registrationFeesPaymentStatus;
    private String registrationFessPaymentOrganization="MCIT";



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