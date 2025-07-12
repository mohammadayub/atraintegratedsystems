package atraintegratedsystems.typeofapproval.service;

import atraintegratedsystems.licenses.model.LicenseApproval;
import atraintegratedsystems.typeofapproval.dto.TypeOfApprovalApplicantDTO;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalApplicant;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalAttachment;
import atraintegratedsystems.typeofapproval.repository.TypeOfApprovalApplicantRepository;
import atraintegratedsystems.typeofapproval.repository.TypeOfApprovalAttachmentRepository;
import atraintegratedsystems.utils.PersianCalendarUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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
    public List<TypeOfApprovalApplicant> findAllUnPaidAddminFee(){
        return typeOfApprovalApplicantRepository.findAllUnPaidAddminFee();
    }
    @Transactional
    public List<TypeOfApprovalApplicant> findAllUnPaidCertificateFee(){
        return typeOfApprovalApplicantRepository.findAllUnPaidCertificateFee();
    }

    @Transactional
    public List<TypeOfApprovalApplicant> getAllReferred(){
        return typeOfApprovalApplicantRepository.findAllReferred();
    }

    @Transactional
    public Optional<TypeOfApprovalApplicant> getById(Long id){
        return typeOfApprovalApplicantRepository.findById(id);
    }


    @Transactional
    public TypeOfApprovalApplicant findById(Long id) {
        return typeOfApprovalApplicantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LicenseApproval not found with id: " + id));
    }


    @Transactional
    public void save(TypeOfApprovalApplicant typeOfApprovalApplicant) {
        typeOfApprovalApplicantRepository.save(typeOfApprovalApplicant);
    }




//    public void updateReferFinance(Long id, String referDateJalali, String referStatus) {
//        TypeOfApprovalApplicant existing = typeOfApprovalApplicantRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Applicant not found with ID: " + id));
//
//        String[] parts = referDateJalali.split("-");
//        int jYear = Integer.parseInt(parts[0]);
//        int jMonth = Integer.parseInt(parts[1]);
//        int jDay = Integer.parseInt(parts[2]);
//
//        PersianCalendarUtils converter = new PersianCalendarUtils();
//        LocalDate referDate = converter.jalaliToGregorian(jYear, jMonth, jDay);
//
//        existing.setReferDate(referDate);
//        existing.setReferStatus(referStatus);
//
//        typeOfApprovalApplicantRepository.save(existing);
//    }




//    public void updateApplicationFee(TypeOfApprovalApplicantDTO dto) {
//        TypeOfApprovalApplicant appFee = typeOfApprovalApplicantRepository.findById(dto.getId())
//                .orElseThrow(() -> new IllegalArgumentException("Applicant not found with ID: " + dto.getId()));
//
//        String applicationFeeEnteredBy = SecurityContextHolder.getContext().getAuthentication().getName();
//
//        appFee.setApplicationFeeStatus(dto.getApplicationFeeStatus());
//        appFee.setApplicationFeeBankVoucherNo(dto.getApplicationFeeBankVoucherNo());
//
//        // Parse and convert Jalali voucher date
//        String[] parts = dto.getApplicationFeeVoucherDateJalali().split("-");
//        int jYear = Integer.parseInt(parts[0]);
//        int jMonth = Integer.parseInt(parts[1]);
//        int jDay = Integer.parseInt(parts[2]);
//
//        PersianCalendarUtils converter = new PersianCalendarUtils();
//        LocalDate voucherDate = converter.jalaliToGregorian(jYear, jMonth, jDay);
//        appFee.setApplicationFeeVoucherDate(voucherDate);
//
//        // Parse and convert Jalali submission date
//        String[] subParts = dto.getApplicationFeeBankVoucherSubmissionDateJalali().split("-");
//        int subYear = Integer.parseInt(subParts[0]);
//        int subMonth = Integer.parseInt(subParts[1]);
//        int subDay = Integer.parseInt(subParts[2]);
//
//        LocalDate submissionDate = converter.jalaliToGregorian(subYear, subMonth, subDay);
//        appFee.setApplicationFeeBankVoucherSubmissionDate(submissionDate);
//
//        appFee.setApplicationFeeEntryDate(LocalDate.now());
//        appFee.setApplicationFeeEnteredBy(applicationFeeEnteredBy);
//
//        typeOfApprovalApplicantRepository.save(appFee);
//    }





















