package atraintegratedsystems.licenses.repository;

import atraintegratedsystems.licenses.model.LicenseApplicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LicenseApplicantRepository extends JpaRepository<LicenseApplicant,Long> {
    @Query("SELECT COALESCE(MAX(l.id), 0) FROM LicenseApplicant l")
    Long findMaxId();


    // Query to find all LicenseApplicant records where paymentStatus is null
    List<LicenseApplicant> findByPaymentStatusIsNull();

}
