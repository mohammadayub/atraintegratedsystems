package atraintegratedsystems.codes.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    // ONE CODE -> MANY DETAILS
    @OneToMany(mappedBy = "smsIdentifierCode", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SmsIdentifierDetail> smsIdentifierDetails;



}
