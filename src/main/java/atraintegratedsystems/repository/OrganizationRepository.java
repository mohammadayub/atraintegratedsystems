package atraintegratedsystems.repository;

import atraintegratedsystems.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization,Integer> {
}
