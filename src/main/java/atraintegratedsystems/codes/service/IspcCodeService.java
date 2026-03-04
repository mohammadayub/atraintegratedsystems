package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.model.IspcCode;
import atraintegratedsystems.codes.repository.IspcCodeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IspcCodeService {

    private final IspcCodeRepository repository;

    public IspcCodeService(IspcCodeRepository repository) {
        this.repository = repository;
    }

    // CREATE
    public IspcCode save(IspcCode code) {
        return repository.save(code);
    }

    // READ ALL
    public List<IspcCode> findAll() {
        return repository.findAll();
    }

    // READ BY ID
    public IspcCode findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ISPC Code not found"));
    }

    // UPDATE
    public IspcCode update(Long id, IspcCode updatedCode) {
        IspcCode existing = findById(id);

        existing.setIspcCodeName(updatedCode.getIspcCodeName());
        existing.setAssignStatus(updatedCode.getAssignStatus());

        return repository.save(existing);
    }

    // DELETE
    public void delete(Long id) {
        repository.deleteById(id);
    }
}