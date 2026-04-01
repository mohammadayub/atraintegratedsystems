package atraintegratedsystems.codes.repository;

import atraintegratedsystems.codes.dto.IspcExtensionViewDTO;
import atraintegratedsystems.codes.model.IspcExtensionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IspcExtensionDetailRepository extends JpaRepository<IspcExtensionDetail,Long> {

    @Query("SELECT new atraintegratedsystems.codes.dto.IspcExtensionViewDTO(" +
            "e.id, " +
            "c.ispcCodeName, " +
            "d.serialNumber, " +
            "d.signalingPoint, " +
            "d.companyName, " +
            "d.companyNameInDari, " +
            "d.enid, " +
            "d.location, " +
            "d.companyAddress, " +
            "d.responsiblePerson, " +
            "d.job, " +
            "d.mobile, " +
            "d.telephone, " +
            "d.email, " +
            "d.assigningDate, " +
            "d.expirationDate, " +
            "e.extensionPaymentStatus, " +
            "e.extensionStartDate, " +
            "e.extentionExpirationDate, " +
            "e.extendStatus ) " +
            "FROM IspcExtensionDetail e " +
            "JOIN e.ispcDetail d " +
            "JOIN d.ispcCode c " +
            "WHERE e.extensionPaymentStatus IS NULL " +
            "AND e.extendStatus = 'Extended'")
    List<IspcExtensionViewDTO> findAllExtensionDetails();

    // Bellow is For Paid Extension List
    @Query("SELECT new atraintegratedsystems.codes.dto.IspcExtensionViewDTO(" +
            "e.id, " +
            "c.ispcCodeName, " +
            "d.serialNumber, " +
            "d.signalingPoint, " +
            "d.companyName, " +
            "d.companyNameInDari, " +
            "d.enid, " +
            "d.location, " +
            "d.companyAddress, " +
            "d.responsiblePerson, " +
            "d.job, " +
            "d.mobile, " +
            "d.telephone, " +
            "d.email, " +
            "d.assigningDate, " +
            "d.expirationDate, " +
            "e.extensionPaymentStatus, " +
            "e.extensionStartDate, " +
            "e.extentionExpirationDate, " +
            "e.extendStatus ) " +
            "FROM IspcExtensionDetail e " +
            "JOIN e.ispcDetail d " +
            "JOIN d.ispcCode c " +
            "WHERE e.extensionPaymentStatus IS NULL " +
            "AND e.extendStatus = 'Extended'")
    List<IspcExtensionViewDTO> findAllPaidExtensionDetails();

    // bellow is for printing

    @Query("SELECT new atraintegratedsystems.codes.dto.IspcExtensionViewDTO(" +
            "e.id, " +
            "c.ispcCodeName, " +
            "d.serialNumber, " +
            "d.signalingPoint, " +
            "d.companyName, " +
            "d.companyNameInDari, " +
            "d.enid, " +
            "d.location, " +
            "d.companyAddress, " +
            "d.responsiblePerson, " +
            "d.job, " +
            "d.mobile, " +
            "d.telephone, " +
            "d.email, " +
            "d.assigningDate, " +
            "d.expirationDate, " +
            "e.extensionPaymentStatus, " +
            "e.extensionStartDate, " +
            "e.extentionExpirationDate, " +
            "e.extendStatus ) " +
            "FROM IspcExtensionDetail e " +
            "JOIN e.ispcDetail d " +
            "JOIN d.ispcCode c " +
            "WHERE e.id = :id")
    IspcExtensionViewDTO findByIdForPrint(Long id);
}