package atraintegratedsystems.typeofapproval.dto;

import atraintegratedsystems.typeofapproval.model.TypeOfApprovalApplicant;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalManufacturerDetail;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TypeOfApprovalFormDTO {
    private TypeOfApprovalApplicant applicant = new TypeOfApprovalApplicant();
    private List<TypeOfApprovalManufacturerDetail> manufacturers = new ArrayList<>();
}
