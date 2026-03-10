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
    @Column(columnDefinition = "TEXT")
    private String gsm;
    @Column(columnDefinition = "TEXT")
    private String cdma;
    @Column(columnDefinition = "TEXT")
    private String lte;
    @Column(columnDefinition = "TEXT")
    private String tetra;
    @Column(columnDefinition = "TEXT")
    private String amateurRadio;
    private String privateMobileRadio;
    @Column(columnDefinition = "TEXT")
    private String pmrRadio;
    @Column(columnDefinition = "TEXT")
    private String radar;
    @Column(columnDefinition = "TEXT")
    private String rlan;
    @Column(columnDefinition = "TEXT")
    private String wimax;
    @Column(columnDefinition = "TEXT")
    private String fwa;
    @Column(columnDefinition = "TEXT")
    private String microwave;
    @Column(columnDefinition = "TEXT")
    private String soundBroadcasting;
    @Column(columnDefinition = "TEXT")
    private String tvBroadcasting;
    private String cordlessPhone;
    @Column(columnDefinition = "TEXT")
    private String srd;
    @Column(columnDefinition = "TEXT")
    private String rfid;
    @Column(columnDefinition = "TEXT")
    private String satelliteRadio;
    @Column(columnDefinition = "TEXT")
    private String radioNavigation;
    @Column(columnDefinition = "TEXT")
    private String satelliteTv;
    @Column(columnDefinition = "TEXT")
    private String vsat;
    @Column(columnDefinition = "TEXT")
    private String other;
    @Column(columnDefinition = "TEXT")
    private String intendedUse;
    @Column(columnDefinition = "TEXT")
    private String modelNumber;
    @Column(columnDefinition = "TEXT")
    private String brandName;
    @Column(columnDefinition = "TEXT")
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
