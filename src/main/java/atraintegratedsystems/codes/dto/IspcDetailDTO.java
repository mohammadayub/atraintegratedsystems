package atraintegratedsystems.codes.dto;

import atraintegratedsystems.codes.model.IspcCode;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Data
public class IspcDetailDTO {

    private Long id;
    private String serialNumber;
    private String ispcNumber;
    private String signalingPoint;
    private String companyName;
    private String enid;
    private String location;
    private String companyAddress;
    private String responsiblePerson;
    private String job;
    private String mobile;
    private String telephone;
    private String email;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate assigningDate;
    private String assigningDateJalali;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;
    private String expirationDateJalali;

    // Fees Related Section
    // Registration Fees Related
    private double registrationFees;
    private String registrationFeesBankVoucherNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate registrationFeesEntryVoucherDate;
    private String registrationFeesEntryVoucherDateJalali;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate registrationFeesBankVoucherSubmissionDate;
    private String registrationFeesBankVoucherSubmissionDateJalali;
    private String registrationFeesPaymentStatus;
    private String registrationFessPaymentOrganization="MCIT";

    // ispc Code Rejection Section
    private String ispcCodeRejectionStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate ispcCodeRejectionDate;
    private String ispcCodeRejectionDateJalali;

    @ManyToOne
    @JoinColumn(name = "ispc_code_id")
    private IspcCode ispcCode;

    private String ispcCodeName;
    // NEW FIELD
    private Long ispcCodeId;



}
