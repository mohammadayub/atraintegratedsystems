package atraintegratedsystems.typeofapproval.dto;

import atraintegratedsystems.typeofapproval.model.TypeOfApprovalApplicant;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalManufacturerDetail;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalTechnicalDetail;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class TypeOfApprovalFormDTO {
    private TypeOfApprovalApplicant applicant = new TypeOfApprovalApplicant();
    private List<TypeOfApprovalManufacturerDetail> manufacturers = new ArrayList<>();
//    private List<TypeOfApprovalTechnicalDetail> details= new ArrayList<>();
    private MultipartFile declarationOfConformity;
    private MultipartFile technicalOperationalDocOfTheRCE;
    private MultipartFile testReportsOfAccreditedLaboratory;
    private MultipartFile circuitDiagramPCB;
    private MultipartFile photographs;
    private MultipartFile label;
    private MultipartFile testReportsIssuedByAccreditedTesting;


 //   TypeOfApprovalTechnicalDetail

    private String gsm;
    private String cdma;
    private String lte;
    private String tetra;
    private String amateurRadio;
    private String privateMobileRadio;
    private String pmrRadio;
    private String radar;
    private String rlan;
    private String wimax;
    private String fwa;
    private String microwave;
    private String soundBroadcasting;
    private String tvBroadcasting;
    private String cordlessPhone;
    private String srd;
    private String rfid;
    private String satelliteRadio;
    private String radioNavigation;
    private String satelliteTv;
    private String vsat;
    private String other;
    private String intendedUse;
    private String modelNumber;
    private String brandName;
    private String typeNumber;
    private String countryofOrigin;
    private String frequencyrangeFromMHZ;
    private String frequencyrangeToMHZ;
    private String frequencyrangeFromGHZ;
    private String frequencyrangeToGHZ;
    private String outputPowerRadiatedConducted;
    private String transmissionCapacity;
    private String channelCapacity;
    private String channelSpacing;
    private String modulationType;
    private String antennaType;
    private String antennaGain;
    private String technicalInterface;
    private String technicalVariants;
    private String equipmentLicenseRequirement;
    private String enteredBy;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate enteredDate;













}
