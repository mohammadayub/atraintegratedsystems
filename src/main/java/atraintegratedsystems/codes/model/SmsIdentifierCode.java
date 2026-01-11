package atraintegratedsystems.codes.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Data
public class SmsIdentifierCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String smsIdentifierCodeName;

    private String assignStatus;

    private LocalDate enteredDate=LocalDate.now();

    // RelationShips

    // One-to-One relationship
    @OneToOne(mappedBy = "smsIdentifierCode",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private SmsIdentifierDetail smsIdentifierDetail;

}
