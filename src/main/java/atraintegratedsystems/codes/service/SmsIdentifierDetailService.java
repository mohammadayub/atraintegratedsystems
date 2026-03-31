package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.SmsIdentifierDetailDTO;
import atraintegratedsystems.codes.model.SmsIdentifierCode;
import atraintegratedsystems.codes.model.SmsIdentifierDetail;
import atraintegratedsystems.codes.repository.SmsIdentifierCodeRepository;
import atraintegratedsystems.codes.repository.SmsIdentifierDetailRepository;
import atraintegratedsystems.utils.DateConverter;
import atraintegratedsystems.utils.PersianCalendarUtils;
import atraintegratedsystems.utils.SerialGeneratorWithString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SmsIdentifierDetailService {

    @Autowired
    private SmsIdentifierDetailRepository detailRepository;

    @Autowired
    private SmsIdentifierCodeRepository codeRepository;

    /* ================= CREATE ================= */

    public void save(SmsIdentifierDetailDTO dto) {

        SmsIdentifierDetail entity;

        // ✅ CHECK IF UPDATE
        if (dto.getId() != null) {
            entity = detailRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Record not found"));
        } else {
            entity = new SmsIdentifierDetail();
        }

        SmsIdentifierCode code = codeRepository.findById(dto.getSmsIdentifierCodeId())
                .orElseThrow(() -> new RuntimeException("SMS Identifier Code not found"));

        mapToEntity(dto, entity);

        entity.setSmsIdentifierCode(code);
        code.setAssignStatus("Assign");

        /* ================= AUTO SERIAL (ONLY FOR NEW) ================= */

        if (entity.getId() == null &&
                (entity.getSerialNumber() == null || entity.getSerialNumber().trim().isEmpty())) {

            SerialGeneratorWithString generator = new SerialGeneratorWithString();

            if (entity.getCompanyName() == null || entity.getExpirationDate() == null) {
                throw new IllegalArgumentException("Company Name and Expiration Date required");
            }

            String codeName = entity.getSmsIdentifierCode().getSmsIdentifierCodeName();

            String serial = generator.generateSerialNumber(
                    entity.getCompanyName(),
                    entity.getExpirationDate(),
                    codeName
            );

            entity.setSerialNumber(serial);
        }

        detailRepository.save(entity);
        codeRepository.save(code);
    }

    /* ================= READ ALL ================= */

    public List<SmsIdentifierDetailDTO> getAll() {
        return detailRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /* ================= READ BY ID ================= */

    public SmsIdentifierDetailDTO getById(Long id) {
        return mapToDTO(
                detailRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Record not found"))
        );
    }

    /* ================= UPDATE ================= */

    public void update(Long id, SmsIdentifierDetailDTO dto) {

        SmsIdentifierDetail entity = detailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        if (dto.getSmsIdentifierCodeId() != null) {

            SmsIdentifierCode code = codeRepository.findById(dto.getSmsIdentifierCodeId())
                    .orElseThrow(() -> new RuntimeException("Code not found"));

            entity.setSmsIdentifierCode(code);
            code.setAssignStatus("Assign");

            codeRepository.save(code);
        }

        mapToEntity(dto, entity);

        detailRepository.save(entity);
    }

    /* ================= DELETE ================= */

    public void delete(Long id) {

        SmsIdentifierDetail detail = detailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        SmsIdentifierCode code = detail.getSmsIdentifierCode();

        if (code != null) {
            code.setAssignStatus("Unassign");
            codeRepository.save(code);
        }

        detailRepository.delete(detail);
    }

    /* ================= ENTITY MAPPER ================= */

    private void mapToEntity(SmsIdentifierDetailDTO dto, SmsIdentifierDetail e) {

        e.setCompanyName(dto.getCompanyName());
        e.setCompanyNameInDari(dto.getCompanyNameInDari());
        // ✅ ONLY set serial if provided (for create or manual input)
        if (dto.getSerialNumber() != null && !dto.getSerialNumber().trim().isEmpty()) {
            e.setSerialNumber(dto.getSerialNumber());
        }
        e.setResponsiblePerson(dto.getResponsiblePerson());
        e.setJob(dto.getJob());
        e.setEnid(dto.getEnid());
        e.setCompanyAddress(dto.getCompanyAddress());
        e.setMobile(dto.getMobile());
        e.setTelephone(dto.getTelephone());
        e.setEmail(dto.getEmail());
        e.setChannel(dto.getChannel());
        e.setServiceType(dto.getServiceType());
        e.setMnosCompanyHost(dto.getMnosCompanyHost());
        e.setCodeCategory(dto.getCodeCategory());

        PersianCalendarUtils converter = new PersianCalendarUtils();

        /* ================= Assigning Date ================= */

        if (dto.getAssigningDateJalali() != null && !dto.getAssigningDateJalali().isEmpty()) {

            String[] parts = dto.getAssigningDateJalali().split("-");

            int y = Integer.parseInt(parts[0]);
            int m = Integer.parseInt(parts[1]);
            int d = Integer.parseInt(parts[2]);

            LocalDate date = converter.jalaliToGregorian(y, m, d);
            e.setAssigningDate(date);
        }

        /* ================= Expiration Date ================= */

        if (dto.getExpirationDateJalali() != null && !dto.getExpirationDateJalali().isEmpty()) {

            String[] parts = dto.getExpirationDateJalali().split("-");

            int y = Integer.parseInt(parts[0]);
            int m = Integer.parseInt(parts[1]);
            int d = Integer.parseInt(parts[2]);

            LocalDate date = converter.jalaliToGregorian(y, m, d);
            e.setExpirationDate(date);
        }

        /* ================= FEES ================= */

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
        e.setRemark(dto.getRemark());
    }

    /* ================= DTO MAPPER ================= */

    private SmsIdentifierDetailDTO mapToDTO(SmsIdentifierDetail e) {

        SmsIdentifierDetailDTO dto = new SmsIdentifierDetailDTO();

        dto.setId(e.getId());
        dto.setCompanyName(e.getCompanyName());
        dto.setCompanyNameInDari(e.getCompanyNameInDari());
        dto.setSerialNumber(e.getSerialNumber());
        dto.setResponsiblePerson(e.getResponsiblePerson());
        dto.setJob(e.getJob());
        dto.setEnid(e.getEnid());
        dto.setCompanyAddress(e.getCompanyAddress());
        dto.setMobile(e.getMobile());
        dto.setTelephone(e.getTelephone());
        dto.setEmail(e.getEmail());
        dto.setChannel(e.getChannel());
        dto.setServiceType(e.getServiceType());
        dto.setMnosCompanyHost(e.getMnosCompanyHost());
        dto.setCodeCategory(e.getCodeCategory());

        if (e.getSmsIdentifierCode() != null) {
            dto.setSmsIdentifierCodeId(e.getSmsIdentifierCode().getId());
            dto.setSmsIdentifierCodeName(e.getSmsIdentifierCode().getSmsIdentifierCodeName());
        }

        DateConverter converter = new DateConverter();

        /* Assigning Date */
        if (e.getAssigningDate() != null) {
            var jd = converter.gregorianToJalali(
                    e.getAssigningDate().getYear(),
                    e.getAssigningDate().getMonthValue(),
                    e.getAssigningDate().getDayOfMonth()
            );

            dto.setAssigningDateJalali(
                    String.format("%04d-%02d-%02d",
                            jd.getYear(),
                            jd.getMonthPersian().getValue(),
                            jd.getDay())
            );
        }

        /* Expiration Date */
        if (e.getExpirationDate() != null) {
            var jd = converter.gregorianToJalali(
                    e.getExpirationDate().getYear(),
                    e.getExpirationDate().getMonthValue(),
                    e.getExpirationDate().getDayOfMonth()
            );

            dto.setExpirationDateJalali(
                    String.format("%04d-%02d-%02d",
                            jd.getYear(),
                            jd.getMonthPersian().getValue(),
                            jd.getDay())
            );
        }

        return dto;
    }
}