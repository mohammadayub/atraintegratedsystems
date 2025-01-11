package atraintegratedsystems.service;

import atraintegratedsystems.dto.UserDTO;
import atraintegratedsystems.model.Organization;
import atraintegratedsystems.model.Role;
import atraintegratedsystems.model.User;
import atraintegratedsystems.repository.OrganizationRepository;
import atraintegratedsystems.repository.RoleRepository;
import atraintegratedsystems.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private UserDTO convertToDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setRoleIds(user.getRoles() != null
                ? user.getRoles().stream().map(Role::getId).collect(Collectors.toList())
                : new ArrayList<>()); // Ensure it's not null
        dto.setOrganizationName(user.getOrganization().getName());
        return dto;
    }

    public UserDTO registerUser(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        // Convert role IDs to Role entities
        List<Role> roles = userDTO.getRoleIds().stream()
                .map(roleId -> roleRepository.findById(roleId)
                        .orElseThrow(() -> new RuntimeException("Role not found")))
                .collect(Collectors.toList());
        user.setRoles(roles);
        // Convert organization ID to Organization entity
        Organization organization = organizationRepository.findById(userDTO.getOrganizationId())
                .orElseThrow(() -> new RuntimeException("Organization not found"));
        user.setOrganization(organization);
        user = userRepository.save(user);
        userDTO.setId(user.getId());
        return userDTO;
    }
}
