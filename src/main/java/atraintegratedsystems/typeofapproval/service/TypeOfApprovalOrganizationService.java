package atraintegratedsystems.typeofapproval.service;

import atraintegratedsystems.typeofapproval.dto.TypeOfApprovalOrganizationDTO;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalOrganization;
import atraintegratedsystems.typeofapproval.repository.TypeOfApprovalOrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TypeOfApprovalOrganizationService {

    private final TypeOfApprovalOrganizationRepository repository;

    // ✅ Get all organizations (DTO list)
    @Transactional(readOnly = true)
    public List<TypeOfApprovalOrganizationDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ✅ Get organization by ID (DTO)
    @Transactional(readOnly = true)
    public Optional<TypeOfApprovalOrganizationDTO> findById(int id) {
        return repository.findById(id)
                .map(this::convertToDTO);
    }

    // ✅ Save or update an organization
    @Transactional
    public TypeOfApprovalOrganizationDTO save(TypeOfApprovalOrganizationDTO dto) {
        TypeOfApprovalOrganization entity = convertToEntity(dto);
        TypeOfApprovalOrganization saved = repository.save(entity);
        return convertToDTO(saved);
    }

    // ✅ Delete organization
    @Transactional
    public void deleteById(int id) {
        repository.deleteById(id);
    }

    // ✅ Check if name already exists
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }

    // ------------------------------
    // Helper mapping methods
    // ------------------------------
    private TypeOfApprovalOrganizationDTO convertToDTO(TypeOfApprovalOrganization org) {
        return new TypeOfApprovalOrganizationDTO(org.getId(), org.getName());
    }

    private TypeOfApprovalOrganization convertToEntity(TypeOfApprovalOrganizationDTO dto) {
        TypeOfApprovalOrganization entity = new TypeOfApprovalOrganization();
        if (dto.getId() > 0) entity.setId(dto.getId()); // prevent overwriting when creating new
        entity.setName(dto.getName());
        return entity;
    }
}
