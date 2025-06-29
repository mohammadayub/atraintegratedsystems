package atraintegratedsystems.typeofapproval.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
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
    private String applicationFeeStatus;
    private String applicationFeeConfirmationDate;
    private String applicationFeeOrganizationName;
    private String applicationFeeBankVoucherNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeeBankVoucherSubmissionDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate confirmationApplicationVoucherDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeeEnteryDate;
    private String applicationFeeEnteredBy;
    private Double adminFee;
    private String adminFeeStatus;
    private String adminFeeConfirmationDate;
    private String adminFeeOrganizationName;
    private String adminFeeBankVoucherNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate adminFeeBankVoucherSubmissionDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate confirmationAdminFeeVoucherDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate adminFeeEnteryDate;
    private String adminFeeEnteredBy;
    private Double certificateFee;
    private String certificateFeeStatus;
    private String certificateFeeConfirmationDate;
    private String certificateFeeOrganizationName;
    private String certificateFeeBankVoucherNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate certificateFeeBankVoucherSubmissionDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate confirmationCertificateFeeVoucherDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate certificateFeeEnteryDate;
    private String certificateFeeEnteredBy;
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