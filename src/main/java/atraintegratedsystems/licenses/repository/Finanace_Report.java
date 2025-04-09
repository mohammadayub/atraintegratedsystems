package atraintegratedsystems.licenses.repository;

import atraintegratedsystems.licenses.model.LicenseApplicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Finanace_Report extends JpaRepository<LicenseApplicant,Long> {

    @Query(value = "SELECT * FROM license_applicants WHERE payment_status = 'Paid'", nativeQuery = true)
    List<LicenseApplicant> applicationFeeFinanaceReport();

}
