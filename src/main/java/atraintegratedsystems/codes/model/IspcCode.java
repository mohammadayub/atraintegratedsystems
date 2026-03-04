package atraintegratedsystems.codes.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class IspcCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String ispcCodeName;

    private String assignStatus;

    private LocalDate enteredDate=LocalDate.now();
    // ONE CODE -> MANY DETAILS
    @OneToMany(mappedBy = "ispcCode", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<IspcDetail> ispcDetails;
}
