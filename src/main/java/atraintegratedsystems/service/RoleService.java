package atraintegratedsystems.service;

import atraintegratedsystems.model.Role;
import atraintegratedsystems.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public Optional<Role> getRoleById(int id){
        return roleRepository.findById(id);
    }

    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }
}
