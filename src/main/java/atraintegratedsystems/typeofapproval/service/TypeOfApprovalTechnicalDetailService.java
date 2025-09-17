package atraintegratedsystems.typeofapproval.service;

import atraintegratedsystems.typeofapproval.dto.TypeOfApprovalManufacturerDetailDTO;
import atraintegratedsystems.typeofapproval.dto.TypeOfApprovalTechnicalDetailDTO;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalTechnicalDetail;
import atraintegratedsystems.typeofapproval.repository.TypeOfApprovalManufacturerDetailRepository;
import atraintegratedsystems.typeofapproval.repository.TypeOfApprovalTechnicalDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TypeOfApprovalTechnicalDetailService {

    @Autowired
    private TypeOfApprovalTechnicalDetailsRepository repository;
    // Get all manufacturer details
    public List<TypeOfApprovalTechnicalDetail> getAllTechnicalDetails() {
          return repository.findAll();
    }
}
