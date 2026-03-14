package atraintegratedsystems.typeofapproval.repository;

import atraintegratedsystems.typeofapproval.model.TypeOfApprovalApplicant;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.lang.model.element.TypeParameterElement;
import java.util.Optional;

public interface TypeOfApprovalAttachmentRepository extends JpaRepository<TypeOfApprovalAttachment,Long> {

    // In TypeOfApprovalAttachmentRepository
    Optional<TypeOfApprovalAttachment> findByApprovalApplicantId(Long applicantId);


    Optional<TypeOfApprovalAttachment> findByApprovalApplicant(TypeOfApprovalApplicant applicant);
}
