package com.example.employeemanagement.utility;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for Date and Time operations.
 * Exposes methods to format/parse dates/times and calculate working hours.
 */
public final class DateUtils {

    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String TIME_PATTERN = "HH:mm:ss";

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);

    private DateUtils() {
        // Prevent instantiation
    }

    /**
     * Calculate duration between check-in and check-out times in a friendly format.
     * @param checkIn The check-in time.
     * @param checkOut The check-out time.
     * @return Formatted string representing working hours (e.g. "8 hours 30 mins").
     */
    public static String calculateWorkingHours(LocalTime checkIn, LocalTime checkOut) {
        if (checkIn == null || checkOut == null) {
            return "0 hours 0 mins";
        }
        if (checkOut.isBefore(checkIn)) {
            return "0 hours 0 mins"; // Safeguard
        }

        Duration duration = Duration.between(checkIn, checkOut);
        long totalMinutes = duration.toMinutes();
        long hours = totalMinutes / 60;
        long minutes = totalMinutes % 60;

        return String.format("%d hours %d mins", hours, minutes);
    }
}
