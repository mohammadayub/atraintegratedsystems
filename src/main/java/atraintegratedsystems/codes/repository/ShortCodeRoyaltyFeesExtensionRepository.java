package atraintegratedsystems.codes.repository;

import atraintegratedsystems.codes.model.ShortCodeRoylatyFeesExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShortCodeRoyaltyFeesExtensionRepository
        extends JpaRepository<ShortCodeRoylatyFeesExtension, Long> {

    boolean existsByShortCodeDetail_Id(Long shortCodeDetailId);

    List<ShortCodeRoylatyFeesExtension>
    findByRoyaltyFeeExtensionPaymentStatusIsNull();
}
