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


    private String typeOfApprovalApplicantNumber;

    @Column(columnDefinition = "TEXT")
    private String manufacturer;

    private String person;

    @Column(columnDefinition = "TEXT")
    private String licenseOperator;

    @Column(columnDefinition = "TEXT")
    private String authorizedImporter;


    private String importRegistrationNo;

    @Column(columnDefinition = "TEXT")
    private String companyName;


    private String contactPerson;

    @Column(columnDefinition = "TEXT")
    private String address;


    private String P_O_Box;;


    private String telephone;


    private String mobile;


    private String email;


    private String typeSelect;

    private Double applicationFee;

    @Column(columnDefinition = "TEXT")
    private String applicationFeeOrganizationName;

    /* ================= Application Fee ================= */


    private String applicationFeeStatus;


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


    private String adminFeeStatus;


    private String adminFeeBankVoucherNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate adminFeeBankVoucherSubmissionDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate adminFeeVoucherDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate adminFeeEntryDate;


    private String adminFeeEnteredBy;

    /* ================= Certificate Fee ================= */

    private Double certificateFee;


    private String certificateFeeStatus;

    @Column(columnDefinition = "TEXT")
    private String certificateFeeOrganizationName;


    private String certificateFeeBankVoucherNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate certificateFeeBankVoucherSubmissionDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate certificateFeeVoucherDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate certificateFeeEntryDate;


    private String certificateFeeEnteredBy;

    /* ================= Refer Section ================= */

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate referDate;


    private String referStatus;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate entryReferDate;


    private String enteredBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate enteredDate;

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