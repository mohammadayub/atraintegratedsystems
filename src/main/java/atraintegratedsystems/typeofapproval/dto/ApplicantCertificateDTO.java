package atraintegratedsystems.typeofapproval.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplicantCertificateDTO {
    private String applicantNumber;
    private String applicantName;
    private String address;
    private String telephone;
    private String email;

    private String modelNumber;
    private String brandName;
    private String typeNumber;
    private String countryOfOrigin;
    private String frequencyRange;
    private String outputPower;
    private String transmissionCapacity;

    private String firstTacNumber;
    private String lastTacNumber;
}
