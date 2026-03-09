package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.IspcDetailDTO;
import atraintegratedsystems.codes.model.IspcDetail;
import atraintegratedsystems.codes.repository.IspcDetailRepository;
import atraintegratedsystems.utils.PersianCalendarUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class IspcRejectionService {

    @Autowired
    private IspcDetailRepository detailRepository;

    /* ================= REJECT ISPC ================= */

    public void reject(IspcDetailDTO dto){

        if(dto.getId() == null){
            throw new RuntimeException("Invalid record id");
        }

        IspcDetail detail = detailRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Record not found"));

        // Set rejection status
        detail.setIspcCodeRejectionStatus("Reject");

        PersianCalendarUtils converter = new PersianCalendarUtils();

        /* ================= Rejection Date ================= */

        if(dto.getIspcCodeRejectionDateJalali() != null &&
                !dto.getIspcCodeRejectionDateJalali().trim().isEmpty()){

            String[] parts = dto.getIspcCodeRejectionDateJalali().trim().split("-");

            if(parts.length == 3){

                int y = Integer.parseInt(parts[0]);
                int m = Integer.parseInt(parts[1]);
                int d = Integer.parseInt(parts[2]);

                LocalDate gregorianDate = converter.jalaliToGregorian(y, m, d);

                detail.setIspcCodeRejectionDate(gregorianDate);
            }
        }

        detailRepository.save(detail);
    }
}