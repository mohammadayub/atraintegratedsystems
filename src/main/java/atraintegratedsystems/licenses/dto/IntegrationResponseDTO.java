package atraintegratedsystems.licenses.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IntegrationResponseDTO {
    private String externalApiMessage;
    private List<LicenseFeeIntegrationDTO> sentRecords;
}
