package atraintegratedsystems.codes.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Getter
@Setter
public class SmsIdentifierFinanceExtensionDTO {

    private Long id;
    private String smsIdentifierCodeName;
    private String companyName;
    private String enid;
    private String mobile;
    private String email;
    private String channel;
    private String serviceType;
    private String mnosCompanyHost;
    private String codeCategory;
    private String applicationPaymentStatus;
    private String annualPaymentStatus;
    private LocalDate extensionStartDate;
    private LocalDate extentionExpirationDate;
    private String extendStatus;
    private LocalDate createdDate;

    // ======== THIS IS WHAT HIBERNATE NEEDS ========
    public SmsIdentifierFinanceExtensionDTO(
            Long id,
            String smsIdentifierCodeName,
            String companyName,
            String enid,
            String mobile,
            String email,
            String channel,
            String serviceType,
            String mnosCompanyHost,
            String codeCategory,
            String applicationPaymentStatus,
            String annualPaymentStatus,
            LocalDate extensionStartDate,
            LocalDate extentionExpirationDate,
            String extendStatus,
            LocalDate createdDate
    ) {
        this.id = id;
        this.smsIdentifierCodeName = smsIdentifierCodeName;
        this.companyName = companyName;
        this.enid = enid;
        this.mobile = mobile;
        this.email = email;
        this.channel = channel;
        this.serviceType = serviceType;
        this.mnosCompanyHost = mnosCompanyHost;
        this.codeCategory = codeCategory;
        this.applicationPaymentStatus = applicationPaymentStatus;
        this.annualPaymentStatus = annualPaymentStatus;
        this.extensionStartDate = extensionStartDate;
        this.extentionExpirationDate = extentionExpirationDate;
        this.extendStatus = extendStatus;
        this.createdDate = createdDate;
    }

// ===== Getters =====

    public Long getId() { return id; }
    public String getSmsIdentifierCodeName() { return smsIdentifierCodeName; }
    public String getCompanyName() { return companyName; }
    public String getEnid() { return enid; }
    public String getMobile() { return mobile; }
    public String getEmail() { return email; }
    public String getChannel() { return channel; }
    public String getServiceType() { return serviceType; }
    public String getMnosCompanyHost() { return mnosCompanyHost; }
    public String getCodeCategory() { return codeCategory; }
    public String getApplicationPaymentStatus() { return applicationPaymentStatus; }
    public String getAnnualPaymentStatus() { return annualPaymentStatus; }
    public LocalDate getExtensionStartDate() { return extensionStartDate; }
    public LocalDate getExtentionExpirationDate() { return extentionExpirationDate; }
    public String getExtendStatus() { return extendStatus; }
    public LocalDate getCreatedDate() { return createdDate; }


}
