package atraintegratedsystems.typeofapproval.repository;

import atraintegratedsystems.typeofapproval.model.TypeOfApprovalTechnicalDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeOfApprovalTechnicalDetailsOfEquipmentRepository
        extends JpaRepository<TypeOfApprovalTechnicalDetails, Long> {
}
