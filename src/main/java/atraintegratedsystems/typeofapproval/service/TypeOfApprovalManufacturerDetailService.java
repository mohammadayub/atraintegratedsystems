package atraintegratedsystems.typeofapproval.service;

import atraintegratedsystems.typeofapproval.model.TypeOfApprovalApplicant;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalManufacturerDetail;
import atraintegratedsystems.typeofapproval.repository.TypeOfApprovalManufacturerDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeOfApprovalManufacturerDetailService {
    @Autowired
    private TypeOfApprovalManufacturerDetailRepository typeOfApprovalManufacturerDetailrepository;

    @Transactional
    public List<TypeOfApprovalManufacturerDetail> showAllTypeOfApprovalManufacturerDetail(){
        return typeOfApprovalManufacturerDetailrepository.findAll();
    }



}
