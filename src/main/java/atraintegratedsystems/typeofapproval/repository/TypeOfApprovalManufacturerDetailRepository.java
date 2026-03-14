package atraintegratedsystems.typeofapproval.repository;

import atraintegratedsystems.typeofapproval.model.TypeOfApprovalApplicant;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalManufacturerDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeOfApprovalManufacturerDetailRepository extends JpaRepository<TypeOfApprovalManufacturerDetail,Long> {

    void deleteByApplicantId(Long applicantId);

    void deleteByApplicant(TypeOfApprovalApplicant applicant);

    List<TypeOfApprovalManufacturerDetail> findByApplicantId(Long applicantId);
}
