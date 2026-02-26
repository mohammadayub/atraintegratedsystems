package atraintegratedsystems.codes.repository;

import atraintegratedsystems.codes.model.ShortCodeSerialNumber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShortCodeSerialNumberRepository extends JpaRepository<ShortCodeSerialNumber,Long> {

    List<ShortCodeSerialNumber> findByStatusIsNull();
}
