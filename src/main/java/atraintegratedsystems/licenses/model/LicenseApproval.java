package atraintegratedsystems.licenses.model;

import atraintegratedsystems.utils.DateConverter;
import atraintegratedsystems.utils.JalaliDate;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

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
    @ManyToOne
    @JoinColumn(name = "license_type_id")
    private LicenseType licenseType; // Link to LicenseType entity
    private String currencyType;
    private String financeType;
    @Column(name="license_fees")
    private BigDecimal licenseFees;

    @OneToOne
    @JoinColumn(name = "license_applicant_id", nullable = false)
    private LicenseApplicant licenseApplicant;

    @Column(name = "approved_by", nullable = true)
    private String approvedBy;

    public JalaliDate getAppDate() {
        if (approvalDate == null) {
            return null; // Return null if issueLicenseDate is null
        }
        DateConverter dateConverter= new DateConverter();
        JalaliDate jalaliApprovalDate=dateConverter.gregorianToJalali(approvalDate.getYear(),approvalDate.getMonthValue(),approvalDate.getDayOfMonth());
        return jalaliApprovalDate;
    }



}
