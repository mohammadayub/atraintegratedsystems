package atraintegratedsystems.typeofapproval.dto;

import atraintegratedsystems.typeofapproval.model.TypeOfApprovalApplicant;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalManufacturerDetail;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalTechnicalDetail;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
public class TypeOfApprovalFormDTO {
    private TypeOfApprovalApplicant applicant = new TypeOfApprovalApplicant();
    private List<TypeOfApprovalManufacturerDetail> manufacturers = new ArrayList<>();
    private List<TypeOfApprovalTechnicalDetail> details= new ArrayList<>();
    private MultipartFile declarationOfConformity;
    private MultipartFile technicalOperationalDocOfTheRCE;
    private MultipartFile testReportsOfAccreditedLaboratory;
    private MultipartFile circuitDiagramPCB;
    private MultipartFile photographs;
    private MultipartFile label;
    private MultipartFile testReportsIssuedByAccreditedTesting;

}
