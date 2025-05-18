package atraintegratedsystems.typeofapproval.service;

import atraintegratedsystems.typeofapproval.model.TypeOfApprovalApplicant;
import atraintegratedsystems.typeofapproval.repository.TypeOfApprovalApplicantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class TypeOfApprovalApplicantService {
    @Autowired
    private TypeOfApprovalApplicantRepository typeOfApprovalApplicantRepository;

    @Transactional
    public List<TypeOfApprovalApplicant> showAllTypeOfApprovalApplicants(){
        return typeOfApprovalApplicantRepository.findAll();
    }

    public TypeOfApprovalApplicant addTypeOfApprovalApplicant(TypeOfApprovalApplicant typeOfApprovalApplicant)
    {
        return typeOfApprovalApplicantRepository.save(typeOfApprovalApplicant);
    }
}
