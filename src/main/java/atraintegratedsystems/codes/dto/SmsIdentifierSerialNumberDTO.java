package atraintegratedsystems.codes.dto;

import lombok.*;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SmsIdentifierSerialNumberDTO {
    private Long id;


    private int serialNumber;


    private String status;
}
