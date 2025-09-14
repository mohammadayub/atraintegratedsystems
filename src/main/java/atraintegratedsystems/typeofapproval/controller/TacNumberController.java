package atraintegratedsystems.typeofapproval.controller;

import atraintegratedsystems.typeofapproval.model.TacNumber;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalManufacturerDetail;
import atraintegratedsystems.typeofapproval.service.TacNumberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/typeofapprovals/tacnumber")
public class TacNumberController {

    private final TacNumberService tacNumberService;

    public TacNumberController(TacNumberService tacNumberService) {
        this.tacNumberService = tacNumberService;
    }

    // Open form for entering TAC number
    @GetMapping("/add-tachnumber/edit/{id}")
    public String showAddTacNumberForm(@PathVariable("id") Long manufacturerId, Model model) {
        TypeOfApprovalManufacturerDetail manufacturer = tacNumberService.getManufacturerById(manufacturerId);
        model.addAttribute("manufacturer", manufacturer);
        model.addAttribute("tacNumber", new TacNumber());
        model.addAttribute("tacNumbers", manufacturer.getTacNumbers()); // list of existing TACs
        return "typeofapprovals/tacnumber/add-tachnumber-form";
    }

    // Save TAC number
    @PostMapping("/save/{id}")
    public String saveTacNumbers(@PathVariable("id") Long manufacturerId,
                                 @RequestParam("from") int from,
                                 @RequestParam("to") int to) {
        tacNumberService.saveTacNumberRange(manufacturerId, from, to);
        return "redirect:/typeofapprovals/tacnumber/add-tachnumber/edit/" + manufacturerId;
    }
}
