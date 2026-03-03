package atraintegratedsystems.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

public class SerialGeneratorWithString {

    private static final AtomicLong globalCounter = new AtomicLong(0);

    /**
     * Format:
     * SOURCE-YYYYMMDD-SMSCODE-N
     */
    public String generateSerialNumber(String sourceUsed,
                                       LocalDate expiryDate,
                                       String smsIdentifierCode) {

        if (sourceUsed == null || sourceUsed.trim().isEmpty()
                || expiryDate == null
                || smsIdentifierCode == null || smsIdentifierCode.trim().isEmpty()) {

            throw new IllegalArgumentException("Required fields missing for serial number generation");
        }

        String formattedDate = expiryDate.format(DateTimeFormatter.BASIC_ISO_DATE);

        String prefix = sourceUsed.trim()
                + "-" + formattedDate
                + "-" + smsIdentifierCode.trim();

        long nextNumber = globalCounter.incrementAndGet();

        return prefix + "-" + nextNumber;
    }

    public long getLastNumber() {
        return globalCounter.get();
    }
}