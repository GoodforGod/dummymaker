package io.dummymaker.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import static io.dummymaker.util.BasicStringUtils.isNotBlank;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 25.02.2018
 */
public class BasicDateUtils {

    private static final Logger logger = Logger.getLogger(BasicDateUtils.class.getName());

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("H:m d.M.yyyy");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("H:m");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d.M.yyyy");
    public static final DateTimeFormatter DATE_SHORT_FORMATTER = DateTimeFormatter.ofPattern("d.M");

    private static Calendar genCalendar() {
        return Calendar.getInstance();
    }

    public static Date parseSimpleDate(String date) {

        return null;
    }

    public static LocalDateTime parseDateTime(String dateTime) {
        final boolean timeStatus = isNotBlank(dateTime);

        return (timeStatus)
                ? parseDateTime(dateTime, DATE_TIME_FORMATTER)
                : null;
    }

    public static LocalDateTime parseDateTime(String date, String time) {
        final boolean timeStatus = isNotBlank(time);
        final boolean dateStatus = isNotBlank(date);

        if (timeStatus & dateStatus)
            return parseDateTime(time + " " + date, DATE_TIME_FORMATTER);

        if (timeStatus) {
            final LocalTime localTime = parseTime(time);
            return (localTime == null)
                    ? null
                    : LocalDateTime.of(0, 0, 0, localTime.getHour(), localTime.getMinute());
        }

        if (dateStatus) {
            final LocalDate localDate = parseDate(time);
            return (localDate == null)
                    ? null
                    : LocalDateTime.of(localDate.getYear(), localDate.getDayOfMonth(), localDate.getDayOfMonth(), 0, 0);
        }

        return null;
    }

    public static LocalDate parseDate(String date) {
        return (isNotBlank(date))
                ? parseDate(date, DATE_FORMATTER)
                : null;
    }

    public static LocalTime parseTime(String time) {
        return (isNotBlank(time))
                ? parseTime(time, TIME_FORMATTER)
                : null;
    }

    private static LocalDateTime parseDateTime(String dateTime, DateTimeFormatter format) {
        try {
            return LocalDateTime.parse(dateTime, format);
        } catch (DateTimeParseException e) {
            logger.warning("[BASIC DATE UTILS] Can not parse date time: " + e.getMessage());
            return null;
        }
    }

    private static LocalTime parseTime(String time, DateTimeFormatter format) {
        try {
            return LocalTime.parse(time, format);
        } catch (DateTimeParseException e) {
            logger.warning("[BASIC DATE UTILS] Can not parse time: " + e.getMessage());
            return null;
        }
    }

    private static LocalDate parseDate(String date, DateTimeFormatter format) {
        try {
            return LocalDate.parse(date, format);
        } catch (DateTimeParseException e) {
            logger.warning("[BASIC DATE UTILS] Can not parse date: " + e.getMessage());
            return null;
        }
    }
}
