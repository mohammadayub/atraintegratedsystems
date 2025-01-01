package atraintegratedsystems.licenses.repository;

import atraintegratedsystems.licenses.model.LicenseAdminFeesExtension;
import atraintegratedsystems.licenses.model.LicenseDatabaseFeesExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LicenseDatabaseFeesExtensionRepository extends JpaRepository<LicenseDatabaseFeesExtension,Long> {


//    New code
    @Query(value = "SELECT * FROM license_database_fees_extension WHERE extend_status != 'Yes'", nativeQuery = true)
    List<LicenseDatabaseFeesExtension> findExtensionsWithStatusNotYes();

    @Query(value = "SELECT * FROM license_database_fees_extension WHERE extend_status = 'Yes'", nativeQuery = true)
    List<LicenseDatabaseFeesExtension> findExtensionsWithStatusYes();
}
