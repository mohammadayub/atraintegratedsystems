package atraintegratedsystems.typeofapproval.repository;

import atraintegratedsystems.typeofapproval.model.TacNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TacNumberRepository extends JpaRepository<TacNumber, Long> {

    // Check if TAC number already exists for a given manufacturer
    boolean existsByTachNoAndTypeOfApprovalManufacturerDetail_Id(Integer tachNo, Long manufacturerId);

    @Query("SELECT t.tachNo FROM TacNumber t WHERE t.typeOfApprovalManufacturerDetail.id = :manufacturerId AND t.tachNo BETWEEN :from AND :to")
    List<Integer> findExistingTacNumbersInRange(@Param("manufacturerId") Long manufacturerId,
                                                @Param("from") int from,
                                                @Param("to") int to);
}
