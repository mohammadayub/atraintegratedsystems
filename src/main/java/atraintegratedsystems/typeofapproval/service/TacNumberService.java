package atraintegratedsystems.typeofapproval.service;

import atraintegratedsystems.typeofapproval.model.TacNumber;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalManufacturerDetail;
import atraintegratedsystems.typeofapproval.repository.TacNumberRepository;
import atraintegratedsystems.typeofapproval.repository.TypeOfApprovalManufacturerDetailRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
        TypeOfApprovalManufacturerDetail manufacturer =
                manufacturerRepository.findById(manufacturerId)
                        .orElseThrow(() -> new RuntimeException("Manufacturer not found with id: " + manufacturerId));

        for (int i = from; i <= to; i++) {
            // ✅ use the correct repository method
            boolean exists = tacNumberRepository
                    .existsByTachNoAndTypeOfApprovalManufacturerDetail_Id(i, manufacturerId);

            if (exists) {
                throw new RuntimeException("TAC Number " + i + " already exists for this manufacturer!");
            }

            TacNumber tacNumber = new TacNumber();
            tacNumber.setTachNo(i);
            tacNumber.setTypeOfApprovalManufacturerDetail(manufacturer);

            manufacturer.getTacNumbers().add(tacNumber);
        }

        manufacturerRepository.save(manufacturer); // cascade saves TAC numbers too
    }

    public TypeOfApprovalManufacturerDetail getManufacturerById(Long id) {
        return manufacturerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manufacturer not found"));
    }
}
