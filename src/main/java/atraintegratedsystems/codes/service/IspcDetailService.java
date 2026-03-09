package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.IspcDetailDTO;
import atraintegratedsystems.codes.model.IspcCode;
import atraintegratedsystems.codes.model.IspcDetail;
import atraintegratedsystems.codes.repository.IspcCodeRepository;
import atraintegratedsystems.codes.repository.IspcDetailRepository;
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

        IspcCode code = codeRepository.findById(dto.getIspcCodeId())
                .orElseThrow(() -> new RuntimeException("ISPC Code not found"));

        IspcDetail entity = new IspcDetail();

        mapToEntity(dto, entity);

        code.setAssignStatus("Assign");

        entity.setIspcCode(code);


        /* ================= AUTO SERIAL ================= */

        SerialGeneratorWithString generator = new SerialGeneratorWithString();

        if(entity.getSerialNumber() == null || entity.getSerialNumber().trim().isEmpty()){

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

        detailRepository.save(entity);
        codeRepository.save(code);
    }


    /* ================= READ ALL ================= */

    public List<IspcDetailDTO> getAllDetails(){

        return detailRepository.findAll()
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

        e.setCompanyName(dto.getCompanyName());
        e.setEnid(dto.getEnid());
        e.setCompanyAddress(dto.getCompanyAddress());
        e.setResponsiblePerson(dto.getResponsiblePerson());
        e.setJob(dto.getJob());
        e.setMobile(dto.getMobile());
        e.setTelephone(dto.getTelephone());
        e.setEmail(dto.getEmail());
        e.setChannel(dto.getChannel());
        e.setServiceType(dto.getServiceType());
        e.setMnosCompanyHost(dto.getMnosCompanyHost());
        e.setCodeCategory(dto.getCodeCategory());

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
        dto.setEnid(e.getEnid());
        dto.setCompanyAddress(e.getCompanyAddress());
        dto.setResponsiblePerson(e.getResponsiblePerson());
        dto.setJob(e.getJob());
        dto.setMobile(e.getMobile());
        dto.setTelephone(e.getTelephone());
        dto.setEmail(e.getEmail());
        dto.setChannel(e.getChannel());
        dto.setServiceType(e.getServiceType());
        dto.setMnosCompanyHost(e.getMnosCompanyHost());
        dto.setCodeCategory(e.getCodeCategory());

        if(e.getIspcCode()!=null){
            dto.setIspcCodeId(e.getIspcCode().getId());
            dto.setIspcCodeName(e.getIspcCode().getIspcCodeName());
        }

        dto.setAssigningDateJalali(
                e.getAssigningDateJalali()!=null
                        ? e.getAssigningDateJalali().toString()
                        : null
        );

        dto.setExpirationDateJalali(
                e.getExpirationDateJalali()!=null
                        ? e.getExpirationDateJalali().toString()
                        : null
        );

        return dto;
    }
}