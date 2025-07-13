package atraintegratedsystems.typeofapproval.model;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class TypeOfApprovalApplicant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate requestDate;
    private String manufacturer;
    private String person;
    private String licenseOperator;
    private String authorizedImporter;
    private String importRegistrationNo;
    @Column(unique = true, nullable = false)
    private String companyName;
    private String contactPerson;
    private String address;
    private String P_O_Box;
    private String telephone;
    private String mobile;
    private String email;
    private String typeSelect;
    private Double applicationFee;
    private String applicationFeeOrganizationName;

    //Application Fee Finance Section
    private String applicationFeeStatus;
    private String applicationFeeBankVoucherNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeeBankVoucherSubmissionDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeeVoucherDate;


    //Audit Section
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeeEntryDate;
    private String applicationFeeEnteredBy;


    private Double adminFee;
    private String adminFeeOrganizationName;

    //Admin Fee Finance Section
    private String adminFeeStatus;

    private String adminFeeBankVoucherNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate adminFeeBankVoucherSubmissionDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate adminFeeVoucherDate;

    //Audit Section
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate adminFeeEntryDate;
    private String adminFeeEnteredBy;


    private Double certificateFee;

    //Certificate Fee Section
    private String certificateFeeStatus;
    private String certificateFeeOrganizationName;
    private String certificateFeeBankVoucherNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate certificateFeeBankVoucherSubmissionDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate certificateFeeVoucherDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")

    //Audit Section
    private LocalDate certificateFeeEntryDate;
    private String certificateFeeEnteredBy;


    //Refer to Finance Department
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate referDate;
    private String referStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate entryReferDate;
    private String userEntered;



    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TypeOfApprovalManufacturerDetail> manufacturers;



    @OneToMany(mappedBy = "approvalApplicant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TypeOfApprovalAttachment> attachments;

    @OneToMany(mappedBy = "technicalDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TypeOfApprovalTechnicalDetail> details;


    @OneToMany(mappedBy = "standardCompliant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TypeOfApprovalStandardCompliant> standardCompliant;




}
