package atraintegratedsystems.typeofapproval.repository;

import atraintegratedsystems.typeofapproval.model.TacNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TacNumberRepository extends JpaRepository<TacNumber, Long> {

    // Check if TAC number already exists for a given manufacturer
    boolean existsByTachNoAndTypeOfApprovalManufacturerDetail_Id(Integer tachNo, Long manufacturerId);
}
