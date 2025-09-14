package atraintegratedsystems.typeofapproval.service;

import atraintegratedsystems.typeofapproval.dto.TypeOfApprovalManufacturerDetailDTO;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalManufacturerDetail;
import atraintegratedsystems.typeofapproval.repository.TypeOfApprovalManufacturerDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TypeOfApprovalManufacturerDetailService {

    private final TypeOfApprovalManufacturerDetailRepository repository;

    // Get all manufacturer details
    public List<TypeOfApprovalManufacturerDetailDTO> getAllManufacturers() {
        return repository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Convert Entity -> DTO
    private TypeOfApprovalManufacturerDetailDTO convertToDTO(TypeOfApprovalManufacturerDetail entity) {
        TypeOfApprovalManufacturerDetailDTO dto = new TypeOfApprovalManufacturerDetailDTO();
        dto.setId(entity.getId());
        dto.setCompanyName(entity.getCompanyName());
        dto.setAuthorizedImporter(entity.getAuthorizedImporter());
        dto.setContactPerson(entity.getContactPerson());
        dto.setAddress(entity.getAddress());
        dto.setManufacturer_P_O_Box(entity.getManufacturer_P_O_Box());
        dto.setManufacturer_telephone(entity.getManufacturer_telephone());
        dto.setManufacturer_mobile(entity.getManufacturer_mobile());
        dto.setManufacturer_email(entity.getManufacturer_email());
        dto.setEnteredBy(entity.getEnteredBy());
        dto.setEnteredDate(entity.getEnteredDate() != null ? entity.getEnteredDate().toString() : null);
        return dto;
    }

    // Convert DTO -> Entity
    private TypeOfApprovalManufacturerDetail convertToEntity(TypeOfApprovalManufacturerDetailDTO dto) {
        TypeOfApprovalManufacturerDetail entity = new TypeOfApprovalManufacturerDetail();
        entity.setId(dto.getId());
        entity.setCompanyName(dto.getCompanyName());
        entity.setAuthorizedImporter(dto.getAuthorizedImporter());
        entity.setContactPerson(dto.getContactPerson());
        entity.setAddress(dto.getAddress());
        entity.setManufacturer_P_O_Box(dto.getManufacturer_P_O_Box());
        entity.setManufacturer_telephone(dto.getManufacturer_telephone());
        entity.setManufacturer_mobile(dto.getManufacturer_mobile());
        entity.setManufacturer_email(dto.getManufacturer_email());
        entity.setEnteredBy(dto.getEnteredBy());
        if (dto.getEnteredDate() != null && !dto.getEnteredDate().isEmpty()) {
            entity.setEnteredDate(LocalDate.parse(dto.getEnteredDate()));
        }
        return entity;
    }
}

