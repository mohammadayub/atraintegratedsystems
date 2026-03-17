package atraintegratedsystems.codes.repository;

import atraintegratedsystems.codes.dto.ShortCodeExtensionViewDTO;
import atraintegratedsystems.codes.model.ShortCodeExtendedFeesExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShortCodeExtendedFeesExtensionRepository extends JpaRepository<ShortCodeExtendedFeesExtension,Long> {

    List<ShortCodeExtendedFeesExtension>
    findByApplicationFeeExtensionPaymentStatusIsNull();

    @Query("SELECT new atraintegratedsystems.codes.dto.ShortCodeExtensionViewDTO(" +
            "cd.id, " +
            "sc.shortCodeName, " +
            "cd.codeStatus, " +
            "cd.sourceUsed, " +
            "cd.location, " +
            "cd.categoryType, " +
            "cd.category, " +
            "cd.chanel, " +
            "cd.emailOfResponsiblePerson, " +
            "ext.applicationFeeExtensionDate, " +
            "ext.applicationFeeExtensionExpirationDate, " +
            "ext.applicationFeeExtendedFees, " +
            "ext.applicationFeeExtensionBankVoucherNo, " +
            "ext.applicationFeeExtensionEntryVoucherDate, " +
            "ext.applicationFeeExtensionBankVoucherSubmissionDate, " +
            "ext.applicationFeeExtensionPaymentStatus" +
            ") " +
            "FROM ShortCodeDetail cd " +
            "JOIN cd.shortCode sc " +
            "LEFT JOIN ShortCodeApplicationFeesExtension ext ON ext.shortCodeDetail.id = cd.id " +
            "WHERE cd.applicationFeesStatus = 'PAID'")
    List<ShortCodeExtensionViewDTO> findPaidShortExtension();
}
