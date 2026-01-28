package atraintegratedsystems.codes.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ShortCodeSerialNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private int serialNumber;


    private String status;

    // ✅ One-to-One (inverse side) - NULL allowed
    @OneToOne(mappedBy = "serialNumber", optional = true)
    private ShortCodeDetail shortCodeDetail;


}
