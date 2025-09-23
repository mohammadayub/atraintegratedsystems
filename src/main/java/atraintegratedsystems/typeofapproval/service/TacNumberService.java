package atraintegratedsystems.typeofapproval.service;

import atraintegratedsystems.typeofapproval.model.TacNumber;
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
                            TypeOfApprovalTechnicalDetailsRepository technicalDetailRepository) {
        this.tacNumberRepository = tacNumberRepository;
        this.technicalDetailRepository = technicalDetailRepository;
    }

    @Transactional
    public void saveTacNumberRange(Long technicalId, int from, int to) {
        if (from > to) {
            throw new IllegalArgumentException("'from' must be less than or equal to 'to'");
        }

        // ✅ Load technical detail
        TypeOfApprovalTechnicalDetail technicalDetail = technicalDetailRepository
                .findById(technicalId)
                .orElseThrow(() -> new EntityNotFoundException("Technical Detail not found with ID: " + technicalId));

// ✅ Generate TAC numbers with dashes
        List<String> formattedTacNumbers = new ArrayList<>();
        for (int i = from; i <= to; i++) {
            formattedTacNumbers.add(technicalDetail.getBrandName() + "-" + technicalDetail.getModelNumber() + "-" + i);
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

        // ✅ Save in batch
        tacNumberRepository.saveAll(newTacNumbers);
    }

    public TypeOfApprovalTechnicalDetail getTechnicalDetailById(Long id) {
        return technicalDetailRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Technical Detail not found with ID: " + id));
    }

    public Integer getNextTacNo() {
        String latestTac = tacNumberRepository.findLatestTacNo();
        if (latestTac == null) {
            return 1; // start from 1 if no TAC exists
        }

        try {
            // e.g. "Atra-BrandName-ModelNumber-200"
            String[] parts = latestTac.split("-");
            String lastPart = parts[parts.length - 1]; // "200"
            int lastNumber = Integer.parseInt(lastPart);
            return lastNumber + 1;
        } catch (NumberFormatException e) {
            // fallback if something goes wrong with parsing
            throw new IllegalStateException("Invalid TAC format found: " + latestTac, e);
        }
    }


    public Page<TacNumber> getAllTacNumbersWithTechnicalDetail(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return tacNumberRepository.findAllWithTechnicalDetail(pageable);
    }

    public List<TacNumber> searchTacNumbers(String keyword) {
        return tacNumberRepository.searchAll(keyword);
    }
}
