package atraintegratedsystems.codes.dto;

import atraintegratedsystems.utils.DateConverter;
import atraintegratedsystems.utils.JalaliDate;
import lombok.Data;
import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class RejectedIspcDetailDTO {

    private Long id;
    private String ispcCodeName;
    private String companyName;
    private String serialNumber;
    private String mobile;
    private String telephone;
    private String email;
    private String ispcNumber;
    private String signalingPoint;
    private String location;
    private String enid;
    private String responsiblePerson;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate assigningDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;
    private String ispcCodeRejectionStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate ispcCodeRejectionDate;

    public RejectedIspcDetailDTO(Long id,
                                 String ispcCodeName,
                                 String companyName,
                                 String serialNumber,
                                 String mobile,
                                 String telephone,
                                 String email,
                                 String ispcNumber,
                                 String signalingPoint,
                                 String location,
                                 String enid,
                                 String responsiblePerson,
                                 LocalDate assigningDate,
                                 LocalDate expirationDate,

                                 String ispcCodeRejectionStatus,
                                 LocalDate ispcCodeRejectionDate) {

        this.id = id;
        this.ispcCodeName = ispcCodeName;
        this.companyName = companyName;
        this.serialNumber = serialNumber;
        this.mobile = mobile;
        this.telephone = telephone;
        this.email = email;
        this.ispcNumber = ispcNumber;
        this.signalingPoint = signalingPoint;
        this.location = location;
        this.enid = enid;
        this.responsiblePerson = responsiblePerson;
        this.assigningDate=assigningDate;
        this.expirationDate=expirationDate;
        this.ispcCodeRejectionStatus = ispcCodeRejectionStatus;
        this.ispcCodeRejectionDate = ispcCodeRejectionDate;
    }


    public JalaliDate getAssigningDateJalali() {

        if(assigningDate == null){
            return null;
        }

        DateConverter converter = new DateConverter();
        return converter.gregorianToJalali(
                assigningDate.getYear(),
                assigningDate.getMonthValue(),
                assigningDate.getDayOfMonth()
        );
    }

    public JalaliDate getExpirationDateJalali() {
        if(expirationDate == null){
            return null;
        }
        DateConverter converter = new DateConverter();
        return converter.gregorianToJalali(
                expirationDate.getYear(),
                expirationDate.getMonthValue(),
                expirationDate.getDayOfMonth()
        );
    }


    public JalaliDate getIspcCodeRejectionDateJalali() {
        if(ispcCodeRejectionDate == null){
            return null;
        }
        DateConverter converter = new DateConverter();
        return converter.gregorianToJalali(
                ispcCodeRejectionDate.getYear(),
                ispcCodeRejectionDate.getMonthValue(),
                ispcCodeRejectionDate.getDayOfMonth()
        );
    }









}