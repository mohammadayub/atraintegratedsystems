package atraintegratedsystems.typeofapproval.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class TypeOfApprovalApplicantDTO {
    private long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate requestDate;
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
    private String typeSelect;
    private Double applicationFee;
    private String applicationFeeOrganizationName;
    private Double adminFee;
    private String adminFeeOrganizationName;
    private Double certificateFee;
    private String certificateFeeOrganizationName;
    private String enteredBy;


    //Refer to Finance Department
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate referDate;
    private String referStatus;


    //Finance Section
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate voucherDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate submissionVoucherDate;
    private String paymentStatus;

}