package atraintegratedsystems.codes.repository;

import atraintegratedsystems.codes.model.SmsIdentifierCode;
import atraintegratedsystems.codes.model.SmsIdentifierSerialNumber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SmsIdentifierSerialNumberRepository extends JpaRepository<SmsIdentifierSerialNumber,Long> {

    List<SmsIdentifierSerialNumber> findByStatusIsNull();
}
