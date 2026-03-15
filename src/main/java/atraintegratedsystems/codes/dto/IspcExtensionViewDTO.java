package atraintegratedsystems.codes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class IspcExtensionViewDTO {

    private Long id;   // Extension ID

    private String ispcCodeName;

    private String serialNumber;
    private String signalingPoint;
    private String companyName;
    private String enid;
    private String location;
    private String companyAddress;
    private String responsiblePerson;
    private String job;
    private String mobile;
    private String telephone;
    private String email;
    private LocalDate assigningDate;
    private LocalDate expirationDate;
    private String extensionPaymentStatus;
    private LocalDate extensionStartDate;
    private LocalDate extentionExpirationDate;
    private String extendStatus;
}