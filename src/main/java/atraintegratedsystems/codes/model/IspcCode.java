package atraintegratedsystems.codes.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Data
public class IspcCode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String ispcCodeName;

    private String assignStatus;

    private LocalDate enteredDate=LocalDate.now();
}
