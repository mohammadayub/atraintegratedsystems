package atraintegratedsystems.typeofapproval.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class TypeOfApprovalTechnicalDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
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
    @Column(columnDefinition = "TEXT")
    private String frequencyrangeFromMHZ;
    @Column(columnDefinition = "TEXT")
    private String frequencyrangeToMHZ;
    @Column(columnDefinition = "TEXT")
    private String frequencyrangeFromGHZ;
    @Column(columnDefinition = "TEXT")
    private String frequencyrangeToGHZ;
    @Column(columnDefinition = "TEXT")
    private String outputPowerRadiatedConducted;
    @Column(columnDefinition = "TEXT")
    private String transmissionCapacity;
    @Column(columnDefinition = "TEXT")
    private String channelCapacity;
    @Column(columnDefinition = "TEXT")
    private String channelSpacing;
    @Column(columnDefinition = "TEXT")
    private String modulationType;
    @Column(columnDefinition = "TEXT")
    private String antennaType;
    @Column(columnDefinition = "TEXT")
    private String antennaGain;
    @Column(columnDefinition = "TEXT")
    private String technicalInterface;
    @Column(columnDefinition = "TEXT")
    private String technicalVariants;
    @Column(columnDefinition = "TEXT")
    private String equipmentLicenseRequirement;
    private String enteredBy;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate enteredDate;

    @ManyToOne
    @JoinColumn(name = "type_of_approval_applicant_id")
    private TypeOfApprovalApplicant technicalDetails;

    @OneToMany(mappedBy = "typeOfApprovalTechnicalDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TacNumber> tacNumbers;
}
