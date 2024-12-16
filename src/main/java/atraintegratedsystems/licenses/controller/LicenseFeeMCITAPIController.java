package atraintegratedsystems.licenses.controller;
import atraintegratedsystems.licenses.service.LicenseFeesSendToExternalSystemApiService;
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
    private LicenseFeesSendToExternalSystemApiService licenseFeesSendToExternalSystemApiService;
//    @GetMapping("/unpaid-mcit")
//    public ResponseEntity<List<LicenseApprovalMcitFeesAPIDTO>> getPendingPayments() {
//        List<LicenseApprovalMcitFeesAPIDTO> pendingPayments = licenseFeesSendToExternalSystemApiService.getAPIData();
//        return ResponseEntity.ok(pendingPayments);
//    }
}
