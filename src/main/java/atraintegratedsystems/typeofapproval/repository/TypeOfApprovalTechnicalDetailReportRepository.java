package atraintegratedsystems.typeofapproval.repository;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalTechnicalDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeOfApprovalTechnicalDetailReportRepository
        extends CrudRepository<TypeOfApprovalTechnicalDetail, Long> {

    @Query(value = "SELECT td.id, td.gsm, td.cdma, td.lte, td.tetra, td.amateur_radio, " +
            "td.private_mobile_radio, td.pmr_radio, td.radar, td.rlan, td.wimax, td.fwa, " +
            "td.microwave, td.sound_broadcasting, td.tv_broadcasting, td.cordless_phone, td.srd, td.rfid, " +
            "td.satellite_radio, td.radio_navigation, td.satellite_tv, td.vsat, td.other, td.intended_use, " +
            "td.model_number, td.brand_name, td.type_number, td.countryof_origin, td.frequencyrange_frommhz, " +
            "td.frequencyrange_tomhz, td.frequencyrange_fromghz, td.frequencyrange_toghz, td.output_power_radiated_conducted, " +
            "td.transmission_capacity, td.channel_capacity, td.channel_spacing, td.modulation_type, td.antenna_type, " +
            "td.antenna_gain, td.technical_interface, td.technical_variants, td.equipment_license_requirement, " +
            "td.entered_by, td.entered_date, app.company_name " +
            "FROM type_of_approval_technical_detail td " +
            "JOIN type_of_approval_applicant app ON td.type_of_approval_applicant_id = app.id " +
            "WHERE app.refer_status = 'Yes' " +
            "AND app.application_fee_status = 'Yes' " +
            "AND app.admin_fee_status = 'Yes' " +
            "AND app.certificate_fee_status = 'Yes'",
            nativeQuery = true)
    List<Object[]> findAllApprovedTechnicalDetailsRaw();
}
