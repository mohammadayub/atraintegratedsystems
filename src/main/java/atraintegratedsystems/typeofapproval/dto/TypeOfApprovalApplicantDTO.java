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

    private String applicationFeeBankVoucherSubmissionDateJalali;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeeBankVoucherSubmissionDate;
    private String applicationFeeVoucherDateJalali;
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
    private String adminFeeBankVoucherSubmissionDateJalali;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate adminFeeBankVoucherSubmissionDate;
    private String adminFeeVoucherDateJalali;
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
    private String certificateFeeBankVoucherSubmissionDateJalali;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate certificateFeeBankVoucherSubmissionDate;
    private String certificateFeeVoucherDateJalali;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate certificateFeeVoucherDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")

    //Audit Section
    private LocalDate certificateFeeEntryDate;
    private String certificateFeeEnteredBy;


    //Refer to Finance Department
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate referDate;
    private String referDateJalali;
    private String referStatus;






}