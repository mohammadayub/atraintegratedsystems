package atraintegratedsystems.licenses.service;


import java.time.LocalDate;

public interface LicenseApplicantBasic {

        Long getId();
        String getCompanyLicenseName();
        LocalDate getReqDate();
        String getValidity();


}
