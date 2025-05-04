package atraintegratedsystems.licenses.service;

import atraintegratedsystems.licenses.dto.LicenseFeeIntegrationDTO;
import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.repository.LicenseApprovalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LicenseFeesIntegrationSendService {

    @Autowired
    private LicenseApprovalRepository licenseApprovalRepository;

    public List<LicenseFeeIntegrationDTO> getLicenseUnPaidList() {
        List<LicenseApproval> approvals = licenseApprovalRepository.findUnpaidLicenseFees();
        return approvals.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private LicenseFeeIntegrationDTO convertToDTO(LicenseApproval approval) {
        LicenseFeeIntegrationDTO dto = new LicenseFeeIntegrationDTO();
        dto.setId(approval.getId());
        dto.setLicenseFeeEntryVoucherDate(approval.getLicenseFeeEntryVoucherDate());
        dto.setLicenseFeeBankVoucherNo(approval.getLicenseFeeBankVoucherNo());
        dto.setLicenseFeeBankVoucherSubmissionDate(approval.getLicenseFeeBankVoucherSubmissionDate());
        dto.setLicenseFeePaymentStatus(approval.getLicenseFeePaymentStatus());
        return dto;
    }
}
