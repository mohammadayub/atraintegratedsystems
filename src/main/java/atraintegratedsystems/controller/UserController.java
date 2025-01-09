package atraintegratedsystems.controller;

import atraintegratedsystems.dto.UserDTO;
import atraintegratedsystems.model.Organization;
import atraintegratedsystems.model.Role;
import atraintegratedsystems.service.OrganizationService;
import atraintegratedsystems.service.RoleService;
import atraintegratedsystems.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/licenses/admin/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserDTO());
        List<Role> roles = roleService.getAllRoles();
        List<Organization> organizations = organizationService.getAllOrganizations();
        model.addAttribute("availableRoles", roles);
        model.addAttribute("organizations", organizations);
        return "licenses/admin/registration";
    }

    @PostMapping("/licenses/admin/registration")
    public String registerUser(@ModelAttribute("user") UserDTO userDTO, Model model) {
        List<Role> roles = roleService.getAllRoles();
        List<Organization> organizations = organizationService.getAllOrganizations();
        model.addAttribute("availableRoles", roles);
        model.addAttribute("organizations", organizations);
        userService.registerUser(userDTO);
        model.addAttribute("successMessage", "User registered successfully!");
        return "licenses/admin/registration";
    }


}
