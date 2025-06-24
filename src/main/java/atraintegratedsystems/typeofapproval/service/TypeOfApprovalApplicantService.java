package atraintegratedsystems.typeofapproval.service;

import atraintegratedsystems.typeofapproval.model.TypeOfApprovalApplicant;
import atraintegratedsystems.typeofapproval.model.TypeOfApprovalAttachment;
import atraintegratedsystems.typeofapproval.repository.TypeOfApprovalApplicantRepository;
import atraintegratedsystems.typeofapproval.repository.TypeOfApprovalAttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
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





}
