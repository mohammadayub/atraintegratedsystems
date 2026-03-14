package atraintegratedsystems.typeofapproval.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class TypeOfApprovalStandardCompliant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="emc")
    private String emc; // file path
    @Column(columnDefinition = "TEXT")
    private String emcTestReportNo;

    @Column(name="radio")
    private String radio; // file path
    @Column(columnDefinition = "TEXT")
    private String radioTestReportNo;

    @Column(name="health_and_safety")
    private String healthAndSafety; // file path
    @Column(columnDefinition = "TEXT")
    private String healthAndSafetyTestReportNo;

    @Column(name="technology_specific")
    private String technologySpecific; // file path
    @Column(columnDefinition = "TEXT")
    private String technologySpecificTestReportNo;

    @ManyToOne
    @JoinColumn(name = "type_of_approval_applicant_id")
    private TypeOfApprovalApplicant standardCompliant;

    private String enteredBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate enteredDate;
}