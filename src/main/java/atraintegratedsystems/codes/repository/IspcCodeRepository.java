package atraintegratedsystems.codes.repository;
import atraintegratedsystems.codes.model.IspcCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IspcCodeRepository extends JpaRepository<IspcCode,Long> {

}
