package com.epam.hr.domain.util;

import com.epam.hr.exception.UtilsException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Provides convenient methods to work with date
 */
public final class DateUtils {
    private static final String FORMAT_OF_DATE = "yyyy-mm-dd";
    private static final String TIME_ZONE_NAME = "Europe/Minsk";
    private static final TimeZone TIME_ZONE = TimeZone.getTimeZone(TIME_ZONE_NAME);

    private DateUtils() {
    }

    /**
     * trying to parse date with "yyyy-mm-dd" format
     *
     * @param dateValue string value of date
     * @return date if format is correct or throws exception
     * @throws UtilsException if date format incorrect
     */
    public static Date tryParse(String dateValue) throws UtilsException {
        try {
            DateFormat dateFormat = new SimpleDateFormat(FORMAT_OF_DATE);
            return dateFormat.parse(dateValue);
        } catch (ParseException e) {
            throw new UtilsException(e);
        }
    }

    /**
     * @return current date
     */
    public static Date currentDate() {
        Calendar calendar = Calendar.getInstance(TIME_ZONE);
        return calendar.getTime();
    }

    /**
     * @return current date with time
     */
    public static Date currentDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return Date.from(localDateTime.atZone(TIME_ZONE.toZoneId()).toInstant());
    }

    /**
     * Calculates offset date from current
     *
     * @param offsetInMinutes offset in minutes
     * @return offset date
     */
    public static Date calculateOffsetDateInMinutes(int offsetInMinutes) {
        return calculateOffsetDateInMinutes(currentDateTime(), offsetInMinutes);
    }

    /**
     * Calculates offset date from current
     *
     * @param offsetInDays offset in days
     * @return offset date
     */
    public static Date calculateOffsetDateInDays(int offsetInDays) {
        return calculateOffsetDateInDays(currentDateTime(), offsetInDays);
    }

    /**
     * Calculates offset date
     *
     * @param offsetInMinutes offset in minutes
     * @param from            date from which offset is calculated
     * @return offset date
     */
    public static Date calculateOffsetDateInMinutes(Date from, int offsetInMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(from);
        calendar.add(Calendar.MINUTE, offsetInMinutes);
        return calendar.getTime();
    }

    /**
     * Calculates offset date
     *
     * @param offsetInDays offset in days
     * @param from         date from which offset is calculated
     * @return offset date
     */
    public static Date calculateOffsetDateInDays(Date from, int offsetInDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(from);
        calendar.add(Calendar.DATE, offsetInDays);
        return calendar.getTime();
    }
}
