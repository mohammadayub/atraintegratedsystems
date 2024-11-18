package atraintegratedsystems.licenses.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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