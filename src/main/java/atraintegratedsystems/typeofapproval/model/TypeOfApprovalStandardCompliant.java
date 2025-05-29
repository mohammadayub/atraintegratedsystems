package atraintegratedsystems.typeofapproval.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class TypeOfApprovalStandardCompliant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    @Column(name="emc")
    private byte[] emc;
    private String emcTestReportNo;
    @Lob
    @Column(name="radio")
    private byte[] radio;
    private String radioTestReportNo;
    @Lob
    @Column(name="health_and_safety")
    private byte[] healthAndSafety;
    private String healthAndSafetyTestReportNo;
    @Lob
    @Column(name="technology_specific")
    private byte[] technologySpecific;
    private String technologySpecificTestReportNo;
    @ManyToOne
    @JoinColumn(name = "type_of_approval_applicant_id")
    private TypeOfApprovalApplicant standardCompliant;
}
