package atraintegratedsystems.codes.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SmsExtensionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "smsIdentifier_detail_id")
    private SmsIdentifierDetail smsIdentifierDetail;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extendingDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;
    private double extendedFees;
    private String bankVoucherNo;
    private String paymentStatus;



}
