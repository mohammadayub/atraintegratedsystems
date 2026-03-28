package atraintegratedsystems.codes.repository;

import atraintegratedsystems.codes.dto.SmsIdentifierExtensionViewDTO;
import atraintegratedsystems.codes.model.SmsIdentifierExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SmsIdentifierExtensionRepository extends JpaRepository<SmsIdentifierExtension, Long> {

    @Query("SELECT new atraintegratedsystems.codes.dto.SmsIdentifierExtensionViewDTO(" +
            "d.id, " +
            "c.smsIdentifierCodeName, " +
            "d.serialNumber, " +
            "d.companyName, " +
            "d.companyNameInDari, " +
            "d.enid, " +
            "d.companyAddress, " +
            "d.responsiblePerson, " +
            "d.job, " +
            "d.mobile, " +
            "d.telephone, " +
            "d.email, " +
            "d.channel, " +
            "d.serviceType, " +
            "d.mnosCompanyHost, " +
            "'Golden', " +

            "d.assigningDate, '' , " +
            "d.expirationDate, '' , " +

            "d.applicationFees, " +
            "d.applicationFeesBankVoucherNo, " +
            "d.applicationFeesEnteryVoucherDate, '' , " +
            "d.applicationFeesBankVoucherSubmissionDate, '' , " +
            "d.applicationFeesPaymentStatus, " +

            "d.royaltyFees, " +
            "d.royaltyFeesBankVoucherNo, " +
            "d.royaltyFeesEnteryVoucherDate, '' , " +
            "d.royaltyFeesBankVoucherSubmissionDate, '' , " +
            "d.royaltyFeesPaymentStatus, " +

            "e.extensionStartDate, '' , " +
            "e.extentionExpirationDate, '' , " +
            "e.extendStatus, " +
            "e.extendDate, " +

            "e.extendedFees, " +
            "e.extensionBankVoucherNo, " +
            "e.extensionEnteryVoucherDate, '' , " +
            "e.extensionBankVoucherSubmissionDate, '' , " +
            "e.extensionPaymentStatus, " +
            "e.extensionEnteryDate, " +
            "e.extensionBy ) " +

            "FROM SmsIdentifierDetail d " +
            "JOIN d.smsIdentifierCode c " +
            "JOIN d.extensions e " +
            "WHERE e.extensionPaymentStatus = 'PAID'")
    List<SmsIdentifierExtensionViewDTO> findAllPaidExtensions();


    @Query("SELECT new atraintegratedsystems.codes.dto.SmsIdentifierExtensionViewDTO(" +
            "d.id, c.smsIdentifierCodeName, d.serialNumber, d.companyName, d.companyNameInDari, " +
            "d.enid, d.companyAddress, d.responsiblePerson, d.job, d.mobile, d.telephone, d.email, " +
            "d.channel, d.serviceType, d.mnosCompanyHost, 'Golden', " +

            "d.assigningDate, '' , d.expirationDate, '' , " +

            "d.applicationFees, d.applicationFeesBankVoucherNo, d.applicationFeesEnteryVoucherDate, '' , " +
            "d.applicationFeesBankVoucherSubmissionDate, '' , d.applicationFeesPaymentStatus, " +

            "d.royaltyFees, d.royaltyFeesBankVoucherNo, d.royaltyFeesEnteryVoucherDate, '' , " +
            "d.royaltyFeesBankVoucherSubmissionDate, '' , d.royaltyFeesPaymentStatus, " +

            "e.extensionStartDate, '' , e.extentionExpirationDate, '' , e.extendStatus, e.extendDate, " +

            "e.extendedFees, e.extensionBankVoucherNo, e.extensionEnteryVoucherDate, '' , " +
            "e.extensionBankVoucherSubmissionDate, '' , e.extensionPaymentStatus, e.extensionEnteryDate, e.extensionBy ) " +

            "FROM SmsIdentifierDetail d " +
            "JOIN d.smsIdentifierCode c " +
            "LEFT JOIN d.extensions e")
    List<SmsIdentifierExtensionViewDTO> findAllWithExtensions();


    @Query("SELECT new atraintegratedsystems.codes.dto.SmsIdentifierExtensionViewDTO(" +
            "d.id, c.smsIdentifierCodeName, d.serialNumber, d.companyName, d.companyNameInDari, " +
            "d.enid, d.companyAddress, d.responsiblePerson, d.job, d.mobile, d.telephone, d.email, " +
            "d.channel, d.serviceType, d.mnosCompanyHost, 'Golden', " +

            "d.assigningDate, '' , d.expirationDate, '' , " +

            "d.applicationFees, d.applicationFeesBankVoucherNo, d.applicationFeesEnteryVoucherDate, '' , " +
            "d.applicationFeesBankVoucherSubmissionDate, '' , d.applicationFeesPaymentStatus, " +

            "d.royaltyFees, d.royaltyFeesBankVoucherNo, d.royaltyFeesEnteryVoucherDate, '' , " +
            "d.royaltyFeesBankVoucherSubmissionDate, '' , d.royaltyFeesPaymentStatus, " +

            "e.extensionStartDate, '' , e.extentionExpirationDate, '' , e.extendStatus, e.extendDate, " +

            "e.extendedFees, e.extensionBankVoucherNo, e.extensionEnteryVoucherDate, '' , " +
            "e.extensionBankVoucherSubmissionDate, '' , e.extensionPaymentStatus, e.extensionEnteryDate, e.extensionBy ) " +

            "FROM SmsIdentifierDetail d " +
            "JOIN d.smsIdentifierCode c " +
            "JOIN d.extensions e " +
            "WHERE d.id = :id")
    SmsIdentifierExtensionViewDTO findByDetailId(@Param("id") Long id);
}