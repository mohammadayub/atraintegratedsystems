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

    @Lob
    @Column(name="declaration_of_Conformity")
    private byte[] declarationOfConformity;

    @Lob
    @Column(name="technical_operational_doc_of_the_RCE")
    private byte[] technicalOperationalDocOfTheRCE;

    @Lob
    @Column(name="test_Reports_of_accredited_laboratory")
    private byte[] testReportsOfAccreditedLaboratory;

    @Lob
    @Column(name="Circuit_diagram_PCB")
    private byte[] CircuitDiagramPCB;

    @Lob
    @Column(name="photographs")
    private byte[] photographs;

    @Lob
    @Column(name="label")
    private byte[] label;

    @Lob
    @Column(name="test_reports_issued_by_accredited_testing")
    private byte[] testReportsIssuedByAccreditedTesting ;

    @ManyToOne
    @JoinColumn(name = "type_of_approval_applicant_id")
    private TypeOfApprovalApplicant applicant;

    private String enteredBy;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate enteredDate;

    @ManyToOne
    @JoinColumn(name = "type_of_approval_applicant_id")
    private TypeOfApprovalApplicant approvalApplicant;


}
