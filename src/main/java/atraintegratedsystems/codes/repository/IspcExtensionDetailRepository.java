package atraintegratedsystems.codes.repository;
import atraintegratedsystems.codes.model.IspcExtensionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IspcExtensionDetailRepository extends JpaRepository<IspcExtensionDetail,Long> {

    @Query(value = "SELECT cd.ispc_detail_id, CONCAT(cd.source_used, '-', cd.ispc_code) AS company_name_with_code " +
            "FROM ispc_extension_detail ced RIGHT OUTER JOIN ispc_detail cd ON ced.ispc_detail_id = cd.ispc_detail_id", nativeQuery = true)
    List<Object[]> getCompanyNamesWithCodes();

    @Query(value = "SELECT * FROM ispc_extension_detail WHERE payment_status IS NULL", nativeQuery = true)
    List<IspcExtensionDetail> findUnpaid();
}
