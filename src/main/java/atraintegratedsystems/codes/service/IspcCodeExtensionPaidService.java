package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.IspcExtensionViewDTO;
import atraintegratedsystems.codes.repository.IspcExtensionDetailRepository;
import atraintegratedsystems.utils.DateConverter;
import atraintegratedsystems.utils.JalaliDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IspcCodeExtensionPaidService {

    @Autowired
    private IspcExtensionDetailRepository ispcExtensionDetailRepository;


    /* ================= GET ALL ================= */

    public List<IspcExtensionViewDTO> getActiveIspcDetailCode() {

        return ispcExtensionDetailRepository.findAllPaidExtensionDetails()
                .stream()
                .map(this::convertDates)
                .collect(Collectors.toList());
    }


    /* ================= GET BY ID ================= */

    public IspcExtensionViewDTO getById(Long id) {

        IspcExtensionViewDTO dto =
                ispcExtensionDetailRepository.findByIdForPrint(id);

        return convertDates(dto);
    }


    /* ================= DATE CONVERSION ================= */

    private IspcExtensionViewDTO convertDates(IspcExtensionViewDTO dto){

        if(dto == null) return null;

        DateConverter converter = new DateConverter();

        /* ===== Assigning Date ===== */
        if(dto.getAssigningDate() != null){

            JalaliDate jd = converter.gregorianToJalali(
                    dto.getAssigningDate().getYear(),
                    dto.getAssigningDate().getMonthValue(),
                    dto.getAssigningDate().getDayOfMonth()
            );

            dto.setAssigningDate(
                    dto.getAssigningDate() // keep original if needed
            );
        }

        /* ===== Expiration Date ===== */
        if(dto.getExpirationDate() != null){

            JalaliDate jd = converter.gregorianToJalali(
                    dto.getExpirationDate().getYear(),
                    dto.getExpirationDate().getMonthValue(),
                    dto.getExpirationDate().getDayOfMonth()
            );

            dto.setExpirationDate(
                    dto.getExpirationDate()
            );
        }

        /* ===== Extension Start Date (JALALI) ===== */
        if(dto.getExtensionStartDate() != null){

            JalaliDate jd = converter.gregorianToJalali(
                    dto.getExtensionStartDate().getYear(),
                    dto.getExtensionStartDate().getMonthValue(),
                    dto.getExtensionStartDate().getDayOfMonth()
            );

            dto.setExtensionStartDateJalali(
                    String.format("%04d-%02d-%02d",
                            jd.getYear(),
                            jd.getMonthPersian().getValue(),
                            jd.getDay())
            );
        }

        /* ===== Extension Expiration Date (JALALI) ===== */
        if(dto.getExtentionExpirationDate() != null){

            JalaliDate jd = converter.gregorianToJalali(
                    dto.getExtentionExpirationDate().getYear(),
                    dto.getExtentionExpirationDate().getMonthValue(),
                    dto.getExtentionExpirationDate().getDayOfMonth()
            );

            dto.setExtentionExpirationDateJalali(
                    String.format("%04d-%02d-%02d",
                            jd.getYear(),
                            jd.getMonthPersian().getValue(),
                            jd.getDay())
            );
        }

        return dto;
    }

}