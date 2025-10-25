package atraintegratedsystems.codes.repository;

import atraintegratedsystems.codes.model.CodeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CodeDetailRepository extends JpaRepository<CodeDetail,Long> {
    Optional<CodeDetail> findByShortCode(int shortCode);

    @Query(value = "SELECT cd.code_detail_id, CONCAT(cd.source_used, '-', cd.short_code) AS company_name_with_code " +
            "FROM code_extension_detail ced RIGHT OUTER JOIN code_detail cd ON ced.code_detail_id = cd.code_detail_id", nativeQuery = true)
    List<Object[]> getCodeDetailWithCompanyNameAndCode();

    @Query(value = "SELECT * FROM code_detail WHERE payment_status IS NULL", nativeQuery = true)
    List<CodeDetail> findUnpaid();


}


