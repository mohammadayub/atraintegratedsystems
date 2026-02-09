package atraintegratedsystems.codes.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "sms_identifier_serial_number")   // ⭐ VERY IMPORTANT
@Data
public class SmsIdentifierSerialNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_number", nullable = false)
    private int serialNumber;

    @Column(name = "status")
    private String status;

    // ✅ Correct inverse mapping
    @OneToOne(mappedBy = "smsIdentifierSerialNumber")
    private SmsIdentifierDetail smsIdentifierDetail;
}
