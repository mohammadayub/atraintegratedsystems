package atraintegratedsystems.licenses.service;
import atraintegratedsystems.licenses.dto.LicenseApprovalDTO;
import atraintegratedsystems.licenses.model.LicenseApplicant;
import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.licenses.model.LicenseType;
import atraintegratedsystems.licenses.repository.LicenseApplicantRepository;
import atraintegratedsystems.licenses.repository.LicenseApprovalRepository;
import atraintegratedsystems.licenses.repository.LicenseTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LicenseApprovalService {


    @Autowired
    private LicenseApplicantRepository licenseApplicantRepository;
    @Autowired
    private LicenseApprovalRepository licenseApprovalRepository;
    @Transactional
    public List<LicenseApplicant> getAllpaid(){
        return licenseApplicantRepository.findAllApplicantsWithPaid();
    }

    @Transactional
    public Optional<LicenseApplicant> getApplicantId(Long id){
        return licenseApplicantRepository.findById(id);
    }

    @Transactional
    public void ApprovalSave(LicenseApproval licenseApproval){
        licenseApprovalRepository.save(licenseApproval);
    }



}
