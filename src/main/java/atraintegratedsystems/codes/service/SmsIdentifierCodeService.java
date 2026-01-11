package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.SmsIdentifierCodeDTO;
import atraintegratedsystems.codes.model.SmsIdentifierCode;
import atraintegratedsystems.codes.repository.SmsIdentifierCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SmsIdentifierCodeService {

    @Autowired
    private SmsIdentifierCodeRepository repository;

    /* CREATE */
    public void save(SmsIdentifierCodeDTO dto) {

        if (repository.existsBySmsIdentifierCodeName(dto.getSmsIdentifierCodeName())) {
            throw new RuntimeException("SMS Identifier Code already exists!");
        }

        SmsIdentifierCode entity = new SmsIdentifierCode();
        entity.setSmsIdentifierCodeName(dto.getSmsIdentifierCodeName());
        entity.setAssignStatus(dto.getAssignStatus());

        repository.save(entity);
    }

    /* READ ALL */
    public List<SmsIdentifierCodeDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /* READ BY ID */
    public SmsIdentifierCodeDTO findById(Long id) {
        SmsIdentifierCode entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Record not found"));
        return convertToDTO(entity);
    }

    /* UPDATE */
    public void update(Long id, SmsIdentifierCodeDTO dto) {

        SmsIdentifierCode entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Record not found"));

        if (!entity.getSmsIdentifierCodeName().equals(dto.getSmsIdentifierCodeName())
                && repository.existsBySmsIdentifierCodeName(dto.getSmsIdentifierCodeName())) {
            throw new RuntimeException("SMS Identifier Code already exists!");
        }

        entity.setSmsIdentifierCodeName(dto.getSmsIdentifierCodeName());
        entity.setAssignStatus(dto.getAssignStatus());

        repository.save(entity);
    }

    /* DELETE */
    public void delete(Long id) {
        repository.deleteById(id);
    }

    /* Mapper */
    private SmsIdentifierCodeDTO convertToDTO(SmsIdentifierCode entity) {
        SmsIdentifierCodeDTO dto = new SmsIdentifierCodeDTO();
        dto.setId(entity.getId());
        dto.setSmsIdentifierCodeName(entity.getSmsIdentifierCodeName());
        dto.setAssignStatus(entity.getAssignStatus());
        dto.setEnteredDate(entity.getEnteredDate());
        return dto;
    }
}
