package atraintegratedsystems.licenses.service;
import atraintegratedsystems.licenses.dto.LicenseApprovalDTO;
import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.repository.LicenseApprovalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class LicenseFeeMCITService {

    @Autowired
    private LicenseApprovalRepository licenseApprovalRepository;

    public List<LicenseApproval> getAllApprovalApplicants() {
        return licenseApprovalRepository.findAll();
    }

    public Optional<LicenseApproval> getLicenseApprovalId(Long id){
        return licenseApprovalRepository.findById(id);
    }

    public void updateLicenseApprovalFields(Long id, LicenseApprovalDTO dto) {
        LicenseApproval licenseApproval = licenseApprovalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LicenseApproval not found with id " + id));

        // Update only the last three fields
        licenseApproval.setLicenseFeeEntryVoucherDate(dto.getLicenseFeeEntryVoucherDate());
        licenseApproval.setLicenseFeeBankVoucherNo(dto.getLicenseFeeBankVoucherNo());
        licenseApproval.setLicenseFeePaymentStatus(dto.getLicenseFeePaymentStatus());

        licenseApprovalRepository.save(licenseApproval);
    }
}
