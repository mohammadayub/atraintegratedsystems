package atraintegratedsystems.codes.repository;

import atraintegratedsystems.codes.model.ShortCodeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ShortCodeDetailRepository extends JpaRepository<ShortCodeDetail,Long> {
    Optional<ShortCodeDetail> findByShortCode(int shortCode);

    @Query(value = "SELECT cd.code_detail_id, CONCAT(cd.source_used, '-', cd.short_code) AS company_name_with_code " +
            "FROM code_extension_detail ced RIGHT OUTER JOIN short_code_detail cd ON ced.code_detail_id = cd.code_detail_id", nativeQuery = true)
    List<Object[]> getCodeDetailWithCompanyNameAndCode();

    @Query(value = "SELECT * FROM short_code_detail WHERE payment_status IS NULL", nativeQuery = true)
    List<ShortCodeDetail> findUnpaid();



//    @Modifying @Transactional @Query("UPDATE CodeDetail c SET c.releaseShortCode = c.shortCode, c.shortCode = NULL WHERE c.id = :id AND c.shortCode IS NOT NULL")
//    int releaseCode(@Param("id") Long id);
@Modifying
@Transactional
@Query(
        "UPDATE ShortCodeDetail c " +
                "SET c.releaseShortCode = c.shortCode, " +
                "    c.shortCode = NULL " +
                "WHERE c.id = :id AND c.shortCode IS NOT NULL"
)
int releaseCode(@Param("id") Long id);




    // for Application Fee

    @Query(value = "SELECT cd.code_detail_id,cd.short_code, cd.code_status, cd.source_used, cd.location, cd.category_type, cd.category, cd.chanel, cd.email_of_responsible_person FROM short_code_detail cd WHERE cd.application_fees_status IS NULL", nativeQuery = true)
    List<Object[]> findunPaidApplicationFee();

    // Tariff Related

//    @Query(value = "SELECT * FROM code_detail WHERE code_detail_id = :id", nativeQuery = true)
//    Object findApplicationFeeById(@Param("id") Long id);

//    @Query(value = "SELECT code_detail_id, short_code, code_status, source_used, location, category_type, category, chanel, email_of_responsible_person FROM code_detail WHERE code_detail_id = :id", nativeQuery = true)
//    Object[] findApplicationFeeById(@Param("id") Long id);

//    @Query(value =
//            "SELECT cd.code_detail_id, " +
//                    "       cd.short_code, " +
//                    "       cd.code_status, " +
//                    "       cd.source_used, " +
//                    "       cd.location, " +
//                    "       cd.category_type, " +
//                    "       cd.category, " +
//                    "       cd.chanel, " +
//                    "       cd.email_of_responsible_person " +
//                    "FROM code_detail cd " +
//                    "WHERE cd.code_detail_id = :id",
//            nativeQuery = true)
//    Object[] findApplicationFeeById(@Param("id") Long id);

    @Query(value =
            "SELECT * FROM short_code_detail " +
                    "WHERE code_detail_id = :id " +
                    "AND application_fees_status IS NULL",
            nativeQuery = true)
    Optional<ShortCodeDetail> findUnpaidApplicationFeeById(@Param("id") Long id);




    // update

//    @Modifying
//    @Transactional
//    @Query(
//            "UPDATE CodeDetail c " +
//                    "SET c.applicationFeesStatus = 'PAID', " +
//                    "    c.applicationFeebankVoucherNo = :voucherNo, " +
//                    "    c.applicationFeebankVoucherSubmissionDate = :submissionDate, " +
//                    "    c.paymentStatus = 'PAID' " +
//                    "WHERE c.id = :id"
//    )
//    int confirmApplicationFeePayment(
//            @Param("id") Long id,
//            @Param("voucherNo") String voucherNo,
//            @Param("submissionDate") String submissionDate
//    );

    @Modifying
    @Transactional
    @Query(
            "UPDATE ShortCodeDetail c " +
                    "SET c.applicationFeesStatus = 'PAID', " +
                    "    c.applicationFeebankVoucherNo = :voucherNo, " +
                    "    c.applicationFeeEnterVoucherDate = :enterDate, " +
                    "    c.applicationFeebankVoucherSubmissionDate = :submissionDate, " +
                    "    c.paymentStatus = 'PAID' " +
                    "WHERE c.id = :id"
    )
    int confirmApplicationFeePayment(
            @Param("id") Long id,
            @Param("voucherNo") String voucherNo,
            @Param("enterDate") String enterDate,
            @Param("submissionDate") String submissionDate
    );


//    Royalty Fee Related

    @Query(value = "SELECT cd.code_detail_id,cd.short_code, cd.code_status, cd.source_used, cd.location, cd.category_type, cd.category, cd.chanel, cd.email_of_responsible_person FROM short_code_detail cd WHERE cd.royalty_fees_status IS null", nativeQuery = true)
    List<Object[]> findunPaidRoyaltyFee();

//    Bellow is related to Tariff
@Query(value =
        "SELECT * FROM short_code_detail " +
                "WHERE code_detail_id = :id " +
                "AND royalty_fees_status IS NULL",
        nativeQuery = true)
Optional<ShortCodeDetail> findUnpaidRoyaltyFeeById(@Param("id") Long id);


    // Bellow is Related To Confirmation

    @Modifying
    @Transactional
    @Query(
            "UPDATE ShortCodeDetail c " +
                    "SET c.royaltyFeesStatus = 'PAID', " +
                    "    c.royaltyFeebankVoucherNo = :voucherNo, " +
                    "    c.royaltyFeeEnterVoucherDate = :enterDate, " +
                    "    c.royaltyFeeBankVoucherSubmissionDate = :submissionDate " +
                    "WHERE c.id = :id"
    )
    int confirmRoyaltyFeePayment(
            @Param("id") Long id,
            @Param("voucherNo") String voucherNo,
            @Param("enterDate") String enterDate,
            @Param("submissionDate") String submissionDate
    );

    // Bellow is for royalty Fee Extension

//    @Query(value = "SELECT * FROM code_detail cd WHERE cd.royalty_fees_status='PAID'", nativeQuery = true)
//    List<Object[]> findUnPaidRoyaltyFeeForExtension();
@Query(
        value =
                "SELECT " +
                        " cd.code_detail_id, " +              // row[0]
                        " cd.short_code, " +                  // row[1]
                        " cd.royalty_fees_status, " +         // row[2]
                        " cd.source_used, " +                 // row[3]
                        " cd.location, " +                    // row[4]
                        " cd.category_type, " +               // row[5]
                        " cd.category, " +                    // row[6]
                        " cd.chanel, " +                      // row[7]
                        " cd.email_of_responsible_person " +  // row[8]
                        "FROM short_code_detail cd " +
                        "WHERE cd.royalty_fees_status = 'PAID'",
        nativeQuery = true
)
List<Object[]> findUnPaidRoyaltyFeeForExtension();



    // Bellow is Application Fee Extension
    @Query(value = "SELECT cd.code_detail_id,cd.short_code, cd.code_status, cd.source_used, cd.location, cd.category_type, cd.category, cd.chanel, cd.email_of_responsible_person FROM short_code_detail cd WHERE cd.application_fees_status='PAID'", nativeQuery = true)
    List<Object[]> findUnPaidApplicationFeeForExtension();


    // Bellow is For Short Code Rejection

    @Modifying
    @Transactional
    @Query(
            "UPDATE ShortCodeDetail c " +
                    "SET c.shortCodeRejectionStatus = :status, " +
                    "    c.shortCodeRejectionDate = :date, " +
                    "    c.codeStatus = 'REJECTED' " +
                    "WHERE c.id = :id"
    )
    int rejectShortCode(
            @Param("id") Long id,
            @Param("status") String status,
            @Param("date") LocalDate date
    );





}