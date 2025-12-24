package atraintegratedsystems.codes.repository;

import atraintegratedsystems.codes.model.CodeExtensionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CodeExtensionDetailRepository extends JpaRepository<CodeExtensionDetail,Long> {

    @Query(value = "SELECT * FROM code_extension_detail WHERE payment_status IS NULL", nativeQuery = true)
    List<CodeExtensionDetail> findUnpaid();

    @Query(
            value = "SELECT * FROM code_extension_detail WHERE application_fee_extended_status = 'EXTENDED'",
            nativeQuery = true
    )
    List<CodeExtensionDetail> findApplicationFeeExtended();

    @Query(
            value = "SELECT * FROM code_extension_detail WHERE royalty_fee_extended_status = 'EXTENDED'",
            nativeQuery = true
    )
    List<CodeExtensionDetail> findRoyaltyFeeExtended();

}
