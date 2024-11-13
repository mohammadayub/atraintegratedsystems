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
    @GetMapping("license/application/tariff")
    public String licenseApplicationPrintTariff()
    {
        return "licenses/finance/application/license_application_print_tariffs";
    }

}
