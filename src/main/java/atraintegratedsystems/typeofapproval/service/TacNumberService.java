package atraintegratedsystems.typeofapproval.service;

import atraintegratedsystems.typeofapproval.model.TacNumber;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalManufacturerDetail;
import atraintegratedsystems.typeofapproval.repository.TacNumberRepository;
import atraintegratedsystems.typeofapproval.repository.TypeOfApprovalManufacturerDetailRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TacNumberService {

    private final TacNumberRepository tacNumberRepository;
    private final TypeOfApprovalManufacturerDetailRepository manufacturerRepository;

    public TacNumberService(TacNumberRepository tacNumberRepository,
                            TypeOfApprovalManufacturerDetailRepository manufacturerRepository) {
        this.tacNumberRepository = tacNumberRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    @Transactional
    public void saveTacNumberRange(Long manufacturerId, int from, int to) {
        if (from > to) {
            throw new IllegalArgumentException("'from' must be less than or equal to 'to'");
        }

        TypeOfApprovalManufacturerDetail manufacturer = manufacturerRepository.findById(manufacturerId)
                .orElseThrow(() -> new EntityNotFoundException("Manufacturer not found with ID: " + manufacturerId));

        // Fetch all existing TAC numbers in the range for this manufacturer in a single DB query
        List<Integer> existingTacNumbers = tacNumberRepository.findExistingTacNumbersInRange(manufacturerId, from, to);

        if (!existingTacNumbers.isEmpty()) {
            throw new IllegalStateException("The following TAC numbers already exist for this manufacturer: "
                    + existingTacNumbers);
        }

        List<TacNumber> newTacNumbers = new ArrayList<>();
        for (int i = from; i <= to; i++) {
            TacNumber tacNumber = new TacNumber();
            tacNumber.setTachNo(i);
            tacNumber.setTypeOfApprovalManufacturerDetail(manufacturer);
            newTacNumbers.add(tacNumber);
        }

        // Add all new TAC numbers to the manufacturer (assuming cascade is set)
        manufacturer.getTacNumbers().addAll(newTacNumbers);

        manufacturerRepository.save(manufacturer); // Save manufacturer and cascade TAC numbers
    }

    public TypeOfApprovalManufacturerDetail getManufacturerById(Long id) {
        return manufacturerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manufacturer not found"));
    }
}
