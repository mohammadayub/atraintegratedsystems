package atraintegratedsystems.codes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import atraintegratedsystems.utils.DateConverter;
import atraintegratedsystems.utils.JalaliDate;

@Entity
@Data
public class IspcExtensionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Standard Department
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extensionStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extentionExpirationDate;

    @Column(name="extend_status")
    private String extendStatus;


    // For Finance Departpemtn
    // Bellow is Application Fee Extension Fields
    private double extendedFees=4000;
    private String extensionBankVoucherNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extensionEnteryVoucherDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extensionBankVoucherSubmissionDate;
    private String extensionPaymentStatus;


    private String extensionBy;

    @ManyToOne
    @JoinColumn(name = "ispc_detail_id")
    private IspcDetail ispcDetail;

    public JalaliDate getExtensionStartDateJalali() {
        if (extensionStartDate == null) {
            return null;
        }

        DateConverter dateConverter = new DateConverter();
        return dateConverter.gregorianToJalali(
                extensionStartDate.getYear(),
                extensionStartDate.getMonthValue(),
                extensionStartDate.getDayOfMonth()
        );
    }

    public JalaliDate getExtentionExpirationDateJalali() {
        if (extentionExpirationDate == null) {
            return null;
        }

        DateConverter dateConverter = new DateConverter();
        return dateConverter.gregorianToJalali(
                extentionExpirationDate.getYear(),
                extentionExpirationDate.getMonthValue(),
                extentionExpirationDate.getDayOfMonth()
        );
    }

    public JalaliDate getExtensionEnteryVoucherDateJalali() {
        if (extensionEnteryVoucherDate == null) {
            return null;
        }

        DateConverter dateConverter = new DateConverter();
        return dateConverter.gregorianToJalali(
                extensionEnteryVoucherDate.getYear(),
                extensionEnteryVoucherDate.getMonthValue(),
                extensionEnteryVoucherDate.getDayOfMonth()
        );
    }

    public JalaliDate getExtensionBankVoucherSubmissionDateJalali() {
        if (extensionBankVoucherSubmissionDate == null) {
            return null;
        }

        DateConverter dateConverter = new DateConverter();
        return dateConverter.gregorianToJalali(
                extensionBankVoucherSubmissionDate.getYear(),
                extensionBankVoucherSubmissionDate.getMonthValue(),
                extensionBankVoucherSubmissionDate.getDayOfMonth()
        );
    }


}

