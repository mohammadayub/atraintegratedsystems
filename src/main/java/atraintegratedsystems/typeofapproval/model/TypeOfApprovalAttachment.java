package atraintegratedsystems.typeofapproval.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class TypeOfApprovalAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name="declaration_of_Conformity")
    private byte[] declaration_of_Conformity;

    @Lob
    @Column(name="technical_operational_doc_of_the_RCE")
    private byte[] technical_operational_doc_of_the_RCE;

    @Lob
    @Column(name="test_Reports_of_accredited_laboratory")
    private byte[] test_Reports_of_accredited_laboratory;

    @Lob
    @Column(name="Circuit_diagram_PCB")
    private byte[] Circuit_diagram_PCB;

    @Lob
    @Column(name="photographs")
    private byte[] photographs;

    @Lob
    @Column(name="label")
    private byte[] label;

    @Lob
    @Column(name="test_reports_issued_by_accredited_testing")
    private byte[] test_reports_issued_by_accredited_testing ;

    @ManyToOne
    @JoinColumn(name = "type_of_approval_applicant_id")
    private TypeOfApprovalApplicant applicant;


}
