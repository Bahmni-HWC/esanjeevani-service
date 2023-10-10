package org.bahmnihwc.esanjeevaniservice.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class DateTimeUtil {

    private final static ZoneId zoneId = ZoneId.of("Asia/Kolkata");

    private final static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String getCurrentDateTimeString() {
        return LocalDateTime.now(zoneId).toString();
    }

    public static String getCurrentDateString() {
        return LocalDateTime.now(zoneId).toLocalDate().toString();
    }

    public static LocalDate parseDate(String dateString) {
        return LocalDate.parse(dateString, dateFormatter);
    }

    public static boolean isValidDate(String date) {
        try {
            LocalDate.parse(date, dateFormatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

}
