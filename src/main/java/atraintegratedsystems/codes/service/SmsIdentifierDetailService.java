package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.SmsIdentifierDetailDTO;
import atraintegratedsystems.codes.model.SmsIdentifierCode;
import atraintegratedsystems.codes.model.SmsIdentifierDetail;
import atraintegratedsystems.codes.repository.SmsIdentifierCodeRepository;
import atraintegratedsystems.codes.repository.SmsIdentifierDetailRepository;
import atraintegratedsystems.utils.PersianCalendarUtils;
import atraintegratedsystems.utils.SerialGenerator;
import atraintegratedsystems.utils.SerialGeneratorWithString;
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



    /* ================= CREATE ================= */
    public void save(SmsIdentifierDetailDTO dto) {

        SmsIdentifierCode code = codeRepo.findById(dto.getSmsIdentifierCodeId())
                .orElseThrow(() -> new RuntimeException("SMS Identifier Code not found"));


        SmsIdentifierDetail entity = new SmsIdentifierDetail();
        mapToEntity(dto, entity);
        code.setAssignStatus("Assign");
        entity.setSmsIdentifierCode(code);




        //Serial Number Auto Generate//

        SerialGeneratorWithString serialGenerator = new SerialGeneratorWithString();

        if (entity.getSerialNumber() == null || entity.getSerialNumber().trim().isEmpty()) {

            if (entity.getCompanyName() == null || entity.getExpirationDate() == null) {
                throw new IllegalArgumentException("Company Name and Expiration Date are required");
            }

            String smsCodeName = entity.getSmsIdentifierCode().getSmsIdentifierCodeName();

            String serial = serialGenerator.generateSerialNumber(
                    entity.getCompanyName(),
                    entity.getExpirationDate(),
                    smsCodeName
            );

            entity.setSerialNumber(serial);
        }

        ///  End  ///


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

        // ✅ Handle SMS Identifier Code change
        if (dto.getSmsIdentifierCodeId() != null) {

            SmsIdentifierCode newCode = codeRepo.findById(dto.getSmsIdentifierCodeId())
                    .orElseThrow(() -> new RuntimeException("Code not found"));

            entity.setSmsIdentifierCode(newCode);

            newCode.setAssignStatus("Assign");
            codeRepo.save(newCode);
        }

        mapToEntity(dto, entity);

        detailRepo.save(entity);
    }


    /* ================= DELETE ================= */
    public void delete(Long id) {

        SmsIdentifierDetail detail = detailRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        SmsIdentifierCode code = detail.getSmsIdentifierCode();

        if (code != null) {
            code.setAssignStatus("Unassign");
            codeRepo.save(code);
        }


        detailRepo.delete(detail);
    }


    /* ================= MAPPERS ================= */

    private void mapToEntity(SmsIdentifierDetailDTO dto, SmsIdentifierDetail e) {

        e.setCompanyName(dto.getCompanyName());
        e.setCompanyNameInDari(dto.getCompanyNameInDari());
        e.setSerialNumber(dto.getSerialNumber());
        e.setResponsiblePerson(dto.getResponsiblePerson());   // ✅ ADD
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



    }

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

        // ✅ SMS Code
        if (e.getSmsIdentifierCode() != null) {
            dto.setSmsIdentifierCodeId(e.getSmsIdentifierCode().getId());
            dto.setSmsIdentifierCodeName(e.getSmsIdentifierCode().getSmsIdentifierCodeName());
        }

        // ✅ FIX: Convert Gregorian → Jalali
        PersianCalendarUtils converter = new PersianCalendarUtils();

        if (e.getAssigningDate() != null) {
            dto.setAssigningDateJalali(
                    converter.gregorianToJalali(e.getAssigningDate()).toString()
            );
        }

        if (e.getExpirationDate() != null) {
            dto.setExpirationDateJalali(
                    converter.gregorianToJalali(e.getExpirationDate()).toString()
            );
        }

        // Fees
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

        return dto;
    }
}
