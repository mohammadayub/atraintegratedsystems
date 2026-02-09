package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.SmsIdentifierSerialNumberDTO;
import atraintegratedsystems.codes.model.SmsIdentifierSerialNumber;
import atraintegratedsystems.codes.repository.SmsIdentifierSerialNumberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SmsIdentifierSerialNumberService {

    private final SmsIdentifierSerialNumberRepository repository;

    public SmsIdentifierSerialNumberService(SmsIdentifierSerialNumberRepository repository) {
        this.repository = repository;
    }

    // ===================== ENTITY -> DTO =====================
    private SmsIdentifierSerialNumberDTO mapToDTO(SmsIdentifierSerialNumber entity) {
        SmsIdentifierSerialNumberDTO dto = new SmsIdentifierSerialNumberDTO();
        dto.setSerialNumber(entity.getSerialNumber());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    // ===================== DTO -> ENTITY =====================
    private SmsIdentifierSerialNumber mapToEntity(SmsIdentifierSerialNumberDTO dto) {
        SmsIdentifierSerialNumber entity = new SmsIdentifierSerialNumber();
        entity.setSerialNumber(dto.getSerialNumber());
        entity.setStatus(null); // ALWAYS NULL
        return entity;
    }

    // LIST
    public List<SmsIdentifierSerialNumberDTO> findAll() {
        List<SmsIdentifierSerialNumber> entities = repository.findAll();
        List<SmsIdentifierSerialNumberDTO> dtoList = new ArrayList<>();

        for (SmsIdentifierSerialNumber entity : entities) {
            dtoList.add(mapToDTO(entity));
        }
        return dtoList;
    }

    // SAVE (CREATE + UPDATE)
    public void save(SmsIdentifierSerialNumberDTO dto) {
        SmsIdentifierSerialNumber entity = mapToEntity(dto);
        repository.save(entity);
    }

    // FIND BY ID
    public SmsIdentifierSerialNumberDTO findById(Long id) {
        Optional<SmsIdentifierSerialNumber> optional = repository.findById(id);
        return optional.map(this::mapToDTO).orElse(null);
    }

    // DELETE
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
