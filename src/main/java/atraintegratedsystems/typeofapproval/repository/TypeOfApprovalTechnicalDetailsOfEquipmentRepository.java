package atraintegratedsystems.typeofapproval.repository;

import atraintegratedsystems.typeofapproval.model.TypeOfApprovalTechnicalDetailsOfTheEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeOfApprovalTechnicalDetailsOfEquipmentRepository
        extends JpaRepository<TypeOfApprovalTechnicalDetailsOfTheEquipment, Long> {
}
