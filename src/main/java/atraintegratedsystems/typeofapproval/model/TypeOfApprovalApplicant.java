package atraintegratedsystems.typeofapproval.model;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "typeofapproval_applicants")
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
    private String enteredBy;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate enteredDate;

    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TypeOfApprovalManufacturerDetail> manufacturers;

//    @OneToMany(mappedBy = "approvalApplicant", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<TypeOfApprovalAttachment> attachments;


    @OneToMany(mappedBy = "approvalApplicant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<TypeOfApprovalAttachment> attachments;

    @OneToMany(mappedBy = "technicalDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TypeOfApprovalTechnicalDetail> details;


    @OneToMany(mappedBy = "standardCompliant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TypeOfApprovalStandardCompliant> standardCompliant;




}
