package atraintegratedsystems.typeofapproval.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class TacNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String tachNo;

    private LocalDate createdAt=LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "manufacturer_detail_id")
    private TypeOfApprovalManufacturerDetail typeOfApprovalManufacturerDetail;
}