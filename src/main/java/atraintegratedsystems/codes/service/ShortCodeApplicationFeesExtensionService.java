package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.ShortCodeApplicationFeesExtensionDTO;
import atraintegratedsystems.codes.model.CodeDetail;
import atraintegratedsystems.codes.model.ShortCodeApplicationFeesExtension;
import atraintegratedsystems.codes.repository.CodeDetailRepository;
import atraintegratedsystems.codes.repository.ShortCodeApplicationFeesExtensionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ShortCodeApplicationFeesExtensionService {

    @Autowired
    private CodeDetailRepository codeDetailRepository;

    @Autowired
    private ShortCodeApplicationFeesExtensionRepository extensionRepository;

    /* =========================
       LIST UNPAID EXTENSIONS
       ========================= */
    public List<Object[]> findUnPaidApplicationFeeForExtension() {
        return codeDetailRepository.findUnPaidApplicationFeeForExtension();
    }

    /* =========================
       GET CODE DETAIL
       ========================= */
    public CodeDetail getCodeDetailById(Long id) {
        return codeDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CodeDetail not found with id: " + id));
    }

    /* =========================
       SAVE EXTENSION USING DTO
       ========================= */
    public void saveExtension(Long codeDetailId, ShortCodeApplicationFeesExtensionDTO dto) {

        CodeDetail codeDetail = getCodeDetailById(codeDetailId);

        ShortCodeApplicationFeesExtension entity = new ShortCodeApplicationFeesExtension();

        // DTO → Entity
        entity.setApplicationFeeExtensionDate(dto.getApplicationFeeExtensionDate());
        entity.setApplicationFeeExtentionExpirationDate(dto.getApplicationFeeExtentionExpirationDate());
        entity.setApplicationFeeExtendedFees(dto.getApplicationFeeExtendedFees());
        entity.setApplicationFeeExtensionBankVoucherNo(dto.getApplicationFeeExtensionBankVoucherNo());
        entity.setApplicationFeeExtensionEnteryVoucherDate(dto.getApplicationFeeExtensionEnteryVoucherDate());
        entity.setApplicationFeeExtensionBankVoucherSubmissionDate(dto.getApplicationFeeExtensionBankVoucherSubmissionDate());
        entity.setApplicationFeeExtensionPaymentStatus(dto.getApplicationFeeExtensionPaymentStatus());

        // ✅ Automatic fields
        entity.setExtendStatus("EXTENDED");
        entity.setExtendDate(LocalDate.now().toString());

        // Relation
        entity.setCodeDetail(codeDetail);

        extensionRepository.save(entity);
    }
}
