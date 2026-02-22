package atraintegratedsystems.codes.model;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class IspcDetail {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String companyName;
    private String enid;
    private String companyAddress;
    private String mobile;
    private String telephone;
    private String email;
    private String channel;
    private String source_used;
    private String location;
    private String chanel;
    private String services;
    private String back_long_number;
    private String id_card_number_of_responsible_person;
    private String mobile_number_of_responsible_person;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate assigning_date;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiration_date;
    private double application_fees;
    private double royalty_fees;


}