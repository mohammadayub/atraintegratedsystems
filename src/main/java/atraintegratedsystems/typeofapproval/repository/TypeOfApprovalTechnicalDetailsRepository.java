package atraintegratedsystems.typeofapproval.repository;

import atraintegratedsystems.typeofapproval.model.TypeOfApprovalTechnicalDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeOfApprovalTechnicalDetailsRepository
        extends JpaRepository<TypeOfApprovalTechnicalDetail, Long> {

    Optional<TypeOfApprovalTechnicalDetail> findByTechnicalDetailsId(Long applicantId);
}
