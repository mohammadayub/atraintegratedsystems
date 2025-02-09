package atraintegratedsystems.controller;

import atraintegratedsystems.dto.UserDTO;
import atraintegratedsystems.model.Organization;
import atraintegratedsystems.model.Role;
import atraintegratedsystems.model.User;
import atraintegratedsystems.service.OrganizationService;
import atraintegratedsystems.service.RoleService;
import atraintegratedsystems.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/licenses/admin/userlists")
    public String showUsersForm(Model model) {
        List<UserDTO> users = userService.getAllUsers();
        List<Role> roles = roleService.getAllRoles();
        List<Organization> organizations = organizationService.getAllOrganizations();

        model.addAttribute("users", users);
        model.addAttribute("availableRoles", roles);
        model.addAttribute("organizations", organizations);
        return "licenses/admin/userlists";
    }


    @GetMapping("/licenses/admin/registration/add")
    public String getRegistrationForm(Model model) {
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
        return "redirect:/licenses/admin/userlists";

    }


    @GetMapping("/licenses/admin/change-password/{id}")
    public String showChangePasswordForm(@PathVariable("id") int userId, Model model) {
        model.addAttribute("userId", userId);
        return "licenses/admin/change-password";
    }

    @PostMapping("/licenses/admin/change-password/{id}")
    public String changePassword(@PathVariable("id") int userId,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Model model) {
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("errorMessage", "Passwords do not match.");
            return "licenses/admin/change-password";
        }

        User user = userService.getUserById(userId);
        if (user == null) {
            model.addAttribute("errorMessage", "User not found.");
            return "licenses/admin/change-password";
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userService.updateUser(user);

        model.addAttribute("successMessage", "Password changed successfully.");
        return "licenses/admin/change-password";
    }


}
