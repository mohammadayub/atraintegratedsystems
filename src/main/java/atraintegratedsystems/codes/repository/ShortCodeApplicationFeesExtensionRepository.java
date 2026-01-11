package atraintegratedsystems.codes.repository;

import atraintegratedsystems.codes.model.ShortCodeApplicationFeesExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShortCodeApplicationFeesExtensionRepository extends JpaRepository<ShortCodeApplicationFeesExtension,Long> {

    List<ShortCodeApplicationFeesExtension>
    findByApplicationFeeExtensionPaymentStatusIsNull();
}
