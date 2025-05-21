package atraintegratedsystems.typeofapproval.dto;

import lombok.Data;

@Data
public class TypeOfApprovalApplicantDTO {
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

}