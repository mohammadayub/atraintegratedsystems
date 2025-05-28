package atraintegratedsystems.typeofapproval.repository;

import atraintegratedsystems.typeofapproval.model.TypeOfApprovalTechnicalDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeOfApprovalTechnicalDetailsRepository
        extends JpaRepository<TypeOfApprovalTechnicalDetail, Long> {
}
