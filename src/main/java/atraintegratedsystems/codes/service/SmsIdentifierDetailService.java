package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.SmsIdentifierDetailDTO;
import atraintegratedsystems.codes.model.SmsIdentifierCode;
import atraintegratedsystems.codes.model.SmsIdentifierDetail;
import atraintegratedsystems.codes.repository.SmsIdentifierCodeRepository;
import atraintegratedsystems.codes.repository.SmsIdentifierDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SmsIdentifierDetailService {

    @Autowired
    private SmsIdentifierDetailRepository detailRepo;

    @Autowired
    private SmsIdentifierCodeRepository codeRepo;

    /* ================= CREATE ================= */
    public void save(SmsIdentifierDetailDTO dto) {

        SmsIdentifierCode code = codeRepo.findById(dto.getSmsIdentifierCodeId())
                .orElseThrow(() -> new RuntimeException("SMS Identifier Code not found"));

        // 🔥 PREVENT DOUBLE ASSIGN (OPTIONAL BUT RECOMMENDED)
        if (code.getSmsIdentifierDetail() != null) {
            throw new RuntimeException("This SMS Identifier Code is already assigned");
        }

        // 🔥 AUTOMATIC STATUS UPDATE
        code.setAssignStatus("Assign");

        SmsIdentifierDetail entity = new SmsIdentifierDetail();
        mapToEntity(dto, entity);
        entity.setSmsIdentifierCode(code);

        detailRepo.save(entity);
        codeRepo.save(code);
    }

    /* ================= READ ALL ================= */
    public List<SmsIdentifierDetailDTO> findAll() {
        return detailRepo.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /* ================= READ BY ID ================= */
    public SmsIdentifierDetailDTO findById(Long id) {
        return mapToDTO(
                detailRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("Record not found"))
        );
    }

    /* ================= UPDATE ================= */
    public void update(Long id, SmsIdentifierDetailDTO dto) {

        SmsIdentifierDetail entity = detailRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        SmsIdentifierCode code = codeRepo.findById(dto.getSmsIdentifierCodeId())
                .orElseThrow(() -> new RuntimeException("SMS Identifier Code not found"));

        // 🔥 ENSURE STATUS REMAINS ASSIGN
        code.setAssignStatus("Assign");

        mapToEntity(dto, entity);
        entity.setSmsIdentifierCode(code);

        detailRepo.save(entity);
        codeRepo.save(code);
    }

    /* ================= DELETE ================= */
    public void delete(Long id) {

        SmsIdentifierDetail detail = detailRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        SmsIdentifierCode code = detail.getSmsIdentifierCode();

        // 🔥 RESET STATUS WHEN DETAIL IS DELETED
        if (code != null) {
            code.setAssignStatus("Unassign");
            codeRepo.save(code);
        }

        detailRepo.delete(detail);
    }

    /* ================= MAPPERS ================= */

    private void mapToEntity(SmsIdentifierDetailDTO dto, SmsIdentifierDetail e) {
        e.setCompanyName(dto.getCompanyName());
        e.setEid(dto.getEid());
        e.setCompanyAddress(dto.getCompanyAddress());
        e.setMobile(dto.getMobile());
        e.setTelephone(dto.getTelephone());
        e.setEmail(dto.getEmail());
        e.setChannel(dto.getChannel());
        e.setServiceType(dto.getServiceType());
        e.setMNOsCompanyHost(dto.getMNOsCompanyHost());
        e.setCodeCategory(dto.getCodeCategory());
        e.setAssigningDate(dto.getAssigningDate());
        e.setExpirationDate(dto.getExpirationDate());

        e.setApplicationFees(dto.getApplicationFees());
        e.setApplicationFeesBankVoucherNo(dto.getApplicationFeesBankVoucherNo());
        e.setApplicationFeesEnteryVoucherDate(dto.getApplicationFeesEnteryVoucherDate());
        e.setApplicationFeesBankVoucherSubmissionDate(dto.getApplicationFeesBankVoucherSubmissionDate());
        e.setApplicationFeesPaymentStatus(dto.getApplicationFeesPaymentStatus());

        e.setRoyaltyFees(dto.getRoyaltyFees());
        e.setRoyaltyFeesBankVoucherNo(dto.getRoyaltyFeesBankVoucherNo());
        e.setRoyaltyFeesEnteryVoucherDate(dto.getRoyaltyFeesEnteryVoucherDate());
        e.setRoyaltyFeesBankVoucherSubmissionDate(dto.getRoyaltyFeesBankVoucherSubmissionDate());
        e.setRoyaltyFeesPaymentStatus(dto.getRoyaltyFeesPaymentStatus());

        e.setShortCodeRejectionStatus(dto.getShortCodeRejectionStatus());
        e.setShortCodeRejectionDate(dto.getShortCodeRejectionDate());
    }

    private SmsIdentifierDetailDTO mapToDTO(SmsIdentifierDetail e) {
        SmsIdentifierDetailDTO dto = new SmsIdentifierDetailDTO();

        dto.setId(e.getId());
        dto.setCompanyName(e.getCompanyName());
        dto.setEid(e.getEid());
        dto.setCompanyAddress(e.getCompanyAddress());
        dto.setMobile(e.getMobile());
        dto.setTelephone(e.getTelephone());
        dto.setEmail(e.getEmail());
        dto.setChannel(e.getChannel());
        dto.setServiceType(e.getServiceType());
        dto.setMNOsCompanyHost(e.getMNOsCompanyHost());
        dto.setCodeCategory(e.getCodeCategory());
        dto.setAssigningDate(e.getAssigningDate());
        dto.setExpirationDate(e.getExpirationDate());

        dto.setApplicationFees(e.getApplicationFees());
        dto.setApplicationFeesBankVoucherNo(e.getApplicationFeesBankVoucherNo());
        dto.setApplicationFeesEnteryVoucherDate(e.getApplicationFeesEnteryVoucherDate());
        dto.setApplicationFeesBankVoucherSubmissionDate(e.getApplicationFeesBankVoucherSubmissionDate());
        dto.setApplicationFeesPaymentStatus(e.getApplicationFeesPaymentStatus());

        dto.setRoyaltyFees(e.getRoyaltyFees());
        dto.setRoyaltyFeesBankVoucherNo(e.getRoyaltyFeesBankVoucherNo());
        dto.setRoyaltyFeesEnteryVoucherDate(e.getRoyaltyFeesEnteryVoucherDate());
        dto.setRoyaltyFeesBankVoucherSubmissionDate(e.getRoyaltyFeesBankVoucherSubmissionDate());
        dto.setRoyaltyFeesPaymentStatus(e.getRoyaltyFeesPaymentStatus());

        dto.setShortCodeRejectionStatus(e.getShortCodeRejectionStatus());
        dto.setShortCodeRejectionDate(e.getShortCodeRejectionDate());

        dto.setSmsIdentifierCodeId(e.getSmsIdentifierCode().getId());

        return dto;
    }
}
