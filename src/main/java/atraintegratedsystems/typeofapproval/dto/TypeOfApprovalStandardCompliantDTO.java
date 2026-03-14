package atraintegratedsystems.typeofapproval.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class TypeOfApprovalStandardCompliantDTO {
    private MultipartFile emc;
    private String emcTestReportNo;

    private MultipartFile radio;
    private String radioTestReportNo;

    private MultipartFile healthAndSafety;
    private String healthAndSafetyTestReportNo;

    private MultipartFile technologySpecific;
    private String technologySpecificTestReportNo;

    private String enteredBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate enteredDate;
}