package atraintegratedsystems.codes.repository;

import atraintegratedsystems.codes.model.SmsIdentifierExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsIdentifierExtensionRepository extends JpaRepository<SmsIdentifierExtension,Long> {
}
