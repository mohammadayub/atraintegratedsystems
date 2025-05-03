
package atraintegratedsystems.licenses.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class LicenseFeeIntegrationDTO {
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate licenseFeeEntryVoucherDate;

    private String licenseFeeBankVoucherNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate licenseFeeBankVoucherSubmissionDate;

    private String licenseFeePaymentStatus;
}
