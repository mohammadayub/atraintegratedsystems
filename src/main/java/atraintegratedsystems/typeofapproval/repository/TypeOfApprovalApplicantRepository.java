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

    @Query(value = "SELECT request_date, manufacturer, person, license_operator, authorized_importer, import_registration_no, company_name, contact_person, address, p_o_box, telephone, mobile, email FROM type_of_approval_applicant", nativeQuery = true)
    List<Object[]> findAllApplicantDetails();

    @Query(value = "SELECT * FROM type_of_approval_applicant where refer_status='Yes'", nativeQuery = true)
    List<TypeOfApprovalApplicant> findAllReferred();

}
