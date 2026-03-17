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
    findByExtendedFeeExtensionPaymentStatusIsNull();

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
            "ext.extendedFeeExtensionDate, " +
            "ext.extendedFeeExtensionExpirationDate, " +
            "ext.extendedFees, " +
            "ext.extendedFeeExtensionBankVoucherNo, " +
            "ext.extendedFeeExtensionEntryVoucherDate, " +
            "ext.extendedFeeExtensionBankVoucherSubmissionDate, " +
            "ext.extendedFeeExtensionPaymentStatus" +
            ") " +
            "FROM ShortCodeDetail cd " +
            "JOIN cd.shortCode sc " +
            "LEFT JOIN ShortCodeExtendedFeesExtension ext ON ext.shortCodeDetail.id = cd.id " +
            "WHERE cd.applicationFeesStatus = 'PAID'")
    List<ShortCodeExtensionViewDTO> findPaidShortExtension();
}
