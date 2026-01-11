package atraintegratedsystems.codes.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class SmsIdentifierSerialNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private int serialNumber;


    private String status;

}
