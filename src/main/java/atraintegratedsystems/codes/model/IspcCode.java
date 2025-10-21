package atraintegratedsystems.codes.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class IspcCode {
    @Id
    @Column(name="ispc_code",unique = true)
    private int id;
}
