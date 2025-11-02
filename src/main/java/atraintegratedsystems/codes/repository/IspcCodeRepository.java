package atraintegratedsystems.codes.repository;
import atraintegratedsystems.codes.model.IspcCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IspcCodeRepository extends JpaRepository<IspcCode,Integer> {


    @Query(value = "SELECT ispc_code.ispc_code,ispc_code.ispc_code|| ' - ' || " +
           "CASE WHEN ispc_detail.ispc_code IS NULL THEN 'unassigned' ELSE 'assign' END AS IspcCode " +
             "FROM ispc_code " +
             "LEFT JOIN ispc_detail ON ispc_code.ispc_code = ispc_detail.ispc_code",
            nativeQuery = true)
    List<Object[]> getIspcCodes();


    @Query(value = "SELECT ispc_code.ispc_code,  " +
            "       CASE WHEN ispc_detail.ispc_code IS NULL THEN 'unassigned' ELSE 'assign' END AS assignment  " +
            "FROM ispc_code " +
            "LEFT JOIN ispc_detail ON ispc_code.ispc_code = ispc_detail.ispc_code", nativeQuery = true)
    List<Object[]> getCodeData();



}
