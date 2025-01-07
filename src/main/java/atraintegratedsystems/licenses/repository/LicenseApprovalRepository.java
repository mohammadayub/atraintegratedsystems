package atraintegratedsystems.licenses.repository;

import atraintegratedsystems.licenses.model.LicenseApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LicenseApprovalRepository extends JpaRepository<LicenseApproval,Long> {


    @Query("SELECT COALESCE(MAX(l.id), 0) FROM LicenseApproval l")
    Long findMaxId();

    Optional<LicenseApproval> findByLicenseApplicantId(Long applicantId);
    @Query(value = "SELECT * FROM License_Approvals " +
            "WHERE approval_status = 'Approve' " +
            "AND (license_fee_mcit_payment_status != 'Paid' OR license_fee_mcit_payment_status IS NULL)",
            nativeQuery = true)
    List<LicenseApproval> findUnpaidApprovedLicenses();


    @Query(value = "SELECT * FROM License_Approvals " +
            "WHERE approval_status = 'Approve' " +
            "AND (administration_fee_payment_status != 'Paid' OR administration_fee_payment_status IS NULL)",
            nativeQuery = true)
    List<LicenseApproval> findUnpaidAdministrationFees();

    @Query(value = "SELECT * FROM License_Approvals " +
            "WHERE approval_status = 'Approve' " +
            "AND (database_maintainance_fee_payment_status != 'Paid' OR database_maintainance_fee_payment_status IS NULL)",
            nativeQuery = true)
    List<LicenseApproval> findUnpaidDatabaseFees();

    @Query(value = "SELECT * FROM License_Approvals " +
            "WHERE approval_status = 'Approve' " +
            "AND (guarantee_fee_payment_status != 'Paid' OR guarantee_fee_payment_status IS NULL)",
            nativeQuery = true)
    List<LicenseApproval> findUnpaidguaranteeFees();

    @Query(value = "SELECT * FROM license_approvals " +
            "WHERE approval_status = 'Approve' " +
            "AND license_fee_mcit_payment_status = 'Paid' " +
            "AND database_maintainance_fee_payment_status = 'Paid' " +
            "AND guarantee_fee_payment_status = 'Paid'",
            nativeQuery = true)
    List<LicenseApproval> findApprovedAndPaidLicenses();

    @Query(value = "SELECT * FROM License_Approvals WHERE approval_status = 'Reject' " ,nativeQuery = true)
    List<LicenseApproval>findAllRejection();









}
