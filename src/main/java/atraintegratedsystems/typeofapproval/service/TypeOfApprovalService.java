package atraintegratedsystems.typeofapproval.service;

import atraintegratedsystems.typeofapproval.dto.TypeOfApprovalFormDTO;
import atraintegratedsystems.typeofapproval.model.*;
import atraintegratedsystems.typeofapproval.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.zip.GZIPOutputStream;

@Service
public class TypeOfApprovalService {

    private final TypeOfApprovalApplicantRepository applicantRepository;
    private final TypeOfApprovalManufacturerDetailRepository manufacturerRepository;
    private final TypeOfApprovalAttachmentRepository attachmentRepository;
    private final TypeOfApprovalTechnicalDetailsRepository technicalDetailsRepository;
    private final TypeOfApprovalStandardComplaintRepository standardComplaintRepository;

    public TypeOfApprovalService(TypeOfApprovalApplicantRepository applicantRepository,
                                 TypeOfApprovalManufacturerDetailRepository manufacturerRepository,
                                 TypeOfApprovalAttachmentRepository attachmentRepository,
                                 TypeOfApprovalTechnicalDetailsRepository technicalDetailsRepository,
                                 TypeOfApprovalStandardComplaintRepository standardComplaintRepository) {
        this.applicantRepository = applicantRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.attachmentRepository = attachmentRepository;
        this.technicalDetailsRepository = technicalDetailsRepository;
        this.standardComplaintRepository = standardComplaintRepository;
    }

    @Transactional
    public void submitForm(TypeOfApprovalFormDTO form) {
        var applicant = form.getApplicant();

        // Check uniqueness
        if (applicantRepository.existsByCompanyName(applicant.getCompanyName())) {
            throw new IllegalArgumentException("Company name already exists.");
        }

        var savedApplicant = applicantRepository.save(applicant);

        // Save manufacturers
        for (TypeOfApprovalManufacturerDetail manufacturer : form.getManufacturers()) {
            manufacturer.setApplicant(savedApplicant);
            manufacturerRepository.save(manufacturer);
        }

        // Save Technical Details
        try {
            TypeOfApprovalTechnicalDetail detail = new TypeOfApprovalTechnicalDetail();
            detail.setTechnicalDetails(savedApplicant);
            detail.setGsm(form.getGsm());
            // Set other properties...
            technicalDetailsRepository.save(detail);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Save attachments (with compression)
        try {
            TypeOfApprovalAttachment attachment = new TypeOfApprovalAttachment();
            attachment.setApprovalApplicant(savedApplicant);
            attachment.setEnteredBy("System");
            attachment.setEnteredDate(LocalDate.now());

            // Compress and save attachments
            setCompressedAttachmentField(attachment::setDeclarationOfConformity, form.getDeclarationOfConformity());
            setCompressedAttachmentField(attachment::setTechnicalOperationalDocOfTheRCE, form.getTechnicalOperationalDocOfTheRCE());
            setCompressedAttachmentField(attachment::setTestReportsOfAccreditedLaboratory, form.getTestReportsOfAccreditedLaboratory());
            setCompressedAttachmentField(attachment::setCircuitDiagramPCB, form.getCircuitDiagramPCB());
            setCompressedAttachmentField(attachment::setPhotographs, form.getPhotographs());
            setCompressedAttachmentField(attachment::setLabel, form.getLabel());
            setCompressedAttachmentField(attachment::setTestReportsIssuedByAccreditedTesting, form.getTestReportsIssuedByAccreditedTesting());

            attachmentRepository.save(attachment);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Save TypeOfApprovalStandardComplaint
        try {
            TypeOfApprovalStandardCompliant standardCompliant = new TypeOfApprovalStandardCompliant();
            standardCompliant.setStandardCompliant(savedApplicant);
            setCompressedAttachmentField(standardCompliant::setEmc, form.getEmc());
            setCompressedAttachmentField(standardCompliant::setRadio, form.getRadio());
            setCompressedAttachmentField(standardCompliant::setHealthAndSafety, form.getHealthAndSafety());
            setCompressedAttachmentField(standardCompliant::setTechnologySpecific, form.getTechnologySpecific());
            standardComplaintRepository.save(standardCompliant);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    private void setCompressedAttachmentField(ThrowingConsumer<byte[]> setter, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String contentType = file.getContentType();
            String filename = file.getOriginalFilename();

            // Validate by MIME type and/or file extension
            if ("application/pdf".equalsIgnoreCase(contentType) ||
                    (filename != null && filename.toLowerCase().endsWith(".pdf"))) {

                // Compress the file content before setting
                byte[] compressedData = compressFile(file.getBytes());
                setter.accept(compressedData);
            } else {
                throw new IllegalArgumentException("Only PDF files are allowed.");
            }
        }
    }

    // Compress the file using GZIP
    private byte[] compressFile(byte[] fileData) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
            gzipOutputStream.write(fileData);
            gzipOutputStream.finish();
            return byteArrayOutputStream.toByteArray();
        }
    }

    @FunctionalInterface
    private interface ThrowingConsumer<T> {
        void accept(T t) throws IOException;
    }
}
