package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.IspcDetailDTO;
import atraintegratedsystems.codes.model.IspcCode;
import atraintegratedsystems.codes.model.IspcDetail;
import atraintegratedsystems.codes.repository.IspcCodeRepository;
import atraintegratedsystems.codes.repository.IspcDetailRepository;
import atraintegratedsystems.utils.DateConverter;
import atraintegratedsystems.utils.JalaliDate;
import atraintegratedsystems.utils.PersianCalendarUtils;
import atraintegratedsystems.utils.SerialGeneratorWithString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IspcDetailService {

    @Autowired
    private IspcDetailRepository detailRepository;

    @Autowired
    private IspcCodeRepository codeRepository;


    /* ================= CREATE ================= */

    public void save(IspcDetailDTO dto){

        IspcDetail entity;

        // ✅ HANDLE UPDATE
        if(dto.getId() != null){
            entity = detailRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Record not found"));
        }else{
            entity = new IspcDetail();
        }

        // ✅ GET CODE
        IspcCode code = codeRepository.findById(dto.getIspcCodeId())
                .orElseThrow(() -> new RuntimeException("ISPC Code not found"));

        // ✅ KEEP OLD SERIAL SAFE
        String existingSerial = entity.getSerialNumber();

        mapToEntity(dto, entity);

        entity.setIspcCode(code);
        code.setAssignStatus("Assign");

        /* ================= AUTO SERIAL (ONLY FOR NEW) ================= */

        if(entity.getId() == null &&
                (entity.getSerialNumber() == null || entity.getSerialNumber().trim().isEmpty())){

            SerialGeneratorWithString generator = new SerialGeneratorWithString();

            if(entity.getCompanyName() == null || entity.getExpirationDate() == null){
                throw new IllegalArgumentException("Company Name and Expiration Date required");
            }

            String codeName = entity.getIspcCode().getIspcCodeName();

            String serial = generator.generateSerialNumber(
                    entity.getCompanyName(),
                    entity.getExpirationDate(),
                    codeName
            );

            entity.setSerialNumber(serial);
        }

        // ✅ RESTORE SERIAL IF LOST DURING UPDATE
        if(existingSerial != null && entity.getSerialNumber() == null){
            entity.setSerialNumber(existingSerial);
        }

        detailRepository.save(entity);
        codeRepository.save(code);
    }


    /* ================= READ ALL ================= */

    public List<IspcDetailDTO> getAllDetails() {
        return detailRepository.findAllNotRejected()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /* ================= READ BY ID ================= */

    public IspcDetailDTO getById(Long id){

        return mapToDTO(
                detailRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Record not found"))
        );
    }


    /* ================= DELETE ================= */

    public void delete(Long id){

        IspcDetail detail = detailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        IspcCode code = detail.getIspcCode();

        if(code != null){
            code.setAssignStatus("Unassign");
            codeRepository.save(code);
        }

        detailRepository.delete(detail);
    }


    /* ================= ENTITY MAPPER ================= */

    private void mapToEntity(IspcDetailDTO dto, IspcDetail e){

        if(dto.getSerialNumber() != null && !dto.getSerialNumber().trim().isEmpty()){
            e.setSerialNumber(dto.getSerialNumber());
        }
        e.setCompanyName(dto.getCompanyName());
        e.setCompanyNameInDari(dto.getCompanyNameInDari());
        e.setEnid(dto.getEnid());
        e.setLocation(dto.getLocation());
        e.setCompanyAddress(dto.getCompanyAddress());
        e.setResponsiblePerson(dto.getResponsiblePerson());
        e.setJob(dto.getJob());
        e.setMobile(dto.getMobile());
        e.setTelephone(dto.getTelephone());
        e.setEmail(dto.getEmail());
        e.setIspcNumber(dto.getIspcNumber());
        e.setSignalingPoint(dto.getSignalingPoint());
        e.setRegistrationFees(50000);
        e.setRemark(dto.getRemark());

        PersianCalendarUtils converter = new PersianCalendarUtils();


        /* ================= Assigning Date ================= */

        if(dto.getAssigningDateJalali()!=null && !dto.getAssigningDateJalali().isEmpty()){

            String[] parts = dto.getAssigningDateJalali().split("-");

            int y = Integer.parseInt(parts[0]);
            int m = Integer.parseInt(parts[1]);
            int d = Integer.parseInt(parts[2]);

            LocalDate date = converter.jalaliToGregorian(y,m,d);

            e.setAssigningDate(date);
        }


        /* ================= Expiration Date ================= */

        if(dto.getExpirationDateJalali()!=null && !dto.getExpirationDateJalali().isEmpty()){

            String[] parts = dto.getExpirationDateJalali().split("-");

            int y = Integer.parseInt(parts[0]);
            int m = Integer.parseInt(parts[1]);
            int d = Integer.parseInt(parts[2]);

            LocalDate date = converter.jalaliToGregorian(y,m,d);

            e.setExpirationDate(date);
        }

    }


    /* ================= DTO MAPPER ================= */

    private IspcDetailDTO mapToDTO(IspcDetail e){

        IspcDetailDTO dto = new IspcDetailDTO();

        dto.setId(e.getId());
        dto.setSerialNumber(e.getSerialNumber());
        dto.setCompanyName(e.getCompanyName());
        dto.setCompanyNameInDari(e.getCompanyNameInDari());
        dto.setEnid(e.getEnid());
        dto.setLocation(e.getLocation());
        dto.setCompanyAddress(e.getCompanyAddress());
        dto.setResponsiblePerson(e.getResponsiblePerson());
        dto.setJob(e.getJob());
        dto.setMobile(e.getMobile());
        dto.setTelephone(e.getTelephone());
        dto.setEmail(e.getEmail());
        dto.setIspcNumber(e.getIspcNumber());
        dto.setSignalingPoint(e.getSignalingPoint());
        dto.setRemark(e.getRemark());

        if(e.getIspcCode()!=null){
            dto.setIspcCodeId(e.getIspcCode().getId());
            dto.setIspcCodeName(e.getIspcCode().getIspcCodeName());
        }

        DateConverter converter = new DateConverter();

        /* Assigning Date */
        if(e.getAssigningDate() != null){

            JalaliDate jd = converter.gregorianToJalali(
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
        if(e.getExpirationDate() != null){

            JalaliDate jd = converter.gregorianToJalali(
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