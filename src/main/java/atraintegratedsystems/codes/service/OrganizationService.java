package atraintegratedsystems.codes.service;


import atra.erp.model.Organization;
import atra.erp.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationService {

    @Autowired
    public OrganizationRepository organizationRepository;

    public List<Organization> getAllOrganizations(){

        return  organizationRepository.findAll();

    }
}
