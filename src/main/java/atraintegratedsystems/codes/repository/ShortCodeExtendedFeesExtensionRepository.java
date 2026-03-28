package atraintegratedsystems.codes.repository;

import atraintegratedsystems.codes.dto.ShortCodeExtensionViewDTO;
import atraintegratedsystems.codes.model.ShortCodeExtendedFeesExtension;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShortCodeExtendedFeesExtensionRepository
        extends JpaRepository<ShortCodeExtendedFeesExtension, Long> {
    List<ShortCodeExtendedFeesExtension>
    findByExtendedFeeExtensionPaymentStatusIsNull();

    @Query("SELECT new atraintegratedsystems.codes.dto.ShortCodeExtensionViewDTO(" +
            "cd.id, sc.shortCodeName, cd.codeStatus, cd.sourceUsed, cd.location, " +
            "cd.categoryType, cd.category, cd.chanel, cd.emailOfResponsiblePerson, " +

            "cd.serialNumber, cd.releaseShortCode, cd.uniqueNameOfSignalingPoint, cd.licenseApplicant.id, " +
            "cd.sourceUsedInDari, cd.address, cd.services, cd.backLongNumber, " +
            "cd.nameOfResponsiblePerson, cd.idCardNumberOfResponsiblePerson, " +
            "cd.mobileNumberOfResponsiblePerson, cd.phoneNumberOfResponsiblePerson, " +
            "cd.job, cd.assigningDate, cd.expirationDate, cd.applicationFees, cd.royaltyFees, " +

            "ext.extendedFeeExtensionDate, ext.extendedFeeExtensionExpirationDate, " +
            "ext.extendedFees, ext.extendedFeeExtensionBankVoucherNo, " +
            "ext.extendedFeeExtensionEntryVoucherDate, ext.extendedFeeExtensionBankVoucherSubmissionDate, " +
            "ext.extendedFeeExtensionPaymentStatus) " +

            "FROM ShortCodeDetail cd " +
            "JOIN cd.shortCode sc " +
            "LEFT JOIN ShortCodeExtendedFeesExtension ext " +
            "ON ext.shortCodeDetail.id = cd.id AND ext.extendedFeeExtensionPaymentStatus = 'PAID' " +
            "WHERE cd.applicationFeesStatus = 'PAID'")
    List<ShortCodeExtensionViewDTO> findPaidShortExtension();


    @Query("SELECT new atraintegratedsystems.codes.dto.ShortCodeExtensionViewDTO(" +
            "cd.id, sc.shortCodeName, cd.codeStatus, cd.sourceUsed, cd.location, " +
            "cd.categoryType, cd.category, cd.chanel, cd.emailOfResponsiblePerson, " +

            "cd.serialNumber, cd.releaseShortCode, cd.uniqueNameOfSignalingPoint, cd.licenseApplicant.id, " +
            "cd.sourceUsedInDari, cd.address, cd.services, cd.backLongNumber, " +
            "cd.nameOfResponsiblePerson, cd.idCardNumberOfResponsiblePerson, " +
            "cd.mobileNumberOfResponsiblePerson, cd.phoneNumberOfResponsiblePerson, " +
            "cd.job, cd.assigningDate, cd.expirationDate, cd.applicationFees, cd.royaltyFees, " +

            "ext.extendedFeeExtensionDate, ext.extendedFeeExtensionExpirationDate, " +
            "ext.extendedFees, ext.extendedFeeExtensionBankVoucherNo, " +
            "ext.extendedFeeExtensionEntryVoucherDate, ext.extendedFeeExtensionBankVoucherSubmissionDate, " +
            "ext.extendedFeeExtensionPaymentStatus) " +

            "FROM ShortCodeDetail cd " +
            "JOIN cd.shortCode sc " +
            "JOIN ShortCodeExtendedFeesExtension ext ON ext.shortCodeDetail.id = cd.id " +
            "WHERE cd.id = :id AND ext.extendedFeeExtensionPaymentStatus = 'PAID' " +
            "ORDER BY ext.extendedFeeExtensionDate DESC")
    List<ShortCodeExtensionViewDTO> findPaidShortExtensionById(@Param("id") Long id);
}