package atraintegratedsystems.typeofapproval.service;

import atraintegratedsystems.typeofapproval.dto.TypeOfApprovalFormDTO;
import atraintegratedsystems.typeofapproval.model.*;
import atraintegratedsystems.typeofapproval.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
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
    public List<TypeOfApprovalFormDTO> getApplicantList() {
        return applicantRepository.findAll().stream().map(applicant -> {
            TypeOfApprovalFormDTO dto = new TypeOfApprovalFormDTO();
            dto.setApplicant(applicant);
            return dto;
        }).collect(Collectors.toList());
    }


    public TypeOfApprovalFormDTO getFormByApplicantId(Long id) {
        var applicant = applicantRepository.findById(id)
                .orElse(null);

        if (applicant == null) {
            return null;
        }

        TypeOfApprovalFormDTO form = new TypeOfApprovalFormDTO();
        form.setApplicant(applicant);

        // Load manufacturers
        var manufacturers = manufacturerRepository.findByApplicantId(id);
        form.setManufacturers(manufacturers);

        // Load technical details
        var techDetail = technicalDetailsRepository.findByTechnicalDetailsId(id);
        techDetail.ifPresent(detail -> {
            form.setGsm(detail.getGsm());
            form.setCdma(detail.getCdma());
            // Set other fields as needed...
        });

        // You can also pre-load file metadata if needed (optional)

        return form;
    }



























    @Transactional
    public void submitForm(TypeOfApprovalFormDTO form) {
        var applicant = form.getApplicant();

        // Check uniqueness
        if (applicantRepository.existsByCompanyName(applicant.getCompanyName())) {
            throw new IllegalArgumentException("Company name already exists.");
        }
        applicant.setApplicationFeeOrganizationName("atra");
        applicant.setAdminFeeOrganizationName("atra");
        applicant.setCertificateFeeOrganizationName("mcit");
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
            detail.setCdma(form.getCdma());
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

    // ✅ NEW METHOD: editForm
    @Transactional
    public void editForm(TypeOfApprovalFormDTO form) {
        var updatedApplicant = form.getApplicant();
        var applicantId = updatedApplicant.getId();

        var existingApplicant = applicantRepository.findById(applicantId)
                .orElseThrow(() -> new IllegalArgumentException("Applicant not found"));

        // Check uniqueness (excluding the current applicant)
        if (applicantRepository.existsByCompanyNameAndIdNot(updatedApplicant.getCompanyName(), applicantId)) {
            throw new IllegalArgumentException("Company name already exists.");
        }

        // Update applicant fields
        existingApplicant.setCompanyName(updatedApplicant.getCompanyName());
        existingApplicant.setApplicationFeeOrganizationName("atra");
        existingApplicant.setAdminFeeOrganizationName("atra");
        existingApplicant.setCertificateFeeOrganizationName("mcit");
        // Add more fields as needed...
        applicantRepository.save(existingApplicant);

        // Replace manufacturers
        manufacturerRepository.deleteByApplicantId(applicantId);
        for (TypeOfApprovalManufacturerDetail manufacturer : form.getManufacturers()) {
            manufacturer.setApplicant(existingApplicant);
            manufacturerRepository.save(manufacturer);
        }

        // Update or create technical detail
        var detail = technicalDetailsRepository.findByTechnicalDetailsId(applicantId)
                .orElse(new TypeOfApprovalTechnicalDetail());
        detail.setTechnicalDetails(existingApplicant);
        detail.setGsm(form.getGsm());
        // Set other properties...
        technicalDetailsRepository.save(detail);

        // Update or create attachments
        try {
            var attachment = attachmentRepository.findByApprovalApplicantId(applicantId)
                    .orElse(new TypeOfApprovalAttachment());
            attachment.setApprovalApplicant(existingApplicant);
            attachment.setEnteredBy("System");
            attachment.setEnteredDate(LocalDate.now());

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

        // Update or create standard compliance
        try {
            var standardCompliant = standardComplaintRepository.findByStandardCompliantId(applicantId)
                    .orElse(new TypeOfApprovalStandardCompliant());
            standardCompliant.setStandardCompliant(existingApplicant);

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

            if ("application/pdf".equalsIgnoreCase(contentType) ||
                    (filename != null && filename.toLowerCase().endsWith(".pdf"))) {

                byte[] compressedData = compressFile(file.getBytes());
                setter.accept(compressedData);
            } else {
                throw new IllegalArgumentException("Only PDF files are allowed.");
            }
        }
    }

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
















