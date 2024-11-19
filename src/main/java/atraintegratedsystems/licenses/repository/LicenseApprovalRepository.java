package atraintegratedsystems.licenses.repository;

import atraintegratedsystems.licenses.model.LicenseApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LicenseApprovalRepository extends JpaRepository<LicenseApproval,Long> {

    @Query(value = "SELECT applicants.id, applicants.req_id, lp.name AS licenseTypeName, applicants.validity, applicants.application_fees, applicants.company_license_name, applicants.entry_voucher_date, applicants.bank_voucher, applicants.payment_status, applicants.finance_type, approval.approval_id, approval.approval_date, approval.approval_status, approval.license_fees, approval.currency_type FROM license_applicants applicants LEFT OUTER JOIN license_approvals approval ON applicants.id = approval.license_applicant_id LEFT OUTER JOIN license_type lp ON lp.id = applicants.license_type_id", nativeQuery = true)
    List<Object[]> findApplicantAndApprovalDetails();

    @Query(value = "SELECT applicants.id, applicants.req_id, lp.name AS licenseTypeName, applicants.validity, applicants.application_fees, applicants.company_license_name, applicants.entry_voucher_date, applicants.bank_voucher, applicants.payment_status, applicants.finance_type, approval.approval_id, approval.approval_date, approval.approval_status, approval.license_fees, approval.currency_type " +
            "FROM license_applicants applicants " +
            "LEFT OUTER JOIN license_approvals approval ON applicants.id = approval.license_applicant_id " +
            "LEFT OUTER JOIN license_type lp ON lp.id = applicants.license_type_id " +
            "WHERE applicants.id = :applicantId", nativeQuery = true)
    Object[] findApplicantApprovalDetailsById(@Param("applicantId") Long applicantId);

    Optional<LicenseApproval> findByLicenseApplicantId(Long applicantId);

}
