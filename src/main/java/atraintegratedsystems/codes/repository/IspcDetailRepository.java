package atraintegratedsystems.codes.repository;
import atraintegratedsystems.codes.dto.IspcDetailDTO;
import atraintegratedsystems.codes.dto.RejectedIspcDetailDTO;
import atraintegratedsystems.codes.model.IspcCode;
import atraintegratedsystems.codes.model.IspcDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IspcDetailRepository extends JpaRepository<IspcDetail,Long> {

    @Query("SELECT d FROM IspcDetail d JOIN d.ispcCode c WHERE d.ispcCodeRejectionStatus IS NULL")
    List<IspcDetail> findAllActiveIspcDetails();

    @Query("SELECT new atraintegratedsystems.codes.dto.RejectedIspcDetailDTO(" +
            "d.id, c.ispcCodeName, d.companyName, d.serialNumber, d.mobile, d.telephone, d.email, " +
            "d.ispcNumber, d.signalingPoint, d.location, d.enid, d.responsiblePerson, " +
            "d.assigningDate, d.expirationDate, " +
            "d.ispcCodeRejectionStatus, d.ispcCodeRejectionDate) " +
            "FROM IspcDetail d JOIN d.ispcCode c " +
            "WHERE d.ispcCodeRejectionStatus = 'Reject'")
    List<RejectedIspcDetailDTO> findAllRecjectIspcDetails();

    @Query("SELECT d FROM IspcDetail d WHERE d.ispcCodeRejectionStatus <> 'Reject' OR d.ispcCodeRejectionStatus IS NULL")
    List<IspcDetail> findAllNotRejected();



    @Query(
            value = "SELECT " +
                    "d.id, " +
                    "d.company_name, " +
                    "d.enid, " +
                    "d.company_address, " +
                    "d.mobile, " +
                    "d.telephone, " +
                    "d.email, " +
                    "d.assigning_date, " +
                    "d.expiration_date, " +
                    "c.ispc_code_name " +
                    "FROM ispc_detail d " +
                    "INNER JOIN ispc_code c " +
                    "ON d.ispc_code_id = c.id " +
                    "WHERE d.registration_fees_payment_status IS NULL " +
                    "AND d.ispc_code_rejection_status IS NULL",
            nativeQuery = true
    )
    List<Object[]> findPendingDetailsNative();


    // Application Fees Confirm Payment
    @Modifying
    @Transactional
    @Query(value = "UPDATE ispc_detail SET " +
            "registration_fees = ?2, " +
            "registration_fees_bank_voucher_no = ?3, " +
            "registration_fees_entry_voucher_date = ?4, " +
            "registration_fees_bank_voucher_submission_date = ?5, " +
            "registration_fees_payment_status = ?6 " +
            "WHERE id = ?1",
            nativeQuery = true)
    void updateRegistrationFees(
            Long id,
            double fees,
            String voucherNo,
            java.sql.Date entryDate,
            java.sql.Date submissionDate,
            String status
    );


    List<Object[]> findTariffById(Long id);


}
