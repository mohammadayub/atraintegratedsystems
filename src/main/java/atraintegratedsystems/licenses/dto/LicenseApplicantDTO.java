package atraintegratedsystems.licenses.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class LicenseApplicantDTO {
    private Long id;
    private String reqId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate reqDate;
    private Long licenseTypeId; // Foreign key reference
    private String currencyType;
    private String financeType;
    private String companyLicenseName;
    private String licenseNo;
    private MultipartFile licenseUpload;
    private String tinNo;
    private MultipartFile tinUpload;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate yearOfEstablishment;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiryDate;
    private Double applicationFees;
    private String plannedActivitiesAndServices;
    private Integer totalNationalEmployees;
    private Integer totalInternationalEmployees;
    private Double expectedInvestment;
    private Double cash;
    private MultipartFile bankStatementUpload;
    private String otherLicenseTaken;
    private String companyAddress;
    private String contactNo;
    private String email;
    private String website;
    private String postAddress;

}
