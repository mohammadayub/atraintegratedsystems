package atraintegratedsystems.typeofapproval.service;

import atraintegratedsystems.typeofapproval.model.TypeOfApprovalAttachment;
import atraintegratedsystems.typeofapproval.repository.TypeOfApprovalAttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TypeOfApprovalAttachmentService {

    @Autowired
    private TypeOfApprovalAttachmentRepository attachmentRepository;

    public Optional<TypeOfApprovalAttachment> findById(Long id) {
        return attachmentRepository.findById(id); // ensures open session
    }
}
