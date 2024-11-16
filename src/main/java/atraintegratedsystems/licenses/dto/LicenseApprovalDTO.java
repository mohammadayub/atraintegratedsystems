package atraintegratedsystems.licenses.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LicenseApprovalDTO {
    private Long id;
    private String approvalId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate approvalDate;
    private Long licenseTypeId; // Reference to LicenseType by ID
    private String currencyType;
    private String financeType;
    private BigDecimal licenseFees;
    private Long licenseApplicantId; // Reference to LicenseApplicant by ID
    private String licenseApplicantName; // Optional, if you want a human-readable name for the applicant
    private String approvedBy;
}
