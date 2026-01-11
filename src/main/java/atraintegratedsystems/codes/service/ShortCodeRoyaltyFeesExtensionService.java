package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.ShortCodeRoyaltyFeesExtensionDTO;
import atraintegratedsystems.codes.model.CodeDetail;
import atraintegratedsystems.codes.model.ShortCodeRoylatyFeesExtension;
import atraintegratedsystems.codes.repository.CodeDetailRepository;
import atraintegratedsystems.codes.repository.ShortCodeRoyaltyFeesExtensionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ShortCodeRoyaltyFeesExtensionService {
    @Autowired
    private CodeDetailRepository codeDetailRepository;
    @Autowired
    private ShortCodeRoyaltyFeesExtensionRepository extensionRepository;


    public List<Object[]> findUnPaidRoyaltyFeeForExtension() {
        return codeDetailRepository.findUnPaidRoyaltyFeeForExtension();
    }

    public void saveRoyaltyFeeExtension(
            Long codeDetailId,
            ShortCodeRoyaltyFeesExtensionDTO dto
    ) {

        CodeDetail codeDetail = codeDetailRepository.findById(codeDetailId)
                .orElseThrow(() ->
                        new RuntimeException("Code Detail not found"));

        ShortCodeRoylatyFeesExtension extension =
                new ShortCodeRoylatyFeesExtension();

        // DTO → Entity
        extension.setRoyaltyFeeExtendedFees(dto.getRoyaltyFeeExtendedFees());
        extension.setRoyaltyFeeExtensionBankVoucherNo(
                dto.getRoyaltyFeeExtensionBankVoucherNo());
        extension.setRoyaltyFeeExtensionDate(
                dto.getRoyaltyFeeExtensionDate());
        extension.setRoyaltyFeeExtentionExpirationDate(
                dto.getRoyaltyFeeExtentionExpirationDate());
        extension.setRoyaltyFeeExtensionEnteryVoucherDate(
                dto.getRoyaltyFeeExtensionEnteryVoucherDate());
        extension.setRoyaltyFeeExtensionBankVoucherSubmissionDate(
                dto.getRoyaltyFeeExtensionBankVoucherSubmissionDate());
        extension.setRoyaltyFeeExtensionPaymentStatus(
                dto.getRoyaltyFeeExtensionPaymentStatus());

        // 🔥 SYSTEM VALUES (AUTOMATIC)
        extension.setExtendStatus("EXTENDED");
        extension.setExtendDate(LocalDate.now().toString());

        // 🔥 AUTO LINK
        extension.setCodeDetail(codeDetail);

        extensionRepository.save(extension);
    }




}
