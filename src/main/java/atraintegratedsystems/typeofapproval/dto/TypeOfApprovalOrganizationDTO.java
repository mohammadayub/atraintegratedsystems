package atraintegratedsystems.typeofapproval.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TypeOfApprovalOrganizationDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
}
