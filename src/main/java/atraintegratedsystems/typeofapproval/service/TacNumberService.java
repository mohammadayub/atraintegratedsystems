package atraintegratedsystems.typeofapproval.service;

import atraintegratedsystems.typeofapproval.model.TacNumber;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalManufacturerDetail;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalTechnicalDetail;
import atraintegratedsystems.typeofapproval.repository.TacNumberRepository;
import atraintegratedsystems.typeofapproval.repository.TypeOfApprovalTechnicalDetailsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TacNumberService {

    private final TacNumberRepository tacNumberRepository;
    private final TypeOfApprovalTechnicalDetailsRepository technicalDetailRepository;


    public TacNumberService(TacNumberRepository tacNumberRepository,
                            TypeOfApprovalTechnicalDetailsRepository technicalRepository ){
        this.tacNumberRepository = tacNumberRepository;
        this.technicalDetailRepository = technicalRepository;
    }

    @Transactional
    public void saveTacNumberRange(Long technicalId, int from, int to) {
        if (from > to) {
            throw new IllegalArgumentException("'from' must be less than or equal to 'to'");
        }

        // ✅ Load manufacturer
        TypeOfApprovalTechnicalDetail technicalDetail = technicalDetailRepository
                .findById(technicalId)
                .orElseThrow(() -> new EntityNotFoundException("Technical Detail not found with ID: " + technicalId));


        // ✅ Generate TAC numbers
        List<String> formattedTacNumbers = new ArrayList<>();
        for (int i = from; i <= to; i++) {
            formattedTacNumbers.add("ATRA-TAC-" + i);
        }

        // ✅ Check duplicates in DB
        List<String> existingTacNumbers =
                tacNumberRepository.findExistingTacNumbersInRange(technicalId, formattedTacNumbers);

        if (!existingTacNumbers.isEmpty()) {
            throw new IllegalStateException("The following TAC numbers already exist: " + existingTacNumbers);
        }

        // ✅ Create new TacNumbers
        List<TacNumber> newTacNumbers = new ArrayList<>();
        for (String tacNo : formattedTacNumbers) {
            TacNumber tacNumber = new TacNumber();
            tacNumber.setTacNo(tacNo);
            tacNumber.setTypeOfApprovalTechnicalDetail(technicalDetail);
            newTacNumbers.add(tacNumber);
        }

        // ✅ Save in batch directly (no cascade, no huge re-save of manufacturer)
        tacNumberRepository.saveAll(newTacNumbers);
    }

    public TypeOfApprovalTechnicalDetail getTechnicalDetailById(Long id) {
        return technicalDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Technical Detail not found"));
    }

    public Integer getNextTacNo() {
        String latestTac = tacNumberRepository.findLatestTacNo();
        if (latestTac == null) {
            return 1; // start from 1 if no TAC exists
        }
        int lastNumber = Integer.parseInt(latestTac.replace("ATRA-TAC-", ""));
        return lastNumber + 1;
    }

    public Page<TacNumber> getAllTacNumbersWithManufacturer(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return tacNumberRepository.findAllWithTechnicalDetail(pageable);
    }

    public List<TacNumber> searchTacNumbers(String keyword) {
        return tacNumberRepository.searchAll(keyword);
    }

}