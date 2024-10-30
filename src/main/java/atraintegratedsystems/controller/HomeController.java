package atraintegratedsystems.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/License/Reg2")
public class HomeController {

    @GetMapping
    public String index(Model model) {
        model.addAttribute("title", "Home Page");
        return "Licenses/licenseapplications";
    }
}