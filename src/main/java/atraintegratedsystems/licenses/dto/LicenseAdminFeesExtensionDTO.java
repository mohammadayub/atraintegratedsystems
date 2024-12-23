package atraintegratedsystems.licenses.dto;

import atraintegratedsystems.utils.JalaliDate;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LicenseAdminFeesExtensionDTO {

    private Long id;
    private Long licenseApprovalId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extensionStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extensionExpireDate;
    //    Add bank voucher date,submission,Fees
    private BigDecimal extensionAdministrationFees;
    private LocalDate extensionAdministrationFeeBankVoucherNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extensionAdministrationFeeBankVoucherDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate extensionAdministrationFeeBankVoucherSubmissionDate;


    //for Extension Section Show Jalali Date
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private JalaliDate extStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private JalaliDate extExpireDate;
}
