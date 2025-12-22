package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.model.ShortCodeSerialNumber;
import atraintegratedsystems.codes.repository.ShortCodeSerialNumberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShortCodeSerialNumberService {

    private final ShortCodeSerialNumberRepository repository;

    // CREATE & UPDATE
    public ShortCodeSerialNumber save(ShortCodeSerialNumber serialNumber) {
        return repository.save(serialNumber);
    }

    // READ ALL
    public List<ShortCodeSerialNumber> findAll() {
        return repository.findAll();
    }

    // READ BY ID
    public ShortCodeSerialNumber findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    // ✅ ADD THIS
    public void markAsAssigned(ShortCodeSerialNumber serialNumber) {
        serialNumber.setStatus("Assigned");
        repository.save(serialNumber);
    }

    // DELETE
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
