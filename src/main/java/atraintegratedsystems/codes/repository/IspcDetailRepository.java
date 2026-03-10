package atraintegratedsystems.codes.repository;
import atraintegratedsystems.codes.dto.IspcDetailDTO;
import atraintegratedsystems.codes.dto.RejectedIspcDetailDTO;
import atraintegratedsystems.codes.model.IspcCode;
import atraintegratedsystems.codes.model.IspcDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
}
