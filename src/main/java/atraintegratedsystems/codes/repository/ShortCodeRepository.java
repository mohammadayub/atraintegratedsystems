package atraintegratedsystems.codes.repository;

import atraintegratedsystems.codes.model.ShortCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShortCodeRepository extends JpaRepository<ShortCode, Long> {

    boolean existsByShortCodeName(Integer shortCodeName);
    List<ShortCode> findByAssignStatusEqualsOrAssignStatusIsNullOrderByShortCodeNameAsc(String status);
    @Query(
            value = "SELECT sc.short_code, " +
                    "CASE WHEN scd.short_code IS NULL THEN 'unassigned' ELSE 'assigned' END AS assignment, " +
                    "scd.source_used, scd.category, scd.name_of_responsible_person, " +
                    "scd.mobile_number_of_responsible_person, scd.id_card_number_of_responsible_person " +
                    "FROM short_code sc " +
                    "LEFT JOIN short_code_detail scd " +
                    "ON sc.short_code = scd.short_code " +
                    "AND scd.code_status IS NULL",
            nativeQuery = true
    )
    List<Object[]> getCodeDetails();

    @Query("SELECT DISTINCT s FROM ShortCode s ORDER BY s.shortCodeName ASC")
    List<ShortCode> findDistinctShortCodes();

//    @Query(
//            value = "SELECT sc.short_code, " +
//                    "sc.short_code || ' - ' || " +
//                    "CASE WHEN scd.short_code IS NULL THEN 'unassigned' ELSE 'assign' END AS ShortCodes " +
//                    "FROM short_code sc " +
//                    "LEFT JOIN short_code_detail scd ON sc.short_code = scd.short_code",
//            nativeQuery = true
//    )
//    List<Object[]> getShortCodes();



    @Query(
            value = "SELECT sc.short_code AS shortCode, " +
                    "sc.short_code || ' - ' || " +
                    "CASE WHEN EXISTS (" +
                    "    SELECT 1 FROM short_code_detail scd " +
                    "    WHERE scd.short_code = sc.short_code" +
                    ") THEN 'assign' ELSE 'unassigned' END AS shortCodeStatus " +
                    "FROM short_code sc " +
                    "ORDER BY sc.short_code",
            nativeQuery = true
    )
    List<Object[]> getShortCodes();














    @Query(
            value = "SELECT sc.short_code, " +
                    "CASE WHEN scd.short_code IS NULL THEN 'unassigned' ELSE 'assign' END AS assignment " +
                    "FROM short_code sc " +
                    "LEFT JOIN short_code_detail scd ON sc.short_code = scd.short_code",
            nativeQuery = true
    )
    List<Object[]> getCodeData();
}
