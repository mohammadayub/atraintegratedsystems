package atraintegratedsystems.codes.controller;

import atraintegratedsystems.codes.repository.ShortCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ISPCController {

    @Autowired
    private ShortCodeRepository shortCodeRepository;


}
