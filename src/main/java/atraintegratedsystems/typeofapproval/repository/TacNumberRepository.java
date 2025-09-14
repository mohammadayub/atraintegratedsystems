package atraintegratedsystems.typeofapproval.repository;

import atraintegratedsystems.typeofapproval.model.TacNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TacNumberRepository extends JpaRepository<TacNumber, Long> {
}
