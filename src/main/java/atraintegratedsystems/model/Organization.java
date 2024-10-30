package atraintegratedsystems.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Data
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="organization_id")
    private int id;
    @NotEmpty(message = "Organization's name cannot be empty.")
    @Column(unique = true)
    @Size(min=1 , max = 12 ,message = "Please enter three characters at least")
    private String name;


}
