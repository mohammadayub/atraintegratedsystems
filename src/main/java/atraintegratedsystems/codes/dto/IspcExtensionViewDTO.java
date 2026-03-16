package atraintegratedsystems.codes.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class IspcExtensionViewDTO {

    private Long id;

    private String ispcCodeName;

    private String serialNumber;
    private String signalingPoint;
    private String companyName;
    private String enid;
    private String location;
    private String companyAddress;
    private String responsiblePerson;
    private String job;
    private String mobile;
    private String telephone;
    private String email;

    private LocalDate assigningDate;
    private LocalDate expirationDate;

    private String extensionPaymentStatus;

    private LocalDate extensionStartDate;
    private LocalDate extentionExpirationDate;

    private String extendStatus;

    // Jalali fields (not part of JPQL constructor)
    private String extensionStartDateJalali;
    private String extentionExpirationDateJalali;


    public IspcExtensionViewDTO(
            Long id,
            String ispcCodeName,
            String serialNumber,
            String signalingPoint,
            String companyName,
            String enid,
            String location,
            String companyAddress,
            String responsiblePerson,
            String job,
            String mobile,
            String telephone,
            String email,
            LocalDate assigningDate,
            LocalDate expirationDate,
            String extensionPaymentStatus,
            LocalDate extensionStartDate,
            LocalDate extentionExpirationDate,
            String extendStatus
    ) {
        this.id = id;
        this.ispcCodeName = ispcCodeName;
        this.serialNumber = serialNumber;
        this.signalingPoint = signalingPoint;
        this.companyName = companyName;
        this.enid = enid;
        this.location = location;
        this.companyAddress = companyAddress;
        this.responsiblePerson = responsiblePerson;
        this.job = job;
        this.mobile = mobile;
        this.telephone = telephone;
        this.email = email;
        this.assigningDate = assigningDate;
        this.expirationDate = expirationDate;
        this.extensionPaymentStatus = extensionPaymentStatus;
        this.extensionStartDate = extensionStartDate;
        this.extentionExpirationDate = extentionExpirationDate;
        this.extendStatus = extendStatus;
    }
}