//    public void updateAdminFee(Long id, String adminFeeStatus, String adminFeeBankVoucherNo
//            , String adminFeeVoucherDateJalali , String adminFeeSubmissionDateJalali){
//        TypeOfApprovalApplicant adminFee= typeOfApprovalApplicantRepository.findById(id)
//                .orElseThrow(()->new IllegalArgumentException("Applicant not found with ID: " + id));
//        String adminFeeEnteredBy = SecurityContextHolder.getContext().getAuthentication().getName();
//        adminFee.setAdminFeeStatus(adminFeeStatus);
//        adminFee.setAdminFeeBankVoucherNo(adminFeeBankVoucherNo);
//
//
//
//        String[] parts = adminFeeVoucherDateJalali.split("-");
//        int jYear = Integer.parseInt(parts[0]);
//        int jMonth = Integer.parseInt(parts[1]);
//        int jDay = Integer.parseInt(parts[2]);
//
//        PersianCalendarUtils converter = new PersianCalendarUtils();
//        LocalDate adminVoucherDate = converter.jalaliToGregorian(jYear, jMonth, jDay);
//
//        adminFee.setAdminFeeVoucherDate(adminVoucherDate);
//
//        adminFee.setAdminFeeEntryDate(LocalDate.now());
//        adminFee.setAdminFeeEnteredBy(adminFeeEnteredBy);
//
//
//        String[] subParts = adminFeeSubmissionDateJalali.split("-");
//        int jSubYear = Integer.parseInt(subParts[0]);
//        int jSubMonth = Integer.parseInt(subParts[1]);
//        int jSubDay = Integer.parseInt(subParts[2]);
//
//        LocalDate adminFeeSubmissionDate = converter.jalaliToGregorian(jSubYear, jSubMonth, jSubDay);
//        adminFee.setAdminFeeBankVoucherSubmissionDate(adminFeeSubmissionDate);
//
//        typeOfApprovalApplicantRepository.save(adminFee);
//
//    }

//    public void updateCertificateFee(Long id, String certificateFeeStatus, String certificateFeeBankVoucherNo
//            , String certificateFeeVoucherDateJalali , String certificateFeeSubmissionDateJalali){
//        TypeOfApprovalApplicant certificateFee= typeOfApprovalApplicantRepository.findById(id)
//                .orElseThrow(()->new IllegalArgumentException("Applicant not found with ID: " + id));
//        String certificateFeeEnteredBy = SecurityContextHolder.getContext().getAuthentication().getName();
//        certificateFee.setCertificateFeeStatus(certificateFeeStatus);
//        certificateFee.setCertificateFeeBankVoucherNo(certificateFeeBankVoucherNo);
//
//        String[] parts =certificateFeeVoucherDateJalali.split("-");
//        int jYear = Integer.parseInt(parts[0]);
//        int jMonth = Integer.parseInt(parts[1]);
//        int jDay = Integer.parseInt(parts[2]);
//
//        PersianCalendarUtils converter = new PersianCalendarUtils();
//        LocalDate certificateFeeVoucherDate = converter.jalaliToGregorian(jYear, jMonth, jDay);
//        certificateFee.setCertificateFeeVoucherDate(certificateFeeVoucherDate);
//
//        certificateFee.setCertificateFeeEntryDate(LocalDate.now());
//        certificateFee.setCertificateFeeEnteredBy(certificateFeeEnteredBy);
//
//        String[] subParts = certificateFeeSubmissionDateJalali.split("-");
//        int jSubYear = Integer.parseInt(subParts[0]);
//        int jSubMonth = Integer.parseInt(subParts[1]);
//        int jSubDay = Integer.parseInt(subParts[2]);
//
//        LocalDate certificateFeeSubmissionDate = converter.jalaliToGregorian(jSubYear, jSubMonth, jSubDay);
//
//        certificateFee.setCertificateFeeBankVoucherSubmissionDate(certificateFeeSubmissionDate);
//
//        typeOfApprovalApplicantRepository.save(certificateFee);
//
//    }






}
