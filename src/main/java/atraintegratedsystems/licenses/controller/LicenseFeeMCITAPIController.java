package atraintegratedsystems.licenses.controller;


import atraintegratedsystems.licenses.model.LicenseApproval;
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
    @GetMapping("/unpaid-mcit")
    public ResponseEntity<List<LicenseApproval>> getUnpaidApprovedLicenses() {
        List<LicenseApproval> unpaidApprovedLicenses = licenseFeesSendToExternalSystemApiService.getAllData();
        return ResponseEntity.ok(unpaidApprovedLicenses);
    }
}
