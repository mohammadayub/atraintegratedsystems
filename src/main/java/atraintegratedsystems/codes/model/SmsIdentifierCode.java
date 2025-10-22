package atraintegratedsystems.codes.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class SmsIdentifierCode {

    @Id
    @Column(name="sms_identifier_code",unique = true)
    private int id;
}
