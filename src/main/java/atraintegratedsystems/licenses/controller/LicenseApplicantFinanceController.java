package atraintegratedsystems.licenses.controller;

import atraintegratedsystems.licenses.model.LicenseApplicant;
import atraintegratedsystems.licenses.service.LicenseApplicantFinanceService;
import atraintegratedsystems.licenses.service.LicenseApplicantService;
import atraintegratedsystems.licenses.service.LicenseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LicenseApplicantFinanceController {

    @Autowired
    private LicenseApplicantFinanceService licenseApplicantFinanceService;

    @Autowired
    private LicenseTypeService licenseTypeService;

    @GetMapping("/licenses/finance/application/license_application_fee_list")
    public String showApplicationProfile(Model model) {
        List<LicenseApplicant> profiles = licenseApplicantFinanceService.getAllUnpaid();
        model.addAttribute("profiles", profiles);
        model.addAttribute("licenseTypes", licenseTypeService.findAll());
        return "licenses/finance/application/license_application_fee_list";
    }
}
