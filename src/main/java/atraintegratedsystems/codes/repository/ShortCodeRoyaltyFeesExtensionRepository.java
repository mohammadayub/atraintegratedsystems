package atraintegratedsystems.codes.repository;

import atraintegratedsystems.codes.model.ShortCodeRoylatyFeesExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShortCodeRoyaltyFeesExtensionRepository extends JpaRepository<ShortCodeRoylatyFeesExtension,Long> {
    // (Optional – future safety)
    boolean existsByCodeDetail_Id(Long codeDetailId);

    List<ShortCodeRoylatyFeesExtension>
    findByRoyaltyFeeExtensionPaymentStatusIsNull();

}
