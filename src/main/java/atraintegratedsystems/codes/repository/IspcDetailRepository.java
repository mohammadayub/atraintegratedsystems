package atraintegratedsystems.codes.repository;
import atraintegratedsystems.codes.model.IspcDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IspcDetailRepository extends JpaRepository<IspcDetail,Long> {

}
