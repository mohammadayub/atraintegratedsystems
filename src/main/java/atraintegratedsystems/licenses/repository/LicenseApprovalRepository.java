package atraintegratedsystems.licenses.repository;

import atraintegratedsystems.licenses.model.LicenseApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LicenseApprovalRepository extends JpaRepository<LicenseApproval,Long> {

    @Query(value = "SELECT applicants.req_id AS reqId, applicants.req_date AS reqDate, applicants.company_license_name AS companyLicenseName, lp.name AS licenseTypeName, applicants.application_fees AS applicationFees, applicants.finance_type AS financeType, applicants.entry_voucher_date AS entryVoucherDate, applicants.bank_voucher AS bankVoucher, applicants.payment_status AS paymentStatus FROM license_applicants applicants LEFT OUTER JOIN license_approvals approvals ON applicants.id = approvals.license_applicant_id LEFT OUTER JOIN license_type lp ON applicants.license_type_id = lp.id WHERE approvals.approval_status IS NULL", nativeQuery = true)
    List<Object[]> findAllApplicantDetails();

}
