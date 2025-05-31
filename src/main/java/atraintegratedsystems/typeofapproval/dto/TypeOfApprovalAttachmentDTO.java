package atraintegratedsystems.typeofapproval.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;

@Data
public class TypeOfApprovalAttachmentDTO {

    private MultipartFile declarationOfConformity;

    private MultipartFile technicalOperationalDocOfTheRCE;


    private MultipartFile testReportsOfAccreditedLaboratory;


    private MultipartFile circuitDiagramPCB;


    private MultipartFile photographs;


    private MultipartFile label;

    private MultipartFile testReportsIssuedByAccreditedTesting ;


    private LocalDate enteredDate;

}
