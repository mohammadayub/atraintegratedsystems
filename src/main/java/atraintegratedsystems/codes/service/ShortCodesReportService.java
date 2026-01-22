package atraintegratedsystems.codes.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class ShortCodesReportService {

    @GetMapping("/codes/shortcode/generalreport")
    public String shortCodeGeneralReportlist()
    {

        return "codes/reports/short_codes_general_report";
    }
}
