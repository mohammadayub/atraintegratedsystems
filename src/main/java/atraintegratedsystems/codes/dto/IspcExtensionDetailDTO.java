package atraintegratedsystems.codes.dto;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class IspcExtensionDetailDTO {

    private Long id;
    private Long ispcDetailId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extendingDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;
    private double extendedFees;
    private String bankVoucherNo;
    private String paymentStatus;
}