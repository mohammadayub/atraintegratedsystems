package atraintegratedsystems.licenses.repository;

import atraintegratedsystems.licenses.model.LicenseAdminFeesExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LicenseAdminFeesExtensionRepository extends JpaRepository<LicenseAdminFeesExtension,Long> {

    @Query(value = "SELECT * FROM license_admin_fees_extension WHERE extend_status != 'Yes'", nativeQuery = true)
    List<LicenseAdminFeesExtension> findExtensionsWithStatusNotYes();

    @Query(value = "SELECT * FROM license_admin_fees_extension WHERE extend_status = 'Yes' AND extension_administration_payment_status is NULL", nativeQuery = true)
    List<LicenseAdminFeesExtension> findExtensionsWithStatusYes();
}
