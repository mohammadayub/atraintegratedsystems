// LicenseIntegrationController.java
package atraintegratedsystems.licenses.controller;
import atraintegratedsystems.licenses.dto.IntegrationResponseDTO;
import atraintegratedsystems.licenses.dto.LicenseFeeIntegrationDTO;
import atraintegratedsystems.licenses.service.LicenseIntegrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/integration")
@RequiredArgsConstructor
public class LicenseIntegrationController {

    @Autowired
    private LicenseIntegrationService service;
    private final RestTemplate restTemplate;

    @Value("${external.api.url}")
    private String externalApiUrl;

    @PostMapping("/send")
    public ResponseEntity<IntegrationResponseDTO> sendPendingPayments() {
        List<LicenseFeeIntegrationDTO> pending = service.getPendingPayments();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<LicenseFeeIntegrationDTO>> request = new HttpEntity<>(pending, headers);

        // Send to external API
        ResponseEntity<String> response = restTemplate.postForEntity(externalApiUrl, request, String.class);

        // Return message and JSON
        IntegrationResponseDTO result = new IntegrationResponseDTO(response.getBody(), pending);
        return ResponseEntity.ok(result);
    }

}
