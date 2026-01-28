package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.model.ShortCode;
import atraintegratedsystems.codes.model.ShortCodeDetail;
import atraintegratedsystems.codes.repository.ShortCodeDetailRepository;
import atraintegratedsystems.codes.repository.ShortCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ShortCodeDetailService {

    @Autowired
    private ShortCodeDetailRepository shortCodeDetailRepository;

    @Autowired
    private ShortCodeRepository shortCodeRepository;

    // -------------------------------------------------------------------
    // SAVE OR UPDATE
    // -------------------------------------------------------------------
    public ShortCodeDetail save(ShortCodeDetail codeDetail) {
        return shortCodeDetailRepository.save(codeDetail);
    }



    // -----------------------------
    // RELEASE SHORT CODE
    // -----------------------------
    @Transactional
    public boolean releaseShortCode(Long id) {
        int updated = shortCodeDetailRepository.releaseCode(id);
        return updated > 0;
    }




    // For backward compatibility (your existing code)
    public void AddShort(ShortCodeDetail code) {
        shortCodeDetailRepository.save(code);
    }

    // -------------------------------------------------------------------
    // FIND ALL
    // -------------------------------------------------------------------
    public List<ShortCodeDetail> getAllDetailCodes() {
        return shortCodeDetailRepository.findAll();
    }

    // -------------------------------------------------------------------
    // DELETE
    // -------------------------------------------------------------------
    public void deleteCodeDetail(Long id) {
        if (shortCodeDetailRepository.existsById(id)) {
            shortCodeDetailRepository.deleteById(id);
        }
    }

    // -------------------------------------------------------------------
    // FIND CODE (short code table)
    // -------------------------------------------------------------------
    public Optional<ShortCode> getShortCode(int shortCode) {
        return shortCodeRepository.findById(shortCode);
    }

    // -------------------------------------------------------------------
    // FIND CODE DETAIL BY ID
    // -------------------------------------------------------------------
    public Optional<ShortCodeDetail> getCodeDetailId(Long id) {
        return shortCodeDetailRepository.findById(id);
    }

    // -------------------------------------------------------------------
    // FIND BY SHORTCODE
    // -------------------------------------------------------------------
    public Optional<ShortCodeDetail> getShortByCode(int shortCode) {
        return shortCodeDetailRepository.findByShortCode(shortCode);
    }

    // -------------------------------------------------------------------
    // CUSTOM QUERIES
    // -------------------------------------------------------------------
    public List<Object[]> getCodeDetailWithCompanyNameAndCode() {
        return shortCodeDetailRepository.getCodeDetailWithCompanyNameAndCode();
    }

    public List<ShortCodeDetail> findUnpaid() {
        return shortCodeDetailRepository.findUnpaid();
    }




}
