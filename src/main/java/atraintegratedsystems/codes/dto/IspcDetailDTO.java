package atraintegratedsystems.codes.dto;

import atraintegratedsystems.codes.model.IspcCode;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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

    // Registration Fees
    private double registrationFees;
    private String registrationFeesBankVoucherNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate registrationFeesEntryVoucherDate;
    private String registrationFeesEntryVoucherDateJalali;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate registrationFeesBankVoucherSubmissionDate;
    private String registrationFeesBankVoucherSubmissionDateJalali;

    private String registrationFeesPaymentStatus;
    private String registrationFessPaymentOrganization = "MCIT";

    // Rejection
    private String ispcCodeRejectionStatus;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate ispcCodeRejectionDate;
    private String ispcCodeRejectionDateJalali;

    private IspcCode ispcCode;
    private String ispcCodeName;
    private Long ispcCodeId;

    // Default constructor
    public IspcDetailDTO() {
    }

    // Constructor used by JPQL query
    public IspcDetailDTO(
            Long id,
            String companyName,
            String enid,
            String companyAddress,
            String mobile,
            String telephone,
            String email,
            LocalDate assigningDate,
            LocalDate expirationDate,
            double registrationFees,
            String registrationFeesBankVoucherNo,
            LocalDate registrationFeesEntryVoucherDate,
            LocalDate registrationFeesBankVoucherSubmissionDate,
            String registrationFeesPaymentStatus,
            String ispcCodeName
    ) {
        this.id = id;
        this.companyName = companyName;
        this.enid = enid;
        this.companyAddress = companyAddress;
        this.mobile = mobile;
        this.telephone = telephone;
        this.email = email;
        this.assigningDate = assigningDate;
        this.expirationDate = expirationDate;
        this.registrationFees = registrationFees;
        this.registrationFeesBankVoucherNo = registrationFeesBankVoucherNo;
        this.registrationFeesEntryVoucherDate = registrationFeesEntryVoucherDate;
        this.registrationFeesBankVoucherSubmissionDate = registrationFeesBankVoucherSubmissionDate;
        this.registrationFeesPaymentStatus = registrationFeesPaymentStatus;
        this.ispcCodeName = ispcCodeName;
    }
}