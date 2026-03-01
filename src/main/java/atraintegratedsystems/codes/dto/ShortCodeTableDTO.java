package atraintegratedsystems.codes.dto;

import atraintegratedsystems.utils.DateConverter;
import atraintegratedsystems.utils.JalaliDate;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ShortCodeTableDTO {

    private Long id;
    private Integer shortCodeName;
    private String serialNumber;
    private Integer releaseShortCode;
    private String codeStatus;
    private String sourceUsed;
    private String sourceUsedInDari;
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
    private LocalDate assigningDate;
    private LocalDate expirationDate;
    private double applicationFees;
    private double registrationFees;
    private double royaltyFees;
    private double total;
    private String paymentStatus;

    public ShortCodeTableDTO(
            Long id,
            Integer shortCodeName,
            String serialNumber,
            Integer releaseShortCode,
            String codeStatus,
            String sourceUsed,
            String sourceUsedInDari,
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
            LocalDate assigningDate,
            LocalDate expirationDate,
            double applicationFees,
            double registrationFees,
            double royaltyFees,
            double total,
            String paymentStatus
    ) {
        this.id = id;
        this.shortCodeName = shortCodeName;
        this.serialNumber=serialNumber;
        this.releaseShortCode = releaseShortCode;
        this.codeStatus = codeStatus;
        this.sourceUsed = sourceUsed;
        this.sourceUsedInDari=sourceUsedInDari;
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
        this.assigningDate=assigningDate;
        this.expirationDate = expirationDate;
        this.applicationFees = applicationFees;
        this.registrationFees = registrationFees;
        this.royaltyFees = royaltyFees;
        this.total = total;
        this.paymentStatus = paymentStatus;

    }

    public JalaliDate getAssignDateJalali() {
        if (assigningDate == null) {
            return null;
        }
        DateConverter dateConverter = new DateConverter();
        return dateConverter.gregorianToJalali(
                assigningDate.getYear(),
                assigningDate.getMonthValue(),
                assigningDate.getDayOfMonth()
        );
    }


    public JalaliDate getExpireDateJalali() {
        if (expirationDate == null) {
            return null;
        }
        DateConverter dateConverter = new DateConverter();
        return dateConverter.gregorianToJalali(
                expirationDate.getYear(),
                expirationDate.getMonthValue(),
                expirationDate.getDayOfMonth()
        );
    }
}