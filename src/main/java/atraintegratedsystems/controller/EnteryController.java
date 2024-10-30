package atraintegratedsystems.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EnteryController {
    @GetMapping("/registeration")
    public String DataEntry()
    {
        return "RegisterationForm";
    }

}
