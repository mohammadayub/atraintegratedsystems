package atraintegratedsystems.controller;

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
    @GetMapping("/licenses/license/home")
    public String getHome(){
        return "licenses/license/home";
    }
    @GetMapping("/licenses/finance/home")
    public String getfinanceHome(){
        return "licenses/finance/home";
    }
    @GetMapping("/licenses/new-applicant")
    public String getlicense_applicant(){
        return "licenses/license/license_new_applicant";
    }
    @GetMapping("licenses/finance/application/license_application_print_tariffs")
    public String licenseApplicationPrintTariff()
    {
        return "licenses/finance/application/license_application_print_tariffs";
    }

    @GetMapping("/licenses/license/approval/license_applicants_approval")
    public String licenseApplicantsApproval()
    {
        return "licenses/license/approval/license_applicants_approval";
    }
//    @GetMapping("/administrationfees")
//    public String administration()
//    {
//        return "licenses/finance/administration_fees/administration_fees";
//    }
    @GetMapping("/licenses/finance/license_finance")
   public String license_finance_home()
{

    return "licenses/finance/license_finance/license_finance_home";
}

    @GetMapping("/licenses/license/report")
    public String license_reports()
    {
        return "licenses/license/report/home";
    }
}
