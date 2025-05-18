package atraintegratedsystems.typeofapproval.service;

import atraintegratedsystems.typeofapproval.model.TypeOfApprovalManufacturerDetail;
import atraintegratedsystems.typeofapproval.repository.TypeOfApprovalManufacturerDetail_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeOfApprovalManufacturerDetail_Service {
    @Autowired
    private TypeOfApprovalManufacturerDetail_Repository typeOfApprovalManufacturerDetail_repository;

    public List<TypeOfApprovalManufacturerDetail> showAllTypeOfApprovalManufacturerDetail(){
        return typeOfApprovalManufacturerDetail_repository.findAll();
    }

}
