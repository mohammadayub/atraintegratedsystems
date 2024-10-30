package atraintegratedsystems.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FinanceController {

    @GetMapping("/finance/licensepayment")
    public String getFinance(){
        return "finance/licensepayment";
    }
}
