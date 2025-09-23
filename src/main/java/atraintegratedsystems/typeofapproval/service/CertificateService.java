package atraintegratedsystems.typeofapproval.service;

import atraintegratedsystems.typeofapproval.dto.ApplicantCertificateDTO;
import atraintegratedsystems.typeofapproval.repository.CertificateRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CertificateService {
    private final CertificateRepository repository;

    public CertificateService(CertificateRepository repository) {
        this.repository = repository;
    }

    public List<ApplicantCertificateDTO> getApprovedCertificatesByCompanyId(Long companyId) {
        return repository.findApprovedCertificatesByCompanyId(companyId).stream()
                .map(r -> new ApplicantCertificateDTO(
                        (String) r[0],   // applicantNumber
                        (String) r[1],   // applicantName
                        (String) r[2],   // address
                        (String) r[3],   // telephone
                        (String) r[4],   // email
                        (String) r[5],   // modelNumber
                        (String) r[6],   // brandName
                        (String) r[7],   // typeNumber
                        (String) r[8],   // countryOfOrigin
                        (String) r[9],   // frequencyRange
                        (String) r[10],  // outputPower
                        (String) r[11],  // transmissionCapacity
                        r[0] + "-" + String.valueOf(r[12]), // applicantNumber + firstTacNumber
                        r[0] + "-" + String.valueOf(r[13])  // applicantNumber + lastTacNumber
                ))
                .collect(Collectors.toList());
    }

}
