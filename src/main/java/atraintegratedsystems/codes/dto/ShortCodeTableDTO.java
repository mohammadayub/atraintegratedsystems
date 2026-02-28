package atraintegratedsystems.codes.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ShortCodeTableDTO {

    private Long id;
    private Integer shortCodeName;
    private Integer releaseShortCode;
    private String codeStatus;
    private String sourceUsed;
    private String location;
    private String chanel;
    private String services;
    private String category;
    private String backLongNumber;
    private String responsiblePerson;
    private String job;
    private String idCard;
    private String mobile;
    private String email;
    private LocalDate expirationDate;
    private double applicationFees;
    private double registrationFees;
    private double royaltyFees;
    private double total;
    private String paymentStatus;

    public ShortCodeTableDTO(
            Long id,
            Integer shortCodeName,
            Integer releaseShortCode,
            String codeStatus,
            String sourceUsed,
            String location,
            String chanel,
            String services,
            String category,
            String backLongNumber,
            String responsiblePerson,
            String job,
            String idCard,
            String mobile,
            String email,
            LocalDate expirationDate,
            double applicationFees,
            double registrationFees,
            double royaltyFees,
            double total,
            String paymentStatus
    ) {
        this.id = id;
        this.shortCodeName = shortCodeName;
        this.releaseShortCode = releaseShortCode;
        this.codeStatus = codeStatus;
        this.sourceUsed = sourceUsed;
        this.location = location;
        this.chanel = chanel;
        this.services = services;
        this.category = category;
        this.backLongNumber = backLongNumber;
        this.responsiblePerson = responsiblePerson;
        this.job = job;
        this.idCard = idCard;
        this.mobile = mobile;
        this.email = email;
        this.expirationDate = expirationDate;
        this.applicationFees = applicationFees;
        this.registrationFees = registrationFees;
        this.royaltyFees = royaltyFees;
        this.total = total;
        this.paymentStatus = paymentStatus;

    }
}