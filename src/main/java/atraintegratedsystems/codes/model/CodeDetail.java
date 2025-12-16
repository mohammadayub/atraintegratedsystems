package atraintegratedsystems.codes.model;


import atraintegratedsystems.licenses.model.LicenseApplicant;
import atraintegratedsystems.licenses.model.LicenseType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class CodeDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="code_detail_id")
    private Long id;
    @Column(name="short_code")
    private Integer shortCode;
    private Integer releaseShortCode;
    private String codeStatus;
    @Column(name="ispc_unique_name_of_signaling_point")
    private String unique_name_of_signaling_point;
    @ManyToOne
    @JoinColumn(name = "license_applicant_id")
    private LicenseApplicant licenseApplicant;
    private String sourceUsed;
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
    public void setTotal(double total) {
        this.total = application_fees+registration_fees+royalty_fees;
    }


}
