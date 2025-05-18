package atraintegratedsystems.typeofapproval.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "typeofapproval_applicants")
@Data
public class TypeOfApprovalApplicant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String manufacturer;
    private String person;
    private String licenseOperator;
    private String authorizedImporter;
    private String importRegistrationNo;
    private String companyName;
    private String contactPerson;
    private String address;
    private String P_O_Box;
    private String telephone;
    private String mobile;
    private String email;
    private String enteredBy;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String enteredDate;




}
