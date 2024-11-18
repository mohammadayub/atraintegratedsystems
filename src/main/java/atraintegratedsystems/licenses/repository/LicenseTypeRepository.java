package atraintegratedsystems.licenses.repository;

import atraintegratedsystems.licenses.model.LicenseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LicenseTypeRepository extends JpaRepository<LicenseType,Long> {


}
