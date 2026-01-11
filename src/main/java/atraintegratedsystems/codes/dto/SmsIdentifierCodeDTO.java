package atraintegratedsystems.codes.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class SmsIdentifierCodeDTO {
    private Long id;
    @NotBlank(message = "SMS Identifier Code is required")
    private String smsIdentifierCodeName;

    private String assignStatus;

    private LocalDate EnteredDate;

}
