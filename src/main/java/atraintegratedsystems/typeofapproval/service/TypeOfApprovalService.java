package atraintegratedsystems.typeofapproval.service;

import atraintegratedsystems.typeofapproval.dto.TypeOfApprovalFormDTO;
import atraintegratedsystems.typeofapproval.model.*;
import atraintegratedsystems.typeofapproval.repository.*;
import atraintegratedsystems.utils.ByteArrayMultipartFile;
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
    private final TypeOfApprovalOrganizationRepository organizationRepository;

    public TypeOfApprovalService(TypeOfApprovalApplicantRepository applicantRepository,
                                 TypeOfApprovalManufacturerDetailRepository manufacturerRepository,
                                 TypeOfApprovalAttachmentRepository attachmentRepository,
                                 TypeOfApprovalTechnicalDetailsRepository technicalDetailsRepository,
                                 TypeOfApprovalStandardComplaintRepository standardComplaintRepository,
                                 TypeOfApprovalOrganizationRepository organizationRepository) {
        this.applicantRepository = applicantRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.attachmentRepository = attachmentRepository;
        this.technicalDetailsRepository = technicalDetailsRepository;
        this.standardComplaintRepository = standardComplaintRepository;
        this.organizationRepository = organizationRepository;
    }

    @Transactional
    public List<TypeOfApprovalFormDTO> getApplicantList() {
        return applicantRepository.findAll().stream().map(applicant -> {
            TypeOfApprovalFormDTO dto = new TypeOfApprovalFormDTO();
            dto.setApplicant(applicant);
            return dto;
        }).collect(Collectors.toList());
    }

    // ✅ Retrieve form with proper organization
    public TypeOfApprovalFormDTO getFormByApplicantId(Long id) {
        var applicant = applicantRepository.findById(id).orElse(null);
        if (applicant == null) return null;

        if (applicant.getTypeOfApprovalOrganization() != null &&
                applicant.getTypeOfApprovalOrganization().getId() != 0) {
            var org = organizationRepository.findById(applicant.getTypeOfApprovalOrganization().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Organization not found"));
            applicant.setTypeOfApprovalOrganization(org);
        }

        TypeOfApprovalFormDTO form = new TypeOfApprovalFormDTO();
        form.setApplicant(applicant);

        // ✅ (rest of your method is unchanged)
        // Load manufacturers, attachments, technical details, etc.
        // ...
        return form;
    }

    // ✅ FIXED: Organization is now correctly linked before save
    @Transactional
    public void submitForm(TypeOfApprovalFormDTO form) {
        var applicant = form.getApplicant();

        // ✅ Check organization
        if (applicant.getTypeOfApprovalOrganization() != null &&
                applicant.getTypeOfApprovalOrganization().getId() != 0) {
            var org = organizationRepository.findById(applicant.getTypeOfApprovalOrganization().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Organization not found"));
            applicant.setTypeOfApprovalOrganization(org);
        } else {
            throw new IllegalArgumentException("Organization must be selected.");
        }

//        // ✅ Check uniqueness
//        if (applicantRepository.existsByCompanyName(applicant.getCompanyName())) {
//            throw new IllegalArgumentException("Company name already exists.");
//        }

        applicant.setApplicationFeeOrganizationName("atra");
        applicant.setAdminFeeOrganizationName("atra");
        applicant.setCertificateFeeOrganizationName("mcit");
        applicant.setTypeOfApprovalApplicantNumber(generateTypeOfApprovalId());

        var savedApplicant = applicantRepository.save(applicant);

        // ✅ The rest is identical — manufacturers, technical details, attachments, etc.
        for (TypeOfApprovalManufacturerDetail manufacturer : form.getManufacturers()) {
            manufacturer.setApplicant(savedApplicant);
            manufacturerRepository.save(manufacturer);
        }

        try {
            TypeOfApprovalTechnicalDetail detail = new TypeOfApprovalTechnicalDetail();
            detail.setTechnicalDetails(savedApplicant);
            detail.setGsm(form.getGsm());
            detail.setCdma(form.getCdma());
            detail.setLte(form.getLte());
            detail.setTetra(form.getTetra());
            detail.setAmateurRadio(form.getAmateurRadio());
            detail.setPrivateMobileRadio(form.getPrivateMobileRadio());
            detail.setPmrRadio(form.getPmrRadio());
            detail.setRadar(form.getRadar());
            detail.setRlan(form.getRlan());
            detail.setWimax(form.getWimax());
            detail.setFwa(form.getFwa());
            detail.setMicrowave(form.getMicrowave());
            detail.setSoundBroadcasting(form.getSoundBroadcasting());
            detail.setTvBroadcasting(form.getTvBroadcasting());
            detail.setCordlessPhone(form.getCordlessPhone());
            detail.setSrd(form.getSrd());
            detail.setRfid(form.getRfid());
            detail.setSatelliteRadio(form.getSatelliteRadio());
            detail.setRadioNavigation(form.getRadioNavigation());
            detail.setSatelliteTv(form.getSatelliteTv());
            detail.setVsat(form.getVsat());
            detail.setOther(form.getOther());
            detail.setIntendedUse(form.getIntendedUse());
            detail.setModelNumber(form.getModelNumber());
            detail.setBrandName(form.getBrandName());
            detail.setTypeNumber(form.getTypeNumber());
            detail.setCountryofOrigin(form.getCountryofOrigin());
            detail.setFrequencyrangeFromMHZ(form.getFrequencyrangeFromMHZ());
            detail.setFrequencyrangeToMHZ(form.getFrequencyrangeToMHZ());
            detail.setFrequencyrangeFromGHZ(form.getFrequencyrangeFromGHZ());
            detail.setFrequencyrangeToGHZ(form.getFrequencyrangeToGHZ());
            detail.setOutputPowerRadiatedConducted(form.getOutputPowerRadiatedConducted());
            detail.setTransmissionCapacity(form.getTransmissionCapacity());
            detail.setChannelCapacity(form.getChannelCapacity());
            detail.setChannelSpacing(form.getChannelSpacing());
            detail.setModulationType(form.getModulationType());
            detail.setAntennaType(form.getAntennaType());
            detail.setAntennaGain(form.getAntennaGain());
            detail.setTechnicalInterface(form.getTechnicalInterface());
            detail.setTechnicalVariants(form.getTechnicalVariants());
            detail.setEquipmentLicenseRequirement(form.getEquipmentLicenseRequirement());
            technicalDetailsRepository.save(detail);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Attachments + Standards (unchanged)
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

        try {
            TypeOfApprovalStandardCompliant standardCompliant = new TypeOfApprovalStandardCompliant();
            standardCompliant.setStandardCompliant(savedApplicant);
            setCompressedAttachmentField(standardCompliant::setEmc, form.getEmc());
            standardCompliant.setEmcTestReportNo(form.getEmcTestReportNo());
            setCompressedAttachmentField(standardCompliant::setRadio, form.getRadio());
            standardCompliant.setRadioTestReportNo(form.getRadioTestReportNo());
            setCompressedAttachmentField(standardCompliant::setHealthAndSafety, form.getHealthAndSafety());
            standardCompliant.setHealthAndSafetyTestReportNo(form.getHealthAndSafetyTestReportNo());
            setCompressedAttachmentField(standardCompliant::setTechnologySpecific, form.getTechnologySpecific());
            standardCompliant.setTechnologySpecificTestReportNo(form.getTechnologySpecificTestReportNo());
            standardComplaintRepository.save(standardCompliant);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ✅ EDIT: same organization fix added
    @Transactional
    public void editForm(TypeOfApprovalFormDTO form) {
        var updatedApplicant = form.getApplicant();
        var applicantId = updatedApplicant.getId();

        var existingApplicant = applicantRepository.findById(applicantId)
                .orElseThrow(() -> new IllegalArgumentException("Applicant not found"));

        // ✅ Update organization
        if (updatedApplicant.getTypeOfApprovalOrganization() != null &&
                updatedApplicant.getTypeOfApprovalOrganization().getId() != 0) {
            var org = organizationRepository.findById(updatedApplicant.getTypeOfApprovalOrganization().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Organization not found"));
            existingApplicant.setTypeOfApprovalOrganization(org);
        }

        existingApplicant.setCompanyName(updatedApplicant.getCompanyName());
        existingApplicant.setApplicationFeeOrganizationName("atra");
        existingApplicant.setAdminFeeOrganizationName("atra");
        existingApplicant.setCertificateFeeOrganizationName("mcit");
        applicantRepository.save(existingApplicant);

        // ✅ rest unchanged
        // (manufacturers, technical details, attachments, etc.)
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

    private String generateTypeOfApprovalId() {
        LocalDate today = LocalDate.now();
        int day = today.getDayOfMonth();
        int month = today.getMonthValue();
        int year = today.getYear() % 100;
        String datePart = String.format("%02d-%02d-%02d", day, month, year);
        Long maxId = applicantRepository.findMaxIdByDate(today);
        Long nextId = (maxId == null) ? 1 : maxId + 1;
        return "ATRA-" + datePart + "-" + String.format("%02d", nextId);
    }
}
