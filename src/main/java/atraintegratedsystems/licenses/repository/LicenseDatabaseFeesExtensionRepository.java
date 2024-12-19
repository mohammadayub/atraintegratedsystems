package atraintegratedsystems.licenses.repository;

import atraintegratedsystems.licenses.model.LicenseDatabaseFeesExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LicenseDatabaseFeesExtensionRepository extends JpaRepository<LicenseDatabaseFeesExtension,Long> {

}
