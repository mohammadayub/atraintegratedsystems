package atraintegratedsystems.typeofapproval.service;

import atraintegratedsystems.typeofapproval.dto.TypeOfApprovalTechnicalDetailDTO;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalApplicant;
import atraintegratedsystems.typeofapproval.repository.TypeOfApprovalApplicantRepository;
import atraintegratedsystems.typeofapproval.repository.TypeOfApprovalTechnicalDetailReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class GeneralReportService {
    @Autowired
    private TypeOfApprovalApplicantRepository typeOfApprovalApplicantRepository;

    @Autowired
    private TypeOfApprovalTechnicalDetailReportRepository technicalRepository;


    public List<TypeOfApprovalApplicant>getAllTypeofApproval()
    {
        return typeOfApprovalApplicantRepository.findAllApplicants();
    }


//    Company Service Report

    public List<TypeOfApprovalTechnicalDetailDTO> getAllApprovedTechnicalDetails() {
        List<Object[]> results = technicalRepository.findAllApprovedTechnicalDetailsRaw();
        List<TypeOfApprovalTechnicalDetailDTO> details = new ArrayList<>();

        for (Object[] row : results) {
            TypeOfApprovalTechnicalDetailDTO dto = new TypeOfApprovalTechnicalDetailDTO();
            dto.setId(((Number)row[0]).longValue());
            dto.setGsm((String)row[1]);
            dto.setCdma((String)row[2]);
            dto.setLte((String)row[3]);
            dto.setTetra((String)row[4]);
            dto.setAmateurRadio((String)row[5]);
            dto.setPrivateMobileRadio((String)row[6]);
            dto.setPmrRadio((String)row[7]);
            dto.setRadar((String)row[8]);
            dto.setRlan((String)row[9]);
            dto.setWimax((String)row[10]);
            dto.setFwa((String)row[11]);
            dto.setMicrowave((String)row[12]);
            dto.setSoundBroadcasting((String)row[13]);
            dto.setTvBroadcasting((String)row[14]);
            dto.setCordlessPhone((String)row[15]);
            dto.setSrd((String)row[16]);
            dto.setRfid((String)row[17]);
            dto.setSatelliteRadio((String)row[18]);
            dto.setRadioNavigation((String)row[19]);
            dto.setSatelliteTv((String)row[20]);
            dto.setVsat((String)row[21]);
            dto.setOther((String)row[22]);
            dto.setIntendedUse((String)row[23]);
            dto.setModelNumber((String)row[24]);
            dto.setBrandName((String)row[25]);
            dto.setTypeNumber((String)row[26]);
            dto.setCountryofOrigin((String)row[27]);
            dto.setFrequencyrangeFromMHZ((String)row[28]);
            dto.setFrequencyrangeToMHZ((String)row[29]);
            dto.setFrequencyrangeFromGHZ((String)row[30]);
            dto.setFrequencyrangeToGHZ((String)row[31]);
            dto.setOutputPowerRadiatedConducted((String)row[32]);
            dto.setTransmissionCapacity((String)row[33]);
            dto.setChannelCapacity((String)row[34]);
            dto.setChannelSpacing((String)row[35]);
            dto.setModulationType((String)row[36]);
            dto.setAntennaType((String)row[37]);
            dto.setAntennaGain((String)row[38]);
            dto.setTechnicalInterface((String)row[39]);
            dto.setTechnicalVariants((String)row[40]);
            dto.setEquipmentLicenseRequirement((String)row[41]);
            dto.setEnteredBy((String)row[42]);
            dto.setEnteredDate(row[43] != null ? ((java.sql.Date)row[43]).toLocalDate() : null);
            dto.setCompanyName((String)row[44]);
            details.add(dto);
        }
        return details;
    }

}