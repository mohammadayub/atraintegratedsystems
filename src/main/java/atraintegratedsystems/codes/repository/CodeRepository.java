package atraintegratedsystems.codes.repository;
import atraintegratedsystems.codes.model.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CodeRepository extends JpaRepository<Code, Integer> {

    @Query(value = "SELECT c.short_code, " +
            "CASE WHEN cd.short_code IS NULL THEN 'unassigned' ELSE 'assigned' END AS assignment , cd.source_used, cd.category, cd.name_of_responsible_person , cd.mobile_number_of_responsible_person ,cd.id_card_number_of_responsible_person " +
            "FROM code c " +
            "LEFT JOIN code_detail cd ON c.short_code = cd.short_code",
            nativeQuery = true)
    List<Object[]> getCodeDetails();



    @Query(value = "SELECT code.short_code, code.short_code || ' - ' || " +
            "CASE WHEN code_detail.short_code IS NULL THEN 'unassigned' ELSE 'assign' END AS ShortCodes " +
            "FROM code " +
            "LEFT JOIN code_detail ON code.short_code = code_detail.short_code",
            nativeQuery = true)
    List<Object[]> getShortCodes();



    @Query(value = "SELECT code.short_code, " +
            "       CASE WHEN code_detail.short_code IS NULL THEN 'unassigned' ELSE 'assign' END AS assignment " +
            "FROM code " +
            "LEFT JOIN code_detail ON code.short_code = code_detail.short_code", nativeQuery = true)
    List<Object[]> getCodeData();




















}
