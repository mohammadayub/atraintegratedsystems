package atraintegratedsystems.licenses.repository;

import atraintegratedsystems.licenses.model.LicenseType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicenseTypeRepository extends JpaRepository<LicenseType,Long> {
}
