package atraintegratedsystems.licenses.controller;

import atraintegratedsystems.licenses.dto.LicenseDatabaseFeesExtensionDTO;
import atraintegratedsystems.licenses.model.LicenseDatabaseFeesExtension;
import atraintegratedsystems.licenses.service.LicenseDatabaseFeesExtensionFinanceService;
import atraintegratedsystems.utils.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class LicenseDatabaseFeeExtensionFinanceController {
    @Autowired
    private LicenseDatabaseFeesExtensionFinanceService licenseDatabaseFeesExtensionFinanceService;

    @GetMapping("/licenses/finance/extension/database_fees_extension/license_database_fees")
    public String showApplicationProfile(Model model) {
        List<LicenseDatabaseFeesExtension> profiles = licenseDatabaseFeesExtensionFinanceService.getAllDatabaseFeesExtension();
        model.addAttribute("profiles", profiles);
        return "licenses/finance/extension/database_fees_extension/license_database_fees";
    }

}
