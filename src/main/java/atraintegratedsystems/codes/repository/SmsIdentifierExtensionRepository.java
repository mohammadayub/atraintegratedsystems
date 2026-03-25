package atraintegratedsystems.codes.repository;

import atraintegratedsystems.codes.dto.SmsIdentifierExtensionViewDTO;
import atraintegratedsystems.codes.model.SmsIdentifierExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SmsIdentifierExtensionRepository extends JpaRepository<SmsIdentifierExtension, Long> {

    // ✅ Get all PAID extensions (like your example)
    @Query("SELECT new atraintegratedsystems.codes.dto.SmsIdentifierExtensionViewDTO(" +
            "d.id, " +
            "c.smsIdentifierCodeName, " +
            "d.companyName, " +
            "d.mobile, " +
            "d.email, " +
            "d.assigningDate, " +
            "d.expirationDate, " +
            "e.extensionStartDate, " +
            "e.extentionExpirationDate, " +
            "e.extendedFees, " +
            "e.extensionBankVoucherNo, " +
            "e.extensionEnteryVoucherDate, " +
            "e.extensionBankVoucherSubmissionDate, " +
            "e.extensionPaymentStatus" +
            ") " +
            "FROM SmsIdentifierDetail d " +
            "JOIN d.smsIdentifierCode c " +
            "LEFT JOIN SmsIdentifierExtension e " +
            "ON e.smsIdentifierDetail.id = d.id " +
            "WHERE e.extensionPaymentStatus = 'PAID'")
    List<SmsIdentifierExtensionViewDTO> findAllPaidExtensions();


    // ✅ Get ALL data (even if extension not exists)
    @Query("SELECT new atraintegratedsystems.codes.dto.SmsIdentifierExtensionViewDTO(" +
            "d.id, c.smsIdentifierCodeName, d.companyName, d.mobile, d.email, " +
            "d.assigningDate, d.expirationDate, " +
            "e.extensionStartDate, e.extentionExpirationDate, e.extendedFees, " +
            "e.extensionBankVoucherNo, e.extensionEnteryVoucherDate, " +
            "e.extensionBankVoucherSubmissionDate, e.extensionPaymentStatus" +
            ") " +
            "FROM SmsIdentifierDetail d " +
            "JOIN d.smsIdentifierCode c " +
            "LEFT JOIN SmsIdentifierExtension e ON e.smsIdentifierDetail.id = d.id")
    List<SmsIdentifierExtensionViewDTO> findAllWithExtensions();

    // Bellow is For Printing

    @Query("SELECT new atraintegratedsystems.codes.dto.SmsIdentifierExtensionViewDTO(" +
            "d.id, " +
            "c.smsIdentifierCodeName, " +
            "d.companyName, " +
            "d.mobile, " +
            "d.email, " +
            "d.assigningDate, " +
            "d.expirationDate, " +
            "e.extensionStartDate, " +
            "e.extentionExpirationDate, " +
            "e.extendedFees, " +
            "e.extensionBankVoucherNo, " +
            "e.extensionEnteryVoucherDate, " +
            "e.extensionBankVoucherSubmissionDate, " +
            "e.extensionPaymentStatus" +
            ") " +
            "FROM SmsIdentifierDetail d " +
            "JOIN d.smsIdentifierCode c " +
            "JOIN d.extensions e " +
            "WHERE d.id = :id")
    SmsIdentifierExtensionViewDTO findByDetailId(@Param("id") Long id);

}