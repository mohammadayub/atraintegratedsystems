package atraintegratedsystems.licenses.repository;

import atraintegratedsystems.licenses.model.LicenseApproval;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicenseApprovalRepository extends JpaRepository<LicenseApproval,Long> {
}
