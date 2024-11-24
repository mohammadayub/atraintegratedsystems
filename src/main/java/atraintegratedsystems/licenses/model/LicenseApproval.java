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
    @Column(name="approval_status")
    private String approvalStatus;
    @Column(name="board_decisions")
    private String boardDecisions;
    private String boardDecisionNumber;
    @ManyToOne
    @JoinColumn(name = "license_type_id")
    private LicenseType licenseType; // Link to LicenseType entity
    private String currencyType;
    @Column(name="license_fees")
    private BigDecimal licenseFees;
    @Column(name="license_payment_office")
    private String licensePaymentOffice;
    @Column(name="adminstrative_yearly_fees")
    private BigDecimal administrativeYearlyFees;
    @Column(name="administrative_yearly_fees_payment_office")
    private String adminstrivateYearlyFeesPaymentOffice;
    @Column(name="guarantee_fees")
    private BigDecimal guaranteeFees;
    @Column(name="gurantee_fees_payment_office")
    private String guaranteeFeesPaymentOffice;
    @Column(name="database_yearly_maintainance_fees")
    private BigDecimal databaseYearlyMaintainanceFees;
    @Column(name="database_yearly_maintainance_fees_payment_office")
    private String databaseYearlyMaintainanceFeesPaymentOffice;
    @ManyToOne
    @JoinColumn(name = "license_applicant_id", nullable = false)
    private LicenseApplicant licenseApplicant;
    public JalaliDate getAppDate() {
        if (approvalDate == null) {
            return null; // Return null if issueLicenseDate is null
        }
        DateConverter dateConverter= new DateConverter();
        JalaliDate jalaliApprovalDate=dateConverter.gregorianToJalali(approvalDate.getYear(),approvalDate.getMonthValue(),approvalDate.getDayOfMonth());
        return jalaliApprovalDate;
    }



}
