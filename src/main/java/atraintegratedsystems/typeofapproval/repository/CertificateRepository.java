package atraintegratedsystems.typeofapproval.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalApplicant;

public interface CertificateRepository extends CrudRepository<TypeOfApprovalApplicant, Long> {

//    @Query(value =
//            "SELECT "
//                    +
//                    "a.type_of_approval_applicant_number AS applicantNumber, " +
//                    "a.company_name AS applicantName, " +
//                    "a.address AS address, " +
//                    "a.telephone AS telephone, " +
//                    "a.email AS email, " +
//                    "t.model_number AS modelNumber, " +
//                    "t.brand_name AS brandName, " +
//                    "t.type_number AS typeNumber, " +
//                    "t.countryof_origin AS countryOfOrigin, " +
//                    "CONCAT(t.frequencyrange_frommhz, '-', t.frequencyrange_tomhz, ' MHz') AS frequencyRange, " +
//                    "t.output_power_radiated_conducted AS outputPower, " +
//                    "t.transmission_capacity AS transmissionCapacity, " +
//                    "MIN(n.tac_no) AS firstTacNumber, " +
//                    "MAX(n.tac_no) AS lastTacNumber " +
//                    "FROM type_of_approval_applicant a " +
//                    "JOIN type_of_approval_technical_detail t ON t.type_of_approval_applicant_id = a.id " +
//                    "JOIN tac_number n ON n.technical_detail_id = t.id " +
//                    "WHERE a.id = :companyId " +
//                    "AND a.application_fee_status = 'Yes' " +
//                    "AND a.admin_fee_status = 'Yes' " +
//                    "AND a.certificate_fee_status = 'Yes' " +
//                    "AND a.refer_status = 'Yes' " +
//                    "GROUP BY a.company_name, a.address, a.telephone, a.email, " +
//                    "t.model_number, t.brand_name, t.type_number, t.countryof_origin, " +
//                    "t.frequencyrange_frommhz, t.frequencyrange_tomhz, " +
//                    "t.output_power_radiated_conducted, t.transmission_capacity",
//            nativeQuery = true)
//    List<Object[]> findApprovedCertificatesByCompanyId(Long companyId);

    @Query(value =
            "SELECT " +
                    "a.type_of_approval_applicant_number AS applicantNumber, " +
                    "a.company_name AS applicantName, " +
                    "a.address AS address, " +
                    "a.telephone AS telephone, " +
                    "a.email AS email, " +
                    "t.model_number AS modelNumber, " +
                    "t.brand_name AS brandName, " +
                    "t.type_number AS typeNumber, " +
                    "t.countryof_origin AS countryOfOrigin, " +
                    "CONCAT(t.frequencyrange_frommhz, '-', t.frequencyrange_tomhz, ' MHz') AS frequencyRange, " +
                    "t.output_power_radiated_conducted AS outputPower, " +
                    "t.transmission_capacity AS transmissionCapacity, " +
                    "MIN(n.tac_no) AS firstTacNumber, " +
                    "MAX(n.tac_no) AS lastTacNumber " +
                    "FROM type_of_approval_applicant a " +
                    "JOIN type_of_approval_technical_detail t ON t.type_of_approval_applicant_id = a.id " +
                    "JOIN tac_number n ON n.technical_detail_id = t.id " +
                    "WHERE a.id = :companyId " +
                    "AND a.application_fee_status = 'Yes' " +
                    "AND a.admin_fee_status = 'Yes' " +
                    "AND a.certificate_fee_status = 'Yes' " +
                    "AND a.refer_status = 'Yes' " +
                    "GROUP BY a.type_of_approval_applicant_number, a.company_name, a.address, a.telephone, a.email, " +
                    "t.model_number, t.brand_name, t.type_number, t.countryof_origin, " +
                    "t.frequencyrange_frommhz, t.frequencyrange_tomhz, " +
                    "t.output_power_radiated_conducted, t.transmission_capacity",
            nativeQuery = true)
    List<Object[]> findApprovedCertificatesByCompanyId(Long companyId);


}
