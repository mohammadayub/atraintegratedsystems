package atraintegratedsystems.codes.repository;
import atraintegratedsystems.codes.model.SmsIdentifierDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SmsIdentifierDetailRepository extends JpaRepository<SmsIdentifierDetail,Long> {

    @Query(value = "SELECT cd.sms_identifier_detail_id, CONCAT(cd.source_used, '-', cd.sms_identifier_code) AS company_name_with_code " +
            "FROM sms_extension_detail ced RIGHT OUTER JOIN sms_identifier_detail cd ON ced.sms_identifier_detail_id = cd.sms_identifier_detail_id", nativeQuery = true)
    List<Object[]> getCompanyNamesWithCodes();

    @Query(value = "SELECT * FROM sms_identifier_detail WHERE payment_status IS NULL", nativeQuery = true)
    List<SmsIdentifierDetail> findUnpaid();
}
