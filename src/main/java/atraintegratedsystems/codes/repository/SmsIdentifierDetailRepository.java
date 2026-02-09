package atraintegratedsystems.codes.repository;
import atraintegratedsystems.codes.dto.SmsIdentifierDetailDTO;
import atraintegratedsystems.codes.dto.SmsIdentifierFinanceExtensionDTO;
import atraintegratedsystems.codes.model.SmsIdentifierDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SmsIdentifierDetailRepository extends JpaRepository<SmsIdentifierDetail,Long> {

    @Query(
            value = "SELECT " +
                    "d.id, " +
                    "d.company_name, " +
                    "d.enid, " +
                    "d.company_address, " +
                    "d.mobile, " +
                    "d.telephone, " +
                    "d.email, " +
                    "d.channel, " +
                    "d.service_type, " +
                    "d.mnos_company_host, " +
                    "d.code_category, " +
                    "d.assigning_date, " +
                    "d.expiration_date, " +
                    "c.sms_identifier_code_name " +
                    "FROM sms_identifier_detail d " +
                    "INNER JOIN sms_identifier_code c " +
                    "ON d.sms_identifier_code_id = c.id " +
                    "WHERE d.application_fees_payment_status IS NULL " +
                    "AND d.short_code_rejection_status IS NULL",
            nativeQuery = true
    )
    List<Object[]> findPendingDetailsNative();

    // Application Fees Confirm Payment
    @Modifying
    @Transactional
    @Query(
            value = "UPDATE sms_identifier_detail SET " +
                    "application_fees = ?2, " +
                    "application_fees_bank_voucher_no = ?3, " +
                    "application_fees_entery_voucher_date = ?4, " +
                    "application_fees_bank_voucher_submission_date = ?5, " +
                    "application_fees_payment_status = ?6 " +
                    "WHERE id = ?1",
            nativeQuery = true
    )
    void updateApplicationFees(
            Long id,
            double applicationFees,
            String voucherNo,
            java.sql.Date entryDate,
            java.sql.Date submissionDate,
            String paymentStatus
    );

    // Tariff Section

    @Query(
            value = "SELECT " +
                    "d.id, " +
                    "d.company_name, " +
                    "d.enid, " +
                    "d.company_address, " +
                    "d.mobile, " +
                    "d.email, " +
                    "d.channel, " +
                    "d.service_type, " +
                    "d.code_category, " +
                    "d.application_fees, " +
                    "d.application_fees_payment_status, " +
                    "c.sms_identifier_code_name " +
                    "FROM sms_identifier_detail d " +
                    "INNER JOIN sms_identifier_code c " +
                    "ON d.sms_identifier_code_id = c.id " +
                    "WHERE d.id = ?1",
            nativeQuery = true
    )
    List<Object[]> findTariffById(Long id);

    // Bellow is For Royalty Fees Payments

    @Query(
            value = "SELECT " +
                    "d.id, " +
                    "d.company_name, " +
                    "d.enid, " +
                    "d.company_address, " +
                    "d.mobile, " +
                    "d.telephone, " +
                    "d.email, " +
                    "d.channel, " +
                    "d.service_type, " +
                    "d.mnos_company_host, " +
                    "d.code_category, " +
                    "d.assigning_date, " +
                    "d.expiration_date, " +
                    "c.sms_identifier_code_name " +
                    "FROM sms_identifier_detail d " +
                    "INNER JOIN sms_identifier_code c " +
                    "ON d.sms_identifier_code_id = c.id " +
                    "WHERE d.royalty_fees_payment_status IS NULL " +
                    "AND d.short_code_rejection_status IS NULL",
            nativeQuery = true
    )
    List<Object[]> findUnPaidRoyaltyFees();

    // Royalty Fees Confirm Payment
    @Modifying
    @Transactional
    @Query(
            value = "UPDATE sms_identifier_detail SET " +
                    "royalty_fees = ?2, " +
                    "royalty_fees_bank_voucher_no = ?3, " +
                    "royalty_fees_entery_voucher_date = ?4, " +
                    "royalty_fees_bank_voucher_submission_date = ?5, " +
                    "royalty_fees_payment_status = ?6 " +
                    "WHERE id = ?1",
            nativeQuery = true
    )
    void updateRoyaltyFees(
            Long id,
            double royaltyFees,
            String voucherNo,
            java.sql.Date entryDate,
            java.sql.Date submissionDate,
            String paymentStatus
    );

    // Royalty Fee Tariff Section
    @Query(
            value = "SELECT " +
                    "d.id, " +
                    "d.company_name, " +
                    "d.enid, " +
                    "d.company_address, " +
                    "d.mobile, " +
                    "d.email, " +
                    "d.channel, " +
                    "d.service_type, " +
                    "d.code_category, " +
                    "d.application_fees, " +
                    "d.application_fees_payment_status, " +
                    "c.sms_identifier_code_name " +
                    "FROM sms_identifier_detail d " +
                    "INNER JOIN sms_identifier_code c " +
                    "ON d.sms_identifier_code_id = c.id " +
                    "WHERE d.id = ?1",
            nativeQuery = true
    )
    List<Object[]> findRoyaltyTariff(Long id);

    // SmsIdentifier Code Rejection

    @Query(
            "SELECT new atraintegratedsystems.codes.dto.SmsIdentifierDetailDTO(" +
                    " d.id, " +
                    " d.companyName, " +
                    " d.enid, " +
                    " d.companyAddress, " +
                    " d.mobile, " +
                    " d.telephone, " +
                    " d.email, " +
                    " d.channel, " +
                    " d.serviceType, " +
                    " d.mnosCompanyHost, " +
                    " d.codeCategory, " +
                    " d.assigningDate, " +
                    " d.expirationDate, " +
                    " d.applicationFees, " +
                    " d.applicationFeesBankVoucherNo, " +
                    " d.applicationFeesEnteryVoucherDate, " +
                    " d.applicationFeesBankVoucherSubmissionDate, " +
                    " d.applicationFeesPaymentStatus, " +
                    " d.royaltyFees, " +
                    " d.royaltyFeesBankVoucherNo, " +
                    " d.royaltyFeesEnteryVoucherDate, " +
                    " d.royaltyFeesBankVoucherSubmissionDate, " +
                    " d.royaltyFeesPaymentStatus, " +
                    " d.shortCodeRejectionStatus, " +
                    " d.shortCodeRejectionDate, " +
                    " c.id, " +
                    " c.smsIdentifierCodeName" +
                    ") " +
                    "FROM SmsIdentifierDetail d " +
                    "JOIN d.smsIdentifierCode c " +
                    "WHERE d.shortCodeRejectionStatus IS NULL"
    )
    List<SmsIdentifierDetailDTO> findAllWhereShortCodeNotRejected();
//
@Transactional
@Modifying
@Query(
        "UPDATE SmsIdentifierDetail d " +
                "SET d.shortCodeRejectionStatus = :status, " +
                "    d.shortCodeRejectionDate = :date " +
                "WHERE d.id = :id"
)
void rejectSmsIdentifier(
        Long id,
        String status,
        LocalDate date
);

    @Query(
            "SELECT new atraintegratedsystems.codes.dto.SmsIdentifierDetailDTO(" +
                    " d.id, " +
                    " d.companyName, " +
                    " d.enid, " +
                    " d.companyAddress, " +
                    " d.mobile, " +
                    " d.telephone, " +
                    " d.email, " +
                    " d.channel, " +
                    " d.serviceType, " +
                    " d.mnosCompanyHost, " +
                    " d.codeCategory, " +
                    " d.assigningDate, " +
                    " d.expirationDate, " +
                    " d.applicationFees, " +
                    " d.applicationFeesBankVoucherNo, " +
                    " d.applicationFeesEnteryVoucherDate, " +
                    " d.applicationFeesBankVoucherSubmissionDate, " +
                    " d.applicationFeesPaymentStatus, " +
                    " d.royaltyFees, " +
                    " d.royaltyFeesBankVoucherNo, " +
                    " d.royaltyFeesEnteryVoucherDate, " +
                    " d.royaltyFeesBankVoucherSubmissionDate, " +
                    " d.royaltyFeesPaymentStatus, " +
                    " d.shortCodeRejectionStatus, " +
                    " d.shortCodeRejectionDate, " +
                    " c.id, " +
                    " c.smsIdentifierCodeName" +
                    ") " +
                    "FROM SmsIdentifierDetail d " +
                    "JOIN d.smsIdentifierCode c " +
                    "WHERE d.shortCodeRejectionStatus IS NULL " +
                    "AND d.applicationFeesPaymentStatus IS NOT NULL " +
                    "AND d.royaltyFeesPaymentStatus IS NOT NULL"
    )
    List<SmsIdentifierDetailDTO> findSmsIdentifierCodeForExtension();


    // Bellow is For SmsIdentifierFinanceExtension
    @Query(
            "SELECT new atraintegratedsystems.codes.dto.SmsIdentifierFinanceExtensionDTO(" +
                    " e.id, " +                     // <<<<<< THIS IS THE REAL FIX
                    " c.smsIdentifierCodeName, " +
                    " d.companyName, " +
                    " d.enid, " +
                    " d.mobile, " +
                    " d.email, " +
                    " d.channel, " +
                    " d.serviceType, " +
                    " d.mnosCompanyHost, " +
                    " d.codeCategory, " +
                    " d.applicationFeesPaymentStatus, " +
                    " d.royaltyFeesPaymentStatus, " +
                    " e.extensionStartDate, " +
                    " e.extentionExpirationDate, " +
                    " e.extendStatus, " +
                    " e.extensionStartDate " +   // createdDate (better than assigningDate)
                    ") " +
                    "FROM SmsIdentifierDetail d " +
                    "JOIN d.smsIdentifierCode c " +
                    "JOIN d.extensions e " +
                    "WHERE d.applicationFeesPaymentStatus = 'PAID' " +
                    "AND d.royaltyFeesPaymentStatus = 'PAID' " +
                    "AND e.extendStatus = 'EXTENDED'"+
                    "AND e.extensionPaymentStatus IS NULL"
    )
    List<SmsIdentifierFinanceExtensionDTO> findFinanceExtensions();




}
