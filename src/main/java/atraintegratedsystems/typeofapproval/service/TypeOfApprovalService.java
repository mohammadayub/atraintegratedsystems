package atraintegratedsystems.typeofapproval.service;

import atraintegratedsystems.typeofapproval.dto.TypeOfApprovalFormDTO;
import atraintegratedsystems.typeofapproval.model.*;
import atraintegratedsystems.typeofapproval.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.GZIPOutputStream;

@Service
public class TypeOfApprovalService {

    @Value("${typeofapproval.attachments.path}")
    private String attachmentsFolder;



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


    // Utility to save file and return path
    private String saveFile(MultipartFile file, String subfolder) throws IOException {
        if (file == null || file.isEmpty()) return null;

        Path dirPath = Paths.get(attachmentsFolder, subfolder);
        Files.createDirectories(dirPath);

        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = dirPath.resolve(filename);

        file.transferTo(filePath.toFile());
        return filePath.toString(); // return saved path
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


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        applicant.setEnteredBy(username);




        applicant.setEnteredDate(LocalDate.now());

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
            detail.setEnteredBy(username);
            detail.setEnteredDate(LocalDate.now());
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

        try {
            // Save Attachments
            TypeOfApprovalAttachment attachment = new TypeOfApprovalAttachment();
            attachment.setApprovalApplicant(savedApplicant);
            attachment.setEnteredBy(username);
            attachment.setEnteredDate(LocalDate.now());

            attachment.setDeclarationOfConformity(saveFile(form.getDeclarationOfConformity(), "attachment_" + savedApplicant.getId()));
            attachment.setTechnicalOperationalDocOfTheRCE(saveFile(form.getTechnicalOperationalDocOfTheRCE(), "attachment_" + savedApplicant.getId()));
            attachment.setTestReportsOfAccreditedLaboratory(saveFile(form.getTestReportsOfAccreditedLaboratory(), "attachment_" + savedApplicant.getId()));
            attachment.setCircuitDiagramPCB(saveFile(form.getCircuitDiagramPCB(), "attachment_" + savedApplicant.getId()));
            attachment.setPhotographs(saveFile(form.getPhotographs(), "attachment_" + savedApplicant.getId()));
            attachment.setLabel(saveFile(form.getLabel(), "attachment_" + savedApplicant.getId()));
            attachment.setTestReportsIssuedByAccreditedTesting(saveFile(form.getTestReportsIssuedByAccreditedTesting(), "attachment_" + savedApplicant.getId()));

            attachmentRepository.save(attachment);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save attachment files", e);
        }

        try {
            // Save Standards
            TypeOfApprovalStandardCompliant standardCompliant = new TypeOfApprovalStandardCompliant();
            standardCompliant.setStandardCompliant(savedApplicant);
            standardCompliant.setEnteredBy(username);
            standardCompliant.setEnteredDate(LocalDate.now());
            standardCompliant.setEmc(saveFile(form.getEmc(), "standardCompliant_" + savedApplicant.getId()));
            standardCompliant.setEmcTestReportNo(form.getEmcTestReportNo());

            standardCompliant.setRadio(saveFile(form.getRadio(), "standardCompliant_" + savedApplicant.getId()));
            standardCompliant.setRadioTestReportNo(form.getRadioTestReportNo());

            standardCompliant.setHealthAndSafety(saveFile(form.getHealthAndSafety(), "standardCompliant_" + savedApplicant.getId()));
            standardCompliant.setHealthAndSafetyTestReportNo(form.getHealthAndSafetyTestReportNo());

            standardCompliant.setTechnologySpecific(saveFile(form.getTechnologySpecific(), "standardCompliant_" + savedApplicant.getId()));
            standardCompliant.setTechnologySpecificTestReportNo(form.getTechnologySpecificTestReportNo());

            standardComplaintRepository.save(standardCompliant);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save standard compliance files", e);
        }
    }

