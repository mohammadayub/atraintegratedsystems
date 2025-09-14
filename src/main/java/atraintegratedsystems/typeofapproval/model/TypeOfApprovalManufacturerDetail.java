package atraintegratedsystems.typeofapproval.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class TypeOfApprovalManufacturerDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyName;
    private String authorizedImporter;
    private String contactPerson;
    private String address;
    private String manufacturer_P_O_Box;
    private String manufacturer_telephone;
    private String manufacturer_mobile;
    private String manufacturer_email;
    private String enteredBy;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate enteredDate;
    @ManyToOne
    @JoinColumn(name = "type_of_approval_applicant_id")
    private TypeOfApprovalApplicant applicant;

    @OneToMany(mappedBy = "typeOfApprovalManufacturerDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TacNumber> tacNumbers;


}
