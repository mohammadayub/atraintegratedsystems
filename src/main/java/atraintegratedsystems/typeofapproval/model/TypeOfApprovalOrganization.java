package atraintegratedsystems.typeofapproval.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TypeOfApprovalOrganization {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="organization_id")
    private int id;
    @NotEmpty(message = "Organization's name cannot be empty.")
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "typeOfApprovalOrganization", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TypeOfApprovalApplicant> typeOfApprovalApplicants;
}
