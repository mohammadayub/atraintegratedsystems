package atraintegratedsystems.typeofapproval.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "type_of_approval_applicant")
public class TypeOfApprovalApplicant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate requestDate;

    @Column(length = 100)
    private String typeOfApprovalApplicantNumber;

    @Column(columnDefinition = "TEXT")
    private String manufacturer;

    @Column(length = 150)
    private String person;

    @Column(columnDefinition = "TEXT")
    private String licenseOperator;

    @Column(columnDefinition = "TEXT")
    private String authorizedImporter;

    @Column(length = 100)
    private String importRegistrationNo;

    @Column(columnDefinition = "TEXT")
    private String companyName;

    @Column(length = 150)
    private String contactPerson;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(length = 50)
    private String P_O_Box;;

    @Column(length = 30)
    private String telephone;

    @Column(length = 30)
    private String mobile;

    @Column(length = 150)
    private String email;

    @Column(length = 50)
    private String typeSelect;

    private Double applicationFee;

    @Column(columnDefinition = "TEXT")
    private String applicationFeeOrganizationName;

    /* ================= Application Fee ================= */

    @Column(length = 50)
    private String applicationFeeStatus;

    @Column(length = 100)
    private String applicationFeeBankVoucherNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeeBankVoucherSubmissionDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeeVoucherDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeeEntryDate;

    @Column(columnDefinition = "TEXT")
    private String applicationFeeEnteredBy;

    /* ================= Admin Fee ================= */

    private Double adminFee;

    @Column(columnDefinition = "TEXT")
    private String adminFeeOrganizationName;

    @Column(length = 50)
    private String adminFeeStatus;

    @Column(length = 100)
    private String adminFeeBankVoucherNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate adminFeeBankVoucherSubmissionDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate adminFeeVoucherDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate adminFeeEntryDate;

    @Column(columnDefinition = "TEXT")
    private String adminFeeEnteredBy;

    /* ================= Certificate Fee ================= */

    private Double certificateFee;

    @Column(length = 50)
    private String certificateFeeStatus;

    @Column(columnDefinition = "TEXT")
    private String certificateFeeOrganizationName;

    @Column(length = 100)
    private String certificateFeeBankVoucherNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate certificateFeeBankVoucherSubmissionDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate certificateFeeVoucherDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate certificateFeeEntryDate;

    @Column(columnDefinition = "TEXT")
    private String certificateFeeEnteredBy;

    /* ================= Refer Section ================= */

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate referDate;

    @Column(length = 50)
    private String referStatus;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate entryReferDate;

    @Column(columnDefinition = "TEXT")
    private String userEntered;

    /* ================= Relations ================= */

    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TypeOfApprovalManufacturerDetail> manufacturers;

    @OneToMany(mappedBy = "approvalApplicant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TypeOfApprovalAttachment> attachments;

    @OneToMany(mappedBy = "technicalDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TypeOfApprovalTechnicalDetail> details;

    @OneToMany(mappedBy = "standardCompliant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TypeOfApprovalStandardCompliant> standardCompliant;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private TypeOfApprovalOrganization typeOfApprovalOrganization;
}