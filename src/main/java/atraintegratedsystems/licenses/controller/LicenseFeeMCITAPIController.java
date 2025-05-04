package atraintegratedsystems.licenses.controller;
import atraintegratedsystems.licenses.dto.LicenseFeeIntegrationDTO;
import atraintegratedsystems.licenses.service.LicenseFeesIntegrationSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/licenses")
public class LicenseFeeMCITAPIController {

    @Autowired
    private LicenseFeesIntegrationSendService licenseFeesIntegrationSendService;

    @GetMapping("/unpaid-mcit")
    public ResponseEntity<List<LicenseFeeIntegrationDTO>> getPendingPayments() {
        List<LicenseFeeIntegrationDTO> pendingPayments = licenseFeesIntegrationSendService.getLicenseUnPaidList();
        return ResponseEntity.ok(pendingPayments);
    }
}

