package atraintegratedsystems.codes.service;

import atraintegratedsystems.codes.model.Code;
import atraintegratedsystems.codes.model.CodeDetail;
import atraintegratedsystems.codes.repository.CodeDetailRepository;
import atraintegratedsystems.codes.repository.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CodeDetailService {

    @Autowired
    private CodeDetailRepository codeDetailRepository;

    @Autowired
    private CodeRepository codeRepository;

    // -------------------------------------------------------------------
    // SAVE OR UPDATE
    // -------------------------------------------------------------------
    public CodeDetail save(CodeDetail codeDetail) {
        return codeDetailRepository.save(codeDetail);
    }

    // For backward compatibility (your existing code)
    public void AddShort(CodeDetail code) {
        codeDetailRepository.save(code);
    }

    // -------------------------------------------------------------------
    // FIND ALL
    // -------------------------------------------------------------------
    public List<CodeDetail> getAllDetailCodes() {
        return codeDetailRepository.findAll();
    }

    // -------------------------------------------------------------------
    // DELETE
    // -------------------------------------------------------------------
    public void deleteCodeDetail(Long id) {
        if (codeDetailRepository.existsById(id)) {
            codeDetailRepository.deleteById(id);
        }
    }

    // -------------------------------------------------------------------
    // FIND CODE (short code table)
    // -------------------------------------------------------------------
    public Optional<Code> getShortCode(int shortCode) {
        return codeRepository.findById(shortCode);
    }

    // -------------------------------------------------------------------
    // FIND CODE DETAIL BY ID
    // -------------------------------------------------------------------
    public Optional<CodeDetail> getCodeDetailId(Long id) {
        return codeDetailRepository.findById(id);
    }

    // -------------------------------------------------------------------
    // FIND BY SHORTCODE
    // -------------------------------------------------------------------
    public Optional<CodeDetail> getShortByCode(int shortCode) {
        return codeDetailRepository.findByShortCode(shortCode);
    }

    // -------------------------------------------------------------------
    // CUSTOM QUERIES
    // -------------------------------------------------------------------
    public List<Object[]> getCodeDetailWithCompanyNameAndCode() {
        return codeDetailRepository.getCodeDetailWithCompanyNameAndCode();
    }

    public List<CodeDetail> findUnpaid() {
        return codeDetailRepository.findUnpaid();
    }
}
