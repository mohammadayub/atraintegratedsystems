package atraintegratedsystems.typeofapproval.repository;

import atraintegratedsystems.typeofapproval.model.TypeOfApprovalStandardCompliant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeOfApprovalStandardComplaintRepository extends JpaRepository<TypeOfApprovalStandardCompliant,Long> {

    // In TypeOfApprovalStandardComplaintRepository
    Optional<TypeOfApprovalStandardCompliant> findByStandardCompliantId(Long applicantId);
}
