package com.epam.hr.domain.util;

import com.epam.hr.exception.UtilsException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static final String DATE_FORMAT = "yyyy-mm-dd";

    public static Date tryParse(String dateValue) throws UtilsException {
        try {
            DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            return dateFormat.parse(dateValue);
        } catch (ParseException e) {
            throw new UtilsException(e);
        }
    }

}
