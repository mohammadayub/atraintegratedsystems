package atraintegratedsystems.codes.repository;

import atraintegratedsystems.codes.model.SmsIdentifierCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SmsIdentifierCodeRepository extends JpaRepository<SmsIdentifierCode, Long> {

    boolean existsBySmsIdentifierCodeName(String smsIdentifierCodeName);

    Optional<SmsIdentifierCode> findBySmsIdentifierCodeName(String smsIdentifierCodeName);

    // ✅ Get all records where assignStatus IS NULL
    List<SmsIdentifierCode> findByAssignStatusIsNullOrAssignStatusEquals(String status);

}
