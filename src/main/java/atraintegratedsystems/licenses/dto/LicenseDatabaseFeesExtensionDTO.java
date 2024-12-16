package atraintegratedsystems.licenses.dto;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Data
public class LicenseDatabaseFeesExtensionDTO {

    private Long id;

    private Long licenseApprovalId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extensionStartDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extensionExpireDate;
}
