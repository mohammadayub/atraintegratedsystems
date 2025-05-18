package atraintegratedsystems.typeofapproval.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class TypeOfApprovalManufacturerDetailDTO {

    private Long id;
    private String companyName;
    private String authorizedImporter;
    private String contactPerson;
    private String address;
    private String manufacturer_P_O_Box;
    private String manufacturer_telephone;
    private String manufacturer_mobile;
    private String manufacturer_email;
    private String enteredBy;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String enteredDate;
}
