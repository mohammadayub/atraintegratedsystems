package atraintegratedsystems.codes.service;
import atraintegratedsystems.codes.model.IspcDetail;
import atraintegratedsystems.codes.repository.IspcDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IspcDetailService {


    @Autowired
    private IspcDetailRepository ispcDetailRepository;

    public void AddIspc(IspcDetail code){
        ispcDetailRepository.save(code);
    }
    public List<IspcDetail> getAllDetailCodes(){
        return ispcDetailRepository.findAll();
    }

    public Optional<IspcDetail> getIspcDetailId(Long id){
        return ispcDetailRepository.findById(id);
    }

    public void deleteIspcDetail(Long id){
        ispcDetailRepository.deleteById(id);
    }

    public List<Object[]> getCompanyNamesWithISPCCode(){
        return
                 ispcDetailRepository.getCompanyNamesWithCodes();
    }

    public List<IspcDetail> findIspcUnPaid(){
        return ispcDetailRepository.findUnpaid();
    }

}
