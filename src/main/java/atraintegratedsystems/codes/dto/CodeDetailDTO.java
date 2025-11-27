package atraintegratedsystems.codes.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class CodeDetailDTO {
    private Long id;
    private int shortCode;
    private String codeStatus;
    private String unique_name_of_signaling_point;
    private Long licenseTypeId;
    private String location;
    private String chanel;
    private String services;
    private String categoryType;
    private String category;
    private String back_long_number;
    private String name_of_responsible_person;
    private String id_card_number_of_responsible_person;
    private String mobile_number_of_responsible_person;
    private String email_of_responsible_person;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate assigning_date;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiration_date;
    private double application_fees;
    private double registration_fees;
    private double royalty_fees;
    private double total;
    private String bankVoucherNo;
    private String paymentStatus;
}
