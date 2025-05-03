package atraintegratedsystems.licenses.service;

import atraintegratedsystems.licenses.dto.LicenseFeeIntegrationDTO;
import atraintegratedsystems.licenses.repository.LicenseApprovalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LicenseIntegrationService {

    @Autowired
    private  LicenseApprovalRepository licenseApprovalRepository;

    public List<LicenseFeeIntegrationDTO> getPendingPayments() {
        return licenseApprovalRepository.findAll().stream()
                .filter(approval -> approval.getLicenseFeePaymentStatus() == null ||
                        approval.getLicenseFeePaymentStatus().equalsIgnoreCase("Not Paid"))
                .map(approval -> {
                    LicenseFeeIntegrationDTO dto = new LicenseFeeIntegrationDTO();
                    dto.setId(approval.getId());
                    dto.setLicenseFeeEntryVoucherDate(approval.getLicenseFeeEntryVoucherDate());
                    dto.setLicenseFeeBankVoucherNo(approval.getLicenseFeeBankVoucherNo());
                    dto.setLicenseFeeBankVoucherSubmissionDate(approval.getLicenseFeeBankVoucherSubmissionDate());
                    dto.setLicenseFeePaymentStatus(approval.getLicenseFeePaymentStatus());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
