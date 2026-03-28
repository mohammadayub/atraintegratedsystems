package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.SmsIdentifierExtensionViewDTO;
import atraintegratedsystems.codes.repository.SmsIdentifierExtensionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SmsIdentifierExtensionPaidService {

    @Autowired
    private SmsIdentifierExtensionRepository repository;

    public List<SmsIdentifierExtensionViewDTO> getPaidExtensions() {
        return repository.findAllPaidExtensions()
                .stream()
                .map(this::fixNulls)
                .collect(Collectors.toList());
    }

    public SmsIdentifierExtensionViewDTO getById(Long id) {
        SmsIdentifierExtensionViewDTO dto = repository.findByDetailId(id);
        return dto != null ? fixNulls(dto) : null;
    }

    private SmsIdentifierExtensionViewDTO fixNulls(SmsIdentifierExtensionViewDTO dto) {
        if (dto == null) return null;

        if (dto.getAssigningDateJalali() == null)
            dto.setAssigningDateJalali("-");

        if (dto.getExpirationDateJalali() == null)
            dto.setExpirationDateJalali("-");

        if (dto.getExtensionStartDateJalali() == null)
            dto.setExtensionStartDateJalali("-");

        if (dto.getExtentionExpirationDateJalali() == null)
            dto.setExtentionExpirationDateJalali("-");

        return dto;
    }
}