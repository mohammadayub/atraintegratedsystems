package atraintegratedsystems.licenses.repository;

import atraintegratedsystems.licenses.model.LicenseApplicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LicenseApplicantRepository extends JpaRepository<LicenseApplicant,Long> {
    @Query("SELECT COALESCE(MAX(l.id), 0) FROM LicenseApplicant l")
    Long findMaxId();


    // Native query to select all license applicants where payment_status is null
    @Query(value = "SELECT * FROM license_applicants WHERE payment_status IS NULL", nativeQuery = true)
    List<LicenseApplicant> findAllApplicantsWithNullPaymentStatus();

    @Query(value = "SELECT * FROM license_applicants WHERE license_status IS NULL", nativeQuery = true)
    List<LicenseApplicant> findAllApplicantsNotApproved();

    @Query(value = "SELECT * FROM license_applicants WHERE req_id = ?1", nativeQuery = true)
    Optional<LicenseApplicant> findByReqId(String reqId);

    @Query(value = "SELECT * FROM license_applicants WHERE payment_status='Paid'", nativeQuery = true)
    List<LicenseApplicant> findAllApplicantsWithPaid();







}
