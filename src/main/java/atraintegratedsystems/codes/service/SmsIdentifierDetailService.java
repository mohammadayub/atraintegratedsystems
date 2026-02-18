package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.SmsIdentifierDetailDTO;
import atraintegratedsystems.codes.model.SmsIdentifierCode;
import atraintegratedsystems.codes.model.SmsIdentifierDetail;
import atraintegratedsystems.codes.model.SmsIdentifierSerialNumber;
import atraintegratedsystems.codes.repository.SmsIdentifierCodeRepository;
import atraintegratedsystems.codes.repository.SmsIdentifierDetailRepository;
import atraintegratedsystems.codes.repository.SmsIdentifierSerialNumberRepository;
import atraintegratedsystems.utils.PersianCalendarUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SmsIdentifierDetailService {

    @Autowired
    private SmsIdentifierDetailRepository detailRepo;

    @Autowired
    private SmsIdentifierCodeRepository codeRepo;

    @Autowired
    private SmsIdentifierSerialNumberRepository serialNumberRepo;


    /* ================= CREATE ================= */
    public void save(SmsIdentifierDetailDTO dto) {

        SmsIdentifierCode code = codeRepo.findById(dto.getSmsIdentifierCodeId())
                .orElseThrow(() -> new RuntimeException("SMS Identifier Code not found"));

        if (code.getSmsIdentifierDetail() != null) {
            throw new RuntimeException("This SMS Identifier Code is already assigned");
        }

        SmsIdentifierDetail entity = new SmsIdentifierDetail();
        mapToEntity(dto, entity);

        code.setAssignStatus("Assign");
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

        // 🔁 Release old serial number if changed
        if (entity.getSmsIdentifierSerialNumber() != null &&
                !entity.getSmsIdentifierSerialNumber().getId().equals(dto.getSmsIdentifierSerialNumberId())) {

            SmsIdentifierSerialNumber oldSn = entity.getSmsIdentifierSerialNumber();
            oldSn.setStatus(null);
            serialNumberRepo.save(oldSn);
        }

        mapToEntity(dto, entity);

        detailRepo.save(entity);
    }


    /* ================= DELETE ================= */
    public void delete(Long id) {

        SmsIdentifierDetail detail = detailRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        SmsIdentifierCode code = detail.getSmsIdentifierCode();
        SmsIdentifierSerialNumber sn = detail.getSmsIdentifierSerialNumber();

        if (code != null) {
            code.setAssignStatus("Unassign");
            codeRepo.save(code);
        }

        if (sn != null) {
            sn.setStatus(null);   // 🔁 free the serial number
            serialNumberRepo.save(sn);
        }

        detailRepo.delete(detail);
    }


    /* ================= MAPPERS ================= */

    private void mapToEntity(SmsIdentifierDetailDTO dto, SmsIdentifierDetail e) {
        e.setCompanyName(dto.getCompanyName());
        e.setEnid(dto.getEnid());
        e.setCompanyAddress(dto.getCompanyAddress());
        e.setMobile(dto.getMobile());
        e.setTelephone(dto.getTelephone());
        e.setEmail(dto.getEmail());
        e.setChannel(dto.getChannel());
        e.setServiceType(dto.getServiceType());
        e.setMnosCompanyHost(dto.getMnosCompanyHost());
        e.setCodeCategory(dto.getCodeCategory());

//        e.setAssigningDate(dto.getAssigningDate());


        PersianCalendarUtils converter = new PersianCalendarUtils();
        /* ================= ENTRY VOUCHER DATE ================= */
        if (dto.getAssigningDateJalali() != null &&
                !dto.getAssigningDateJalali().trim().isEmpty()) {


                String[] parts = dto.getAssigningDateJalali().trim().split("-");

                if (parts.length == 3) {
                    int year = Integer.parseInt(parts[0]);
                    int month = Integer.parseInt(parts[1]);
                    int day = Integer.parseInt(parts[2]);

                    LocalDate assigningDate = converter.jalaliToGregorian(year, month, day);
                    e.setAssigningDate(assigningDate); // ✅ FIXED
                }
        }



//        e.setExpirationDate(dto.getExpirationDate());



        /* ================= Expiry DATE ================= */
        if (dto.getExpirationDateJalali() != null &&
                !dto.getExpirationDateJalali().trim().isEmpty()) {


            String[] part = dto.getExpirationDateJalali().trim().split("-");

            if (part.length == 3) {
                int eyear = Integer.parseInt(part[0]);
                int emonth = Integer.parseInt(part[1]);
                int eday = Integer.parseInt(part[2]);

                LocalDate expDate = converter.jalaliToGregorian(eyear, emonth, eday);
                e.setExpirationDate(expDate); // ✅ FIXED
            }
        }









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

        if (dto.getSmsIdentifierSerialNumberId() != null) {
            SmsIdentifierSerialNumber sn = serialNumberRepo.findById(dto.getSmsIdentifierSerialNumberId())
                    .orElseThrow(() -> new RuntimeException("Serial Number not found"));

            // 🚫 Prevent double assign
            if ("Assigned".equalsIgnoreCase(sn.getStatus())) {
                throw new RuntimeException("This Serial Number is already assigned!");
            }

            sn.setStatus("Assigned");   // 🔥 mark as assigned
            e.setSmsIdentifierSerialNumber(sn);
        }

    }

    private SmsIdentifierDetailDTO mapToDTO(SmsIdentifierDetail e) {
        SmsIdentifierDetailDTO dto = new SmsIdentifierDetailDTO();

        dto.setId(e.getId());
        dto.setCompanyName(e.getCompanyName());
        dto.setEnid(e.getEnid());
        dto.setCompanyAddress(e.getCompanyAddress());
        dto.setMobile(e.getMobile());
        dto.setTelephone(e.getTelephone());
        dto.setEmail(e.getEmail());
        dto.setChannel(e.getChannel());
        dto.setServiceType(e.getServiceType());
        dto.setMnosCompanyHost(e.getMnosCompanyHost());
        dto.setCodeCategory(e.getCodeCategory());
//        dto.setAssigningDate(e.getAssigningDate());

        dto.setAssigningDateJalali(
                e.getAssigningDateJalali() != null
                        ? e.getAssigningDateJalali().toString()
                        : null
        );

//        dto.setExpirationDate(e.getExpirationDate());

        dto.setExpirationDateJalali(
                e.getExpirationDateJalali() != null
                        ? e.getExpirationDateJalali().toString()
                        : null
        );

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

        if (e.getSmsIdentifierSerialNumber() != null) {
            dto.setSmsIdentifierSerialNumberId(e.getSmsIdentifierSerialNumber().getId());
        }


        return dto;
    }
}
