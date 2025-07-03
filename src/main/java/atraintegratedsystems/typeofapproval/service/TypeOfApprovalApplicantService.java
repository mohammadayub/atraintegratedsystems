package atraintegratedsystems.typeofapproval.service;

import atraintegratedsystems.typeofapproval.model.TypeOfApprovalApplicant;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalAttachment;
import atraintegratedsystems.typeofapproval.repository.TypeOfApprovalApplicantRepository;
import atraintegratedsystems.typeofapproval.repository.TypeOfApprovalAttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TypeOfApprovalApplicantService {

    @Autowired
    private TypeOfApprovalApplicantRepository typeOfApprovalApplicantRepository;


    @Transactional
    public List<Object[]> getAllTypeOfApprovalApplicants()
    {
      return typeOfApprovalApplicantRepository.findAllApplicantDetails();
    }

    @Transactional
    public List<TypeOfApprovalApplicant>getAllTypeofApproval()
    {
        return typeOfApprovalApplicantRepository.findAllApplicants();
    }

    @Transactional
    public List<TypeOfApprovalApplicant> getAllReferred(){
        return typeOfApprovalApplicantRepository.findAllReferred();
    }

    @Transactional
    public Optional<TypeOfApprovalApplicant> getById(Long id){
        return typeOfApprovalApplicantRepository.findById(id);
    }

    public void updateReferFinance(Long id, LocalDate referDate, String referStatus) {
        TypeOfApprovalApplicant existing = typeOfApprovalApplicantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Applicant not found with ID: " + id));

        existing.setReferDate(referDate);
        existing.setReferStatus(referStatus);

        // Do NOT touch or replace existing.getDetails() to avoid orphanRemoval issues

        typeOfApprovalApplicantRepository.save(existing);
    }


    public void updateApplicationFee(Long id, String applicationFeeStatus, String applicationFeeBankVoucherNo
    , LocalDate applicationFeeVoucherDate , LocalDate applicationFeeSubmissionDate){
        TypeOfApprovalApplicant appFee= typeOfApprovalApplicantRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Applicant not found with ID: " + id));
        String applicationFeeEnteredBy = SecurityContextHolder.getContext().getAuthentication().getName();
        appFee.setApplicationFeeStatus(applicationFeeStatus);
        appFee.setApplicationFeeBankVoucherNo(applicationFeeBankVoucherNo);
        appFee.setApplicationFeeVoucherDate(applicationFeeVoucherDate);
        appFee.setApplicationFeeEntryDate(LocalDate.now());
        appFee.setApplicationFeeEnteredBy(applicationFeeEnteredBy);
        appFee.setApplicationFeeBankVoucherSubmissionDate(applicationFeeSubmissionDate);

        typeOfApprovalApplicantRepository.save(appFee);



    }

    public void updateAdminFee(Long id, String adminFeeStatus, String adminFeeBankVoucherNo
            , LocalDate adminFeeVoucherDate , LocalDate adminFeeSubmissionDate){
        TypeOfApprovalApplicant adminFee= typeOfApprovalApplicantRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Applicant not found with ID: " + id));
        String adminFeeEnteredBy = SecurityContextHolder.getContext().getAuthentication().getName();
        adminFee.setAdminFeeStatus(adminFeeStatus);
        adminFee.setAdminFeeBankVoucherNo(adminFeeBankVoucherNo);
        adminFee.setAdminFeeVoucherDate(adminFeeVoucherDate);
        adminFee.setAdminFeeEntryDate(LocalDate.now());
        adminFee.setAdminFeeEnteredBy(adminFeeEnteredBy);
        adminFee.setAdminFeeBankVoucherSubmissionDate(adminFeeSubmissionDate);

        typeOfApprovalApplicantRepository.save(adminFee);

    }

    public void updateCertificateFee(Long id, String certificateFeeStatus, String certificateFeeBankVoucherNo
            , LocalDate certificateFeeVoucherDate , LocalDate certificateFeeSubmissionDate){
        TypeOfApprovalApplicant certificateFee= typeOfApprovalApplicantRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Applicant not found with ID: " + id));
        String certificateFeeEnteredBy = SecurityContextHolder.getContext().getAuthentication().getName();
        certificateFee.setCertificateFeeStatus(certificateFeeStatus);
        certificateFee.setCertificateFeeBankVoucherNo(certificateFeeBankVoucherNo);
        certificateFee.setCertificateFeeVoucherDate(certificateFeeVoucherDate);
        certificateFee.setCertificateFeeEntryDate(LocalDate.now());
        certificateFee.setCertificateFeeEnteredBy(certificateFeeEnteredBy);
        certificateFee.setCertificateFeeBankVoucherSubmissionDate(certificateFeeSubmissionDate);

        typeOfApprovalApplicantRepository.save(certificateFee);

    }






}
