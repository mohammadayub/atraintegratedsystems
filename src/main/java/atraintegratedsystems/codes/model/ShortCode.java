package atraintegratedsystems.codes.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class ShortCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer shortCodeName;

    private String assignStatus = "UNASSIGN";


    @ToString.Exclude
    @OneToMany(mappedBy = "shortCode", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ShortCodeDetail> shortCodeDetail;
}