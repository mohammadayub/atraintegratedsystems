package atraintegratedsystems.codes.repository;
import atraintegratedsystems.codes.model.SmsIdentifierCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SmsIdentifierCodeRepository extends JpaRepository<SmsIdentifierCode,Integer> {

    @Query(value = "SELECT sms_identifier_code.sms_identifier_code, " +
            "CONCAT(sms_identifier_code.sms_identifier_code, ' - ', " +
            "CASE " +
            "WHEN sms_identifier_detail.sms_identifier_code IS NULL THEN 'unassigned' " +
            "ELSE 'assign' " +
            "END) AS SMSIdentifierCode " +
            "FROM sms_identifier_code " +
            "LEFT JOIN sms_identifier_detail ON sms_identifier_code.sms_identifier_code = sms_identifier_detail.sms_identifier_code",
            nativeQuery = true)
    List<Object[]> getSMSIdentifiersWithStatus();


    @Query(value = "SELECT sms_identifier_code.sms_identifier_code,  " +
            "       CASE WHEN sms_identifier_detail.sms_identifier_code IS NULL THEN 'unassigned' ELSE 'assign' END AS assignment " +
            "FROM sms_identifier_code " +
            "LEFT JOIN sms_identifier_detail ON sms_identifier_code.sms_identifier_code = sms_identifier_detail.sms_identifier_code", nativeQuery = true)
    List<Object[]> getCodeData();

}
