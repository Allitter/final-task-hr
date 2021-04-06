package com.epam.hr.domain.util;

import com.epam.hr.exception.ServiceRuntimeException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Pattern;

/**
 * Provides convenient methods to work with date
 */
public final class DateUtils {
    public static final String FORMAT_OF_DATE = "yyyy-MM-dd";
    private static final String TIME_ZONE_NAME = "Europe/Minsk";
    private static final TimeZone TIME_ZONE = TimeZone.getTimeZone(TIME_ZONE_NAME);
    private static final String THIRTY_ONE_DAY_DATE_REGEX = "(\\d{4})-((0[13578])|(1[02]))-31";
    private static final String THIRTY_DAY_DATE_REGEX = "(\\d{4})-((0[13-9])|(1[012]))-(30|29)";
    private static final String COMMON_DATE_REGEX = "(\\d{4})-((0[1-9])|(1[02]))-((0[1-9])|(1\\d)|(2[0-8]))";
    private static final String PROBABLY_LEAP_YEAR_REGEX = "\\d{4}-02-29";
    private static final Pattern THIRTY_ONE_DAY_DATE_PATTERN = Pattern.compile(THIRTY_ONE_DAY_DATE_REGEX);
    private static final Pattern THIRTY_DAY_DATE_PATTERN = Pattern.compile(THIRTY_DAY_DATE_REGEX);
    private static final Pattern COMMON_DATE_PATTERN = Pattern.compile(COMMON_DATE_REGEX);
    private static final Pattern PROBABLY_LEAP_YEAR_PATTERN = Pattern.compile(PROBABLY_LEAP_YEAR_REGEX);
    public static final int BEGIN_YEAR_INDEX = 0;
    public static final int END_YEAR_INDEX = 4;

    private DateUtils() {
    }

    /**
     * trying to parse date with "yyyy-MM-dd" format
     *
     * @param dateValue string value of date
     * @return date if format is correct or throws exception
     * @throws ServiceRuntimeException if date format incorrect
     */
    public static Date tryParse(String dateValue) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(FORMAT_OF_DATE);
            return dateFormat.parse(dateValue);
        } catch (ParseException e) {
            throw new ServiceRuntimeException(e);
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

    /**
     * is date valid
     *
     * @param date date value
     * @return true if date has valid format
     */
    public static boolean isValidDate(String date) {
        if (date == null || date.equals("")) {
            return false;
        }

        if (COMMON_DATE_PATTERN.matcher(date).matches()
                || THIRTY_DAY_DATE_PATTERN.matcher(date).matches()
                || THIRTY_ONE_DAY_DATE_PATTERN.matcher(date).matches()) {
            return true;
        }

        /*
         * step 1: If the year is evenly divisible by 4, go to step 2. Otherwise, go to step 5.
         * step 2: If the year is evenly divisible by 100, go to step 3. Otherwise, go to step 4.
         * step 3: If the year is evenly divisible by 400, go to step 4. Otherwise, go to step 5.
         * step 4: The year is a leap year (it has 366 days).
         * step 5: The year is not a leap year (it has 365 days).
         * */
        if (PROBABLY_LEAP_YEAR_PATTERN.matcher(date).matches()) {
            String yearValue = date.substring(BEGIN_YEAR_INDEX, END_YEAR_INDEX);
            int year = Integer.parseInt(yearValue);
            return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
        }

        return false;
    }
}
