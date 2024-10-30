package atraintegratedsystems.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LicenseProfilesController {

    @GetMapping("/license/licenseprofile")
    public String getLicenseProfile(){
      return "licenses/licenseprofile" ;
    }
}
