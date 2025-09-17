package atraintegratedsystems.typeofapproval.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;

@Data
@Entity
public class TacNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String tacNo;

    private LocalDate createdAt=LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "technical_detail_id")
    private TypeOfApprovalTechnicalDetail typeOfApprovalTechnicalDetail;
}