package atraintegratedsystems.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Layout {
    @GetMapping("/main")
    public String Shared_layout()
    {
        return "Main";
    }
}
