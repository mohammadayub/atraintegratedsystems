package atraintegratedsystems.licenses.dto;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class LicenseApplicantDTO {
    private Long id;
    private String reqId;
    private String reqDateJalali;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate reqDate;
    private Long licenseTypeId; // Foreign key reference
    private String licenseTypeName;
    private String currencyType;
    private String financeType;
    private String companyLicenseName;
    private MultipartFile applicationUpload;
    private MultipartFile enidUpload;
    private MultipartFile articleOfAssociationUpload;
    private MultipartFile businessPlanUpload;
    private String licenseNo;
    private MultipartFile licenseUpload;
    private String tinNo;
    private MultipartFile identityFormUpload;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate yearOfEstablishment;
    private String jalaliYearOfEstablishment;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiryDate;
    private String jalaliExpiryDate;
    private BigDecimal applicationFees;
    private int validity;
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
    private String entryApplicationFeeVoucherDateJalali;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate entryApplicationFeeVoucherDate;
    private String bankVoucher;
    private String applicationFeeBankVoucherSubmissionDateJalali;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeeBankVoucherSubmissionDate;
    private String paymentStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationFeeExpiryDate;
    private String jalaliReferToBoardDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate referToBoardDate;
    private String isSend;
    private MultipartFile proposalUpload;

    //Audit for Profile
    private String profileEnteredBy;
    private LocalDateTime profileEnteredCreatedDate;
    private String completeProfileEnteredBy;
    private LocalDateTime completedProfileCreatedDate;
    //Audit for Application Fee
    private String applicationFeeEnteredBy;
    private LocalDateTime applicationFeeCreatedDate;

    //Audit for Send to Board
    private String sendToBoardEnteredBy;
    private LocalDateTime sendToBoardCreatedDate;


}
