package atraintegratedsystems.typeofapproval.dto;

import lombok.Data;

import javax.persistence.Lob;

@Data
public class TypeOfApprovalStandardCompliantDTO {
    private Long id;

    @Lob
    private byte[] emc;
    private String emcTestReportNo;

    @Lob
    private byte[] radio;
    private String radioTestReportNo;

    @Lob
    private byte[] healthAndSafety;
    private String healthAndSafetyTestReportNo;

    @Lob
    private byte[] technologySpecific;

    private String technologySpecificTestReportNo;
}
