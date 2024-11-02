package atraintegratedsystems.licenses.model;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class LicenseType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ElementCollection
    @CollectionTable(name = "license_activities", joinColumns = @JoinColumn(name = "license_type_id"))
    @Column(name = "activity")
    private List<String> activities;

    // Getters and Setters
}