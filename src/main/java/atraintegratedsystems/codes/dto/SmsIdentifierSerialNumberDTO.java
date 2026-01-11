package atraintegratedsystems.codes.dto;

import javax.persistence.Column;

public class SmsIdentifierSerialNumberDTO {
    private Long id;

    @Column(nullable = false)
    private int serialNumber;


    private String status;
}
