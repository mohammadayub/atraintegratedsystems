package atraintegratedsystems.typeofapproval.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "typeofapproval_applicants")
@Data
public class TypeOfApprovalApplicant {

    private long id;
    private String manufacturer;
    private String person;
    private String licenseOperator;

}
