package atraintegratedsystems.typeofapproval.service;

import atraintegratedsystems.typeofapproval.model.TypeOfApprovalApplicant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeOfApprovalApplicantAttachmentService {

    @Autowired
    private TypeOfApprovalApplicantService typeOfApprovalApplicantService;

    @Transactional
    public List<TypeOfApprovalApplicant> getTypeOfApprovalApplicantWithAttachment() {
        return typeOfApprovalApplicantService.getAllTypeofApproval();
    }
}

