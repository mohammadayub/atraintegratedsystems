package atraintegratedsystems.typeofapproval.repository;

import atraintegratedsystems.licenses.model.LicenseApplicant;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalApplicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeOfApprovalApplicantRepository extends JpaRepository<TypeOfApprovalApplicant,Long> {

    boolean existsByCompanyName(String companyName);

    @Query(value = "SELECT * FROM typeofapproval_applicants", nativeQuery = true)
    List<TypeOfApprovalApplicant> findAllApplicants();

}
