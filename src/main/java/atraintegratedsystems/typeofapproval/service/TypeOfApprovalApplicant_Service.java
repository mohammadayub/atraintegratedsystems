package atraintegratedsystems.typeofapproval.service;

import atraintegratedsystems.typeofapproval.model.TypeOfApprovalApplicant;
import atraintegratedsystems.typeofapproval.repository.TypeOfApprovalApplicant_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeOfApprovalApplicant_Service {
    @Autowired
    private TypeOfApprovalApplicant_Repository typeOfApprovalApplicantRepository;

    public List<TypeOfApprovalApplicant> showAllTypeOfApprovalApplicants(){
        return typeOfApprovalApplicantRepository.findAll();
    }
}
