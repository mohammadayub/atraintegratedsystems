package atraintegratedsystems.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

public class SerialGenerator {

    // Global counter for all serial numbers
    private static final AtomicLong globalCounter = new AtomicLong(0);

    /**
     * Generates a serial number in the format:
     * SOURCE-YYYYMMDD-SHORTCODE-N
     *
     * N = last number + 1, always increments
     */
    public String generateSerialNumber(String sourceUsed,
                                       LocalDate expiryDate,
                                       Integer shortCode) {

        if (sourceUsed == null || expiryDate == null || shortCode == null) {
            throw new IllegalArgumentException("Required fields missing for serial number generation");
        }

        // Format date as yyyyMMdd
        String formattedDate = expiryDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // Prefix without number
        String prefix = sourceUsed + "-" + formattedDate + "-" + shortCode;

        // Increment global counter
        long nextNumber = globalCounter.incrementAndGet();

        return prefix + "-" + nextNumber;
    }

    /**
     * Optional: to know the last generated number
     */
    public long getLastNumber() {
        return globalCounter.get();
    }
}