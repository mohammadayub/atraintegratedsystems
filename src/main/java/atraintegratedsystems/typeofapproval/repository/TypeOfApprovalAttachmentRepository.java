package atraintegratedsystems.typeofapproval.repository;

import atraintegratedsystems.typeofapproval.model.TypeOfApprovalAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.lang.model.element.TypeParameterElement;

public interface TypeOfApprovalAttachmentRepository extends JpaRepository<TypeOfApprovalAttachment,Long> {
}
