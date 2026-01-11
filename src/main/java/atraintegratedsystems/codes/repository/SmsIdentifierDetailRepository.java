package atraintegratedsystems.codes.repository;
import atraintegratedsystems.codes.model.SmsIdentifierDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SmsIdentifierDetailRepository extends JpaRepository<SmsIdentifierDetail,Long> {

}
