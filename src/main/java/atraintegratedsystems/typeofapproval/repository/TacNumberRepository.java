package atraintegratedsystems.typeofapproval.repository;

import atraintegratedsystems.typeofapproval.model.TacNumber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TacNumberRepository extends JpaRepository<TacNumber, Long> {

    // ✅ Check if TAC number already exists for a given technical detail
    boolean existsByTacNoAndTypeOfApprovalTechnicalDetail_Id(String tacNo, Long technicalId);

    // ✅ Find existing TAC numbers in a given range (passed as strings)
    @Query("SELECT t.tacNo FROM TacNumber t " +
            "WHERE t.typeOfApprovalTechnicalDetail.id = :technicalId " +
            "AND t.tacNo IN :range")
    List<String> findExistingTacNumbersInRange(@Param("technicalId") Long technicalId,
                                               @Param("range") List<String> range);

    // ✅ Get latest TAC number
    @Query(value = "SELECT tac_no FROM tac_number ORDER BY id DESC LIMIT 1", nativeQuery = true)
    String findLatestTacNo();

    // ✅ Fetch all TAC numbers with technical details (paginated)
    @Query(value = "SELECT t FROM TacNumber t JOIN FETCH t.typeOfApprovalTechnicalDetail",
            countQuery = "SELECT count(t) FROM TacNumber t")
    Page<TacNumber> findAllWithTechnicalDetail(Pageable pageable);

    // ✅ Search TAC numbers by brand or model
    @Query("SELECT t FROM TacNumber t " +
            "JOIN t.typeOfApprovalTechnicalDetail m " +
            "WHERE LOWER(m.brandName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "   OR LOWER(m.modelNumber) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<TacNumber> searchAll(@Param("keyword") String keyword);

    // ✅ Search TAC numbers by brand or model
//    @Query("SELECT t FROM TacNumber t " +
////            "JOIN t.typeOfApprovalTechnicalDetail m " +
////            "WHERE LOWER(m.companyName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
////            "   OR LOWER(m.modelNumber) LIKE LOWER(CONCAT('%', :keyword, '%'))")
////    List<TacNumber> searchPrint(@Param("keyword") String keyword);

    @Query("SELECT t FROM TacNumber t " +
            "JOIN t.typeOfApprovalTechnicalDetail td " +
            "JOIN td.technicalDetails applicant " +  // join to the applicant
            "WHERE LOWER(applicant.companyName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "   OR LOWER(t.tacNo) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<TacNumber> searchPrint(@Param("keyword") String keyword);

}
