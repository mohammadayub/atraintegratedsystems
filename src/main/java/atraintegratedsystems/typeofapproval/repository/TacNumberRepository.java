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
    boolean existsByTachNoAndTypeOfApprovalManufacturerDetail_Id(String tachNo, Long manufacturerId);

    // Find existing TAC numbers in a given range (passed as strings)
    @Query("SELECT t.tachNo FROM TacNumber t " +
            "WHERE t.typeOfApprovalManufacturerDetail.id = :manufacturerId " +
            "AND t.tachNo IN :range")
    List<String> findExistingTacNumbersInRange(@Param("manufacturerId") Long manufacturerId,
                                               @Param("range") List<String> range);

    // Today New Changes
    @Query(value = "SELECT tach_no FROM tac_number ORDER BY id DESC LIMIT 1", nativeQuery = true)
    String findLatestTacNo();

}
