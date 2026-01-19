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
    @GetMapping("/licenses/index")
    public String getIndex(){
        return "/licenses/index";
    }
    @GetMapping("/index")
    public String getindexhome(){
        return "index";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_ADMIN') or hasRole('ROLE_LICENSE') or hasRole('ROLE_LICENSE_COMPLETION_PROFILE') or hasRole('ROLE_LICENSE_APPROVAL') or hasRole('ROLE_LICENSE_PROFILE_ENTRY')")
    @GetMapping("/licenses/license/home")
    public String getHome(){
        return "licenses/license/home";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_ADMIN') or hasRole('ROLE_FINANCE') or hasRole('ROLE_MINISTRY')")
    @GetMapping("/licenses/finance/home")
    public String getfinanceHome(){
        return "licenses/finance/home";
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_ADMIN') or hasRole('ROLE_LICENSE') or hasRole('ROLE_LICENSE_COMPLETION_PROFILE') or hasRole('ROLE_LICENSE_PROFILE_ENTRY')")
    @GetMapping("/licenses/new-applicant")
    public String getlicense_applicant(){
        return "licenses/license/license_new_applicant";
    }
    @GetMapping("licenses/finance/application/license_application_print_tariffs")
    public String licenseApplicationPrintTariff()
    {
        return "licenses/finance/application/license_application_print_tariffs";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_ADMIN') or hasRole('ROLE_LICENSE_APPROVAL')")
    @GetMapping("/licenses/license/approval/license_applicants_approval")
    public String licenseApplicantsApproval()
    {
        return "licenses/license/approval/license_applicants_approval";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_ADMIN') or hasRole('ROLE_FINANCE') or hasRole('ROLE_MINISTRY')")
    @GetMapping("/licenses/finance/license_finance")
    public String license_finance_home()
    {
        return "licenses/finance/license_finance/license_finance_home";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_ADMIN') or hasRole('ROLE_LICENSE')")
    @GetMapping("/licenses/license/report")
    public String license_reports()
    {
        return "licenses/license/report/home";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_ADMIN') or hasRole('ROLE_LICENSE') or hasRole('ROLE_LICENSE_COMPLETION_PROFILE')")
    @GetMapping("licenses/license/extension/extension_home")
    public String extension_home()
    {
        return "licenses/license/extension/extension_home";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_LICENSE_ADMIN') or hasRole('ROLE_FINANCE')")
    @GetMapping("licenses/finanace/extension/home")
    public String finance_extension_home()
    {

        return "licenses/finance/extension/home";
    }

    //Codes Management Section

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CODES_ADMIN')")
    @GetMapping("/Codes")
    public String codes_home()
    {
        return "codes/index";
    }

//    Type of Approval Management Section  typeofapprovals
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TYPEOFAPPROVAL_ADMIN') or hasRole('ROLE_TYPEOFAPPROVAL_ATRA_FINANCE') or hasRole('ROLE_TYPEOFAPPROVAL_MCIT_FINANCE') or hasRole('ROLE_TYPEOFAPPROVAL_STANDARD') or hasRole('ROLE_TYPEOFAPPROVAL_ONLINE_APPLICATION')")
    @GetMapping("typeofapprovals/home")
    public String typeofapprovals_home()
    {
        return "typeofapprovals/home";
    }

    @GetMapping("/finance/finance-report")
    public String finance()
    {
        return "licenses/license/report/finance/home";
    }

    @GetMapping("/license/license-report")
    public String license_report()
    {
        return "licenses/license/report/license/home";
    }

    @GetMapping("/codes/finance/home")
    public String shortCodeFinanceHome()
    {
        return "codes/finance/home";
    }

    @GetMapping("/codes/finance/extension/home")
    public String shortCodeFinanceExtensionHome()
    {
        return "codes/finance/extension/home";
    }

    @GetMapping("/codes/sms-identifier/home")
    public String smsIdentifierHome()
    {
        return "codes/smsidentifier/home";
    }

    @GetMapping("/codes/under-working")
    public String codesHome()
    {
        return "codes/working";
    }
}
