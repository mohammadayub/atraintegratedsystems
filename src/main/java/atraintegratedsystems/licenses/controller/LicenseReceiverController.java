package atraintegratedsystems.licenses.controller;


import atraintegratedsystems.licenses.dto.LicenseFeeIntegrationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/integration")
public class LicenseReceiverController {

    @PostMapping("/receive")
    public ResponseEntity<?> receive(@RequestBody List<LicenseFeeIntegrationDTO> data) {
        data.forEach(dto -> System.out.println("Received: " + dto.getId()));
        return ResponseEntity.ok("Received " + data.size() + " records");
    }
}