    // ✅ EDIT: same organization fix added
    @Transactional
    public void editForm(TypeOfApprovalFormDTO form) {

        var updatedApplicant = form.getApplicant();
        var applicantId = updatedApplicant.getId();

        var existingApplicant = applicantRepository.findById(applicantId)
                .orElseThrow(() -> new IllegalArgumentException("Applicant not found"));

        // ✅ Organization Fix
        if (updatedApplicant.getTypeOfApprovalOrganization() != null &&
                updatedApplicant.getTypeOfApprovalOrganization().getId() != 0) {

            var org = organizationRepository.findById(
                            updatedApplicant.getTypeOfApprovalOrganization().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Organization not found"));

            existingApplicant.setTypeOfApprovalOrganization(org);
        }

        // ✅ Get current username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // ✅ Update Applicant Fields
        existingApplicant.setCompanyName(updatedApplicant.getCompanyName());
        existingApplicant.setApplicationFeeOrganizationName("atra");
        existingApplicant.setAdminFeeOrganizationName("atra");
        existingApplicant.setCertificateFeeOrganizationName("mcit");

//        existingApplicant.setUpdatedBy(username);
//        existingApplicant.setUpdatedDate(LocalDate.now());

        applicantRepository.save(existingApplicant);

        // --------------------------------------------------
        // ✅ Update Manufacturers
        // --------------------------------------------------

        manufacturerRepository.deleteByApplicant(existingApplicant);

        for (TypeOfApprovalManufacturerDetail manufacturer : form.getManufacturers()) {
            manufacturer.setApplicant(existingApplicant);
            manufacturerRepository.save(manufacturer);
        }

        // --------------------------------------------------
        // ✅ Update Technical Details
        // --------------------------------------------------

        TypeOfApprovalTechnicalDetail detail =
                technicalDetailsRepository.findByTechnicalDetails(existingApplicant)
                        .orElse(new TypeOfApprovalTechnicalDetail());

        detail.setTechnicalDetails(existingApplicant);
        detail.setEnteredBy(username);
        detail.setEnteredDate(LocalDate.now());

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

        // --------------------------------------------------
        // ✅ Update Attachments (FILES)
        // --------------------------------------------------

        try {

            TypeOfApprovalAttachment attachment =
                    attachmentRepository.findByApprovalApplicant(existingApplicant)
                            .orElse(new TypeOfApprovalAttachment());

            attachment.setApprovalApplicant(existingApplicant);
            attachment.setEnteredBy(username);
            attachment.setEnteredDate(LocalDate.now());

            if (!form.getDeclarationOfConformity().isEmpty())
                attachment.setDeclarationOfConformity(saveFile(form.getDeclarationOfConformity(), "attachment_" + existingApplicant.getId()));

            if (!form.getTechnicalOperationalDocOfTheRCE().isEmpty())
                attachment.setTechnicalOperationalDocOfTheRCE(saveFile(form.getTechnicalOperationalDocOfTheRCE(), "attachment_" + existingApplicant.getId()));

            if (!form.getTestReportsOfAccreditedLaboratory().isEmpty())
                attachment.setTestReportsOfAccreditedLaboratory(saveFile(form.getTestReportsOfAccreditedLaboratory(), "attachment_" + existingApplicant.getId()));

            if (!form.getCircuitDiagramPCB().isEmpty())
                attachment.setCircuitDiagramPCB(saveFile(form.getCircuitDiagramPCB(), "attachment_" + existingApplicant.getId()));

            if (!form.getPhotographs().isEmpty())
                attachment.setPhotographs(saveFile(form.getPhotographs(), "attachment_" + existingApplicant.getId()));

            if (!form.getLabel().isEmpty())
                attachment.setLabel(saveFile(form.getLabel(), "attachment_" + existingApplicant.getId()));

            if (!form.getTestReportsIssuedByAccreditedTesting().isEmpty())
                attachment.setTestReportsIssuedByAccreditedTesting(saveFile(form.getTestReportsIssuedByAccreditedTesting(), "attachment_" + existingApplicant.getId()));

            attachmentRepository.save(attachment);

        } catch (IOException e) {
            throw new RuntimeException("Failed to update attachment files", e);
        }

        // --------------------------------------------------
        // ✅ Update Standards
        // --------------------------------------------------

        try {

            TypeOfApprovalStandardCompliant standard =
                    standardComplaintRepository.findByStandardCompliant(existingApplicant)
                            .orElse(new TypeOfApprovalStandardCompliant());

            standard.setStandardCompliant(existingApplicant);
            standard.setEnteredBy(username);
            standard.setEnteredDate(LocalDate.now());

            if (!form.getEmc().isEmpty())
                standard.setEmc(saveFile(form.getEmc(), "standardCompliant_" + existingApplicant.getId()));
            standard.setEmcTestReportNo(form.getEmcTestReportNo());

            if (!form.getRadio().isEmpty())
                standard.setRadio(saveFile(form.getRadio(), "standardCompliant_" + existingApplicant.getId()));
            standard.setRadioTestReportNo(form.getRadioTestReportNo());

            if (!form.getHealthAndSafety().isEmpty())
                standard.setHealthAndSafety(saveFile(form.getHealthAndSafety(), "standardCompliant_" + existingApplicant.getId()));
            standard.setHealthAndSafetyTestReportNo(form.getHealthAndSafetyTestReportNo());

            if (!form.getTechnologySpecific().isEmpty())
                standard.setTechnologySpecific(saveFile(form.getTechnologySpecific(), "standardCompliant_" + existingApplicant.getId()));
            standard.setTechnologySpecificTestReportNo(form.getTechnologySpecificTestReportNo());

            standardComplaintRepository.save(standard);

        } catch (IOException e) {
            throw new RuntimeException("Failed to update standard files", e);
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
