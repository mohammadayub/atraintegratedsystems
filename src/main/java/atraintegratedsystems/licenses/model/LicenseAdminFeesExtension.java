package atraintegratedsystems.licenses.model;

import atraintegratedsystems.utils.DateConverter;
import atraintegratedsystems.utils.JalaliDate;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "license_database_fees_extension")
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


}
