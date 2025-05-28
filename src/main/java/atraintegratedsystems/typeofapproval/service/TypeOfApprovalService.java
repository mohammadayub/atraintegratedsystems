package atraintegratedsystems.typeofapproval.service;
import atraintegratedsystems.typeofapproval.dto.TypeOfApprovalFormDTO;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalAttachment;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalManufacturerDetail;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalTechnicalDetail;
import atraintegratedsystems.typeofapproval.repository.TypeOfApprovalApplicantRepository;
import atraintegratedsystems.typeofapproval.repository.TypeOfApprovalAttachmentRepository;
import atraintegratedsystems.typeofapproval.repository.TypeOfApprovalManufacturerDetailRepository;
import atraintegratedsystems.typeofapproval.repository.TypeOfApprovalTechnicalDetailsRepository;
import org.springframework.format.annotation.DateTimeFormat;
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
    private final TypeOfApprovalTechnicalDetailsRepository technicalDetailsRepository;

    public TypeOfApprovalService(TypeOfApprovalApplicantRepository applicantRepository,
                                 TypeOfApprovalManufacturerDetailRepository manufacturerRepository,
                                 TypeOfApprovalAttachmentRepository attachmentRepository,
                                 TypeOfApprovalTechnicalDetailsRepository technicalDetailsRepository) {
        this.applicantRepository = applicantRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.attachmentRepository = attachmentRepository;
        this.technicalDetailsRepository = technicalDetailsRepository;
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

//        //Save technicalDetails
//        for(TypeOfApprovalTechnicalDetail detail :form.getDetails()){
//            detail.setTechnicalDetails(savedApplicant);
//            technicalDetailsRepository.save(detail);
//        }
        //Save Technical Details

        try{
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
            detail.setSatelliteRadio(form.getSatelliteRadio());
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
            detail.setEnteredBy("Online Entry");
            detail.setEnteredDate(LocalDate.now());
            technicalDetailsRepository.save(detail);
        }
     catch (Exception e) {
        // Preferably use a logger here
        e.printStackTrace();
    }
        // Save attachments
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
            // Preferably use a logger here
            e.printStackTrace();
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
