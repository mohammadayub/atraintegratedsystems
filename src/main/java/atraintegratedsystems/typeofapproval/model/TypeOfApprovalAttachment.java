package atraintegratedsystems.typeofapproval.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class TypeOfApprovalAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Store file paths instead of byte[]
    @Column(name="declaration_of_Conformity")
    private String declarationOfConformity;

    @Column(name="technical_operational_doc_of_the_RCE")
    private String technicalOperationalDocOfTheRCE;

    @Column(name="test_Reports_of_accredited_laboratory")
    private String testReportsOfAccreditedLaboratory;

    @Column(name="Circuit_diagram_PCB")
    private String circuitDiagramPCB;

    @Column(name="photographs")
    private String photographs;

    @Column(name="label")
    private String label;

    @Column(name="test_reports_issued_by_accredited_testing")
    private String testReportsIssuedByAccreditedTesting;

    private String enteredBy;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate enteredDate;

    @ManyToOne
    @JoinColumn(name = "type_of_approval_applicant_id")
    private TypeOfApprovalApplicant approvalApplicant;
}