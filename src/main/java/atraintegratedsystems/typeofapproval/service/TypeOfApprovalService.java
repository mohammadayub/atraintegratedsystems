package atraintegratedsystems.typeofapproval.service;

import atraintegratedsystems.typeofapproval.dto.TypeOfApprovalFormDTO;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalAttachment;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalManufacturerDetail;
import atraintegratedsystems.typeofapproval.repository.TypeOfApprovalApplicantRepository;
import atraintegratedsystems.typeofapproval.repository.TypeOfApprovalAttachmentRepository;
import atraintegratedsystems.typeofapproval.repository.TypeOfApprovalManufacturerDetailRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@Service
public class TypeOfApprovalService {

    private final TypeOfApprovalApplicantRepository applicantRepository;
    private final TypeOfApprovalManufacturerDetailRepository manufacturerRepository;
    private final TypeOfApprovalAttachmentRepository attachmentRepository;

    public TypeOfApprovalService(TypeOfApprovalApplicantRepository applicantRepository,
                                 TypeOfApprovalManufacturerDetailRepository manufacturerRepository,
                                 TypeOfApprovalAttachmentRepository attachmentRepository) {
        this.applicantRepository = applicantRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.attachmentRepository = attachmentRepository;
    }

    @Transactional
    public void submitForm(TypeOfApprovalFormDTO form) {
        var applicant = form.getApplicant();

        // Check uniqueness
        if (applicantRepository.existsByCompanyName(applicant.getCompanyName())) {
            throw new IllegalArgumentException("Company name already exists.");
        }

        var savedApplicant = applicantRepository.save(applicant);

        for (TypeOfApprovalManufacturerDetail manufacturer : form.getManufacturers()) {
            manufacturer.setApplicant(savedApplicant);
            manufacturerRepository.save(manufacturer);
        }

        try {
            TypeOfApprovalAttachment attachment = new TypeOfApprovalAttachment();
            attachment.setApprovalApplicant(savedApplicant);
            attachment.setEnteredBy("System");
            attachment.setEnteredDate(LocalDate.now());

            setAttachmentField(attachment::setDeclarationOfConformity, form.getDeclarationOfConformity());
            setAttachmentField(attachment::setTechnicalOperationalDocOfTheRCE, form.getTechnicalOperationalDocOfTheRCE());
            setAttachmentField(attachment::setTestReportsOfAccreditedLaboratory, form.getTestReportsOfAccreditedLaboratory());
            setAttachmentField(attachment::setCircuitDiagramPCB, form.getCircuitDiagramPCB());
            setAttachmentField(attachment::setPhotographs, form.getPhotographs());
            setAttachmentField(attachment::setLabel, form.getLabel());
            setAttachmentField(attachment::setTestReportsIssuedByAccreditedTesting, form.getTestReportsIssuedByAccreditedTesting());

            attachmentRepository.save(attachment);
        } catch (IOException e) {
            e.printStackTrace(); // Use logging
        }
    }


    @Transactional
    private void setAttachmentField(ThrowingConsumer<byte[]> setter, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String contentType = file.getContentType();
            String filename = file.getOriginalFilename();

            // Validate by MIME type and/or file extension
            if ("application/pdf".equalsIgnoreCase(contentType) ||
                    (filename != null && filename.toLowerCase().endsWith(".pdf"))) {
                setter.accept(file.getBytes());
            } else {
                throw new IllegalArgumentException("Only PDF files are allowed.");
            }
        }
    }


    @FunctionalInterface
    private interface ThrowingConsumer<T> {
        void accept(T t) throws IOException;
    }
}

