package atraintegratedsystems.codes.repository;
import atraintegratedsystems.codes.model.SmsExtensionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SmsExtensionDetailRepository extends JpaRepository<SmsExtensionDetail,Long> {

    @Query(value = "SELECT * FROM sms_extension_detail WHERE payment_status IS NULL", nativeQuery = true)
    List<SmsExtensionDetail> findUnpaid();


}
