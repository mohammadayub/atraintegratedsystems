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
}
