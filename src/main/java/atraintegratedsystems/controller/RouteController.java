package atraintegratedsystems.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class RouteController {
    @GetMapping("/main")
    public String Shared_layout()
    {
        return "Main";
    }
    @GetMapping("/Registration")
    public String getRegistration(){
        return "RegistrationForm";
    }
    @GetMapping("/index")
    public String getIndex(){
        return "index";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE') or hasRole('ROLE_LICENSE_ENTRY') or hasRole('ROLE_LICENSE_APPROVAL') or hasRole('ROLE_LICENSE_APPLICANT_ENTRY')")
    @GetMapping("/licenses/license/home")
    public String getHome(){
        return "licenses/license/home";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_FINANCE') or hasRole('ROLE_MINISTRY')")
    @GetMapping("/licenses/finance/home")
    public String getfinanceHome(){
        return "licenses/finance/home";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE') or hasRole('ROLE_LICENSE_ENTRY') or hasRole('ROLE_LICENSE_APPLICANT_ENTRY')")
    @GetMapping("/licenses/new-applicant")
    public String getlicense_applicant(){
        return "licenses/license/license_new_applicant";
    }

    @GetMapping("licenses/finance/application/license_application_print_tariffs")
    public String licenseApplicationPrintTariff()
    {
        return "licenses/finance/application/license_application_print_tariffs";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_APPROVAL')")
    @GetMapping("/licenses/license/approval/license_applicants_approval")
    public String licenseApplicantsApproval()
    {
        return "licenses/license/approval/license_applicants_approval";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_FINANCE') or hasRole('ROLE_MINISTRY')")
    @GetMapping("/licenses/finance/license_finance")
    public String license_finance_home()
    {
        return "licenses/finance/license_finance/license_finance_home";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE')")
    @GetMapping("/licenses/license/report")
    public String license_reports()
    {
        return "licenses/license/report/home";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE') or hasRole('ROLE_LICENSE_ENTRY')")
    @GetMapping("licenses/license/extension/extension_home")
    public String extension_home()
    {
        return "licenses/license/extension/extension_home";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_FINANCE')")
    @GetMapping("licenses/finanace/extension/home")
    public String finance_extension_home()
    {

        return "licenses/finance/extension/home";
    }

}
