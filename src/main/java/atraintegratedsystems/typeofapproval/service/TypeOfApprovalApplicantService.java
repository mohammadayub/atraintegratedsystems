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

    public List<TypeOfApprovalApplicant> getAllPaidCompanies() {
        return typeOfApprovalApplicantRepository.findAllPaidCompany();
    }






}
