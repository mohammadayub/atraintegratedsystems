package atraintegratedsystems.codes.repository;

import atraintegratedsystems.codes.model.SmsIdentifierApplicationFeesExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsIdentifierApplicationFeesExtensionRepository extends JpaRepository<SmsIdentifierApplicationFeesExtension,Long> {
}
