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


    // Native query join with manufacturer detail
//    @Query(value = "SELECT t.id as id, t.tach_no as tachNo, t.created_at as createdAt, " +
//            "m.company_name as companyName, m.authorized_importer as authorizedImporter, " +
//            "m.contact_person as contactPerson, m.address as address, " +
//            "m.manufacturer_p_o_box as manufacturerPobox, m.manufacturer_telephone as manufacturerTelephone, " +
//            "m.manufacturer_mobile as manufacturerMobile, m.manufacturer_email as manufacturerEmail " +
//            "FROM tac_number t " +
//            "JOIN type_of_approval_manufacturer_detail m ON t.manufacturer_detail_id = m.id ", nativeQuery = true)
//    List<Object[]> findAllTacNumbersWithManufacturer();


    @Query(value = "SELECT t FROM TacNumber t JOIN t.typeOfApprovalManufacturerDetail m",
            countQuery = "SELECT count(t) FROM TacNumber t")
    Page<TacNumber> findAllWithManufacturer(Pageable pageable);

}
