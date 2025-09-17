package atraintegratedsystems.typeofapproval.repository;

import atraintegratedsystems.typeofapproval.model.TacNumber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface TacNumberRepository extends JpaRepository<TacNumber, BigInteger> {

//    // Check if TAC number already exists for a given manufacturer
//    boolean existsByTachNoAndTypeOfApprovalManufacturerDetail_Id(String tacNo, Long technicalId);

    // Find existing TAC numbers in a given range (passed as strings)
    @Query("SELECT t.tacNo FROM TacNumber t " +
            "WHERE t.typeOfApprovalTechnicalDetail.id = :technicalId " +
            "AND t.tacNo IN :range")
    List<String> findExistingTacNumbersInRange(@Param("technicalId") Long technicalId,
                                               @Param("range") List<String> range);

    // Today New Changes
    @Query(value = "SELECT tac_no FROM tac_number ORDER BY id DESC LIMIT 1", nativeQuery = true)
    String findLatestTacNo();




    @Query(value = "SELECT t FROM TacNumber t JOIN t.typeOfApprovalTechnicalDetail m",
            countQuery = "SELECT count(t) FROM TacNumber t")
    Page<TacNumber> findAllWithTechnicalDetail(Pageable pageable);

    @Query("SELECT t FROM TacNumber t " +
            "JOIN t.typeOfApprovalTechnicalDetail m " +
            "WHERE LOWER(m.brandName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "   OR LOWER(m.modelNumber) LIKE LOWER(CONCAT('%', :keyword, '%')) " )
    List<TacNumber> searchAll(@Param("keyword") String keyword);




}