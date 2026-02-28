package atraintegratedsystems.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class SerialGenerator {

    // Global counter for all serial numbers
    private static final AtomicInteger globalCounter = new AtomicInteger(0);

    /**
     * Generates a serial number in the format: SOURCE-YYYYMMDD-SHORTCODE-N
     * where N increments globally for each new insertion.
     */
    public String generateSerialNumber(String sourceUsed, LocalDate expiryDate, Integer shortCode) {

        if (sourceUsed == null || expiryDate == null || shortCode == null) {
            throw new IllegalArgumentException("Required fields missing for serial number generation");
        }

        String formattedDate = expiryDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // Prefix without number
        String prefix = sourceUsed + "-" + formattedDate + "-" + shortCode;

        // Increment global counter
        int nextNumber = globalCounter.incrementAndGet();

        return prefix + "-" + nextNumber;
    }
}