package atraintegratedsystems.typeofapproval.repository;

import atraintegratedsystems.typeofapproval.model.TypeOfApprovalManufacturerDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeOfApprovalManufacturerDetailRepository extends JpaRepository<TypeOfApprovalManufacturerDetail,Long> {

    void deleteByApplicantId(Long applicantId);

    List<TypeOfApprovalManufacturerDetail> findByApplicantId(Long applicantId);
}
