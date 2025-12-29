package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.dto.CodeDetailDTO;
import atraintegratedsystems.codes.model.CodeDetail;
import atraintegratedsystems.codes.repository.CodeDetailRepository;
import atraintegratedsystems.codes.repository.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CodesDetailPaymentsConfirmationService {
    @Autowired
    private CodeDetailRepository codeDetailRepository;

    @Autowired
    private CodeRepository codeRepository;

    // find uppaid application fees
    public List<Object[]> getunPaidApplicationFees() {
        return codeDetailRepository.findunPaidApplicationFee();
    }

    // application fee tariff related

//    public Object[] getApplicationFeeTariff(Long id) {
//        Object result = codeDetailRepository.findApplicationFeeById(id);
//        return result != null ? (Object[]) result : null;
//    }

    //    public Object[] getApplicationFeeTariff(Long id) {
//        return codeDetailRepository.findApplicationFeeById(id);
//    }
// ✅ Load unpaid application fee for edit
    public Optional<CodeDetail> getUnpaidApplicationFeeForEdit(Long id) {
        return codeDetailRepository.findUnpaidApplicationFeeById(id);
    }

    // Confirm application fee

//    @Transactional
//    public boolean confirmApplicationFee(
//            Long id,
//            String voucherNo,
//            String submissionDate
//    ) {
//        int updated = codeDetailRepository.confirmApplicationFeePayment(
//                id,
//                voucherNo,
//                submissionDate
//        );
//        return updated > 0;
//    }
//@Transactional
//public boolean confirmApplicationFee(
//        Long id,
//        String voucherNo,
//        String enterDate,
//        String submissionDate
//) {
//    int updated = codeDetailRepository.confirmApplicationFeePayment(
//            id,
//            voucherNo,
//            enterDate,
//            submissionDate
//    );
//    return updated > 0;
//}

    @Transactional
    public boolean confirmApplicationFee(CodeDetailDTO dto) {

        int updated = codeDetailRepository.confirmApplicationFeePayment(
                dto.getId(),
                dto.getApplicationFeebankVoucherNo(),
                dto.getApplicationFeeEnterVoucherDate(),
                dto.getApplicationFeebankVoucherSubmissionDate()
        );

        return updated > 0;
    }


//    Royalty Fee Section

    public List<Object[]> getunPaidRyaltyFees() {
        return codeDetailRepository.findunPaidRoyaltyFee();
    }

    // Royalty Fee Print Tariffs Section

    public Optional<CodeDetail> getUnRoyaltyFeeForEdit(Long id) {
        return codeDetailRepository.findUnpaidRoyaltyFeeById(id);
    }

    // Royalty Fees Confirmation Section
    @Transactional
    public boolean confirmRoyaltyFee(CodeDetailDTO dto) {

        int updated = codeDetailRepository.confirmRoyaltyFeePayment(
                dto.getId(),
                dto.getRoyaltyFeebankVoucherNo(),
                dto.getRoyaltyFeeEnterVoucherDate(),
                dto.getRoyaltyFeeBankVoucherSubmissionDate()
        );

        return updated > 0;
    }

    // Royalty Fee Extension section
    public List<Object[]> getUnPaidRyaltyFeesForExtension() {
        return codeDetailRepository.findUnPaidRoyaltyFeeForExtension();
    }

    // Application Fee Extension Section
    public List<Object[]> getUnPaidApplicationFeesForExtension() {
        return codeDetailRepository.findUnPaidApplicationFeeForExtension();
    }
}
