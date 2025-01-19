package atraintegratedsystems.licenses.service;


import atraintegratedsystems.licenses.dto.LicenseApplicantApprovalDTO;
import atraintegratedsystems.licenses.dto.LicenseApplicantDTO;
import atraintegratedsystems.licenses.model.LicenseApplicant;
import atraintegratedsystems.licenses.model.LicenseType;
import atraintegratedsystems.licenses.repository.LicenseApplicantRepository;
import atraintegratedsystems.licenses.repository.LicenseTypeRepository;
import atraintegratedsystems.utils.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class LicenseNewApplicantService {
    @Autowired
    private LicenseApplicantRepository repository;
    @Autowired
    private LicenseTypeRepository licenseTypeRepository;
    private static final List<String> VALID_FILE_TYPES = Arrays.asList("application/pdf", "image/jpeg", "image/png");

    @Transactional
    public List<LicenseApplicant> getAllApplicants() {
      return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<LicenseApplicant> getApplicantById(Long id) {
        return repository.findById(id);
    }

    public List<LicenseApplicantApprovalDTO> getAllLicenseApplicantApprovalDetails() {
        return repository.findAllLicenseApplicantApprovalDetails();
    }

    @Transactional
    public LicenseApplicant saveProfile(LicenseApplicantDTO dto) throws Exception {
        LicenseApplicant profile = new LicenseApplicant();
        profile.setReqId(generateRequestId());
        DateConverter dateConverter = new DateConverter();
        // Convert Jalali date to Gregorian
        LocalDate requestDate = dateConverter.jalaliToGregorian(dto.getReqDate().getYear(), dto.getReqDate().getMonthValue(), dto.getReqDate().getDayOfMonth());
        profile.setReqDate(requestDate);

        // Set the LicenseType based on the provided licenseTypeId
        if (dto.getLicenseTypeId() != null) {
            LicenseType licenseType = licenseTypeRepository.findById(dto.getLicenseTypeId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid license type ID: " + dto.getLicenseTypeId()));
            profile.setLicenseType(licenseType);
        } else {
            throw new IllegalArgumentException("License type is required");
        }

        profile.setCurrencyType(dto.getCurrencyType());
        profile.setFinanceType(dto.getFinanceType());
        profile.setCompanyLicenseName(dto.getCompanyLicenseName());

        MultipartFile applicationUpload = dto.getApplicationUpload();

        if (applicationUpload != null && !applicationUpload.isEmpty()) {
            String contentType = applicationUpload.getContentType();

            if (!VALID_FILE_TYPES.contains(contentType)) {
                throw new IllegalArgumentException("Invalid File Type: " + contentType);
            }

            profile.setApplicationUpload(applicationUpload.getBytes());
        }
        return repository.save(profile);
    }


    private String generateRequestId() {
        Long maxId = repository.findMaxId();
        String prefix = "ATRA-REQ-";
        if (maxId == null) {
            return prefix + "0001";
        }
        Long nextId = maxId + 1;
        return prefix + String.format("%04d", nextId);
    }

}
