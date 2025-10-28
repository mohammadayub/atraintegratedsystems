package atraintegratedsystems.typeofapproval.repository;

import atraintegratedsystems.typeofapproval.model.TypeOfApprovalOrganization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeOfApprovalOrganizationRepository extends JpaRepository<TypeOfApprovalOrganization, Integer> {
    boolean existsByName(String name);
}
