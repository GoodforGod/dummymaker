package io.dummymaker.util;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Logger;

/**
 * Contains basic date util methods and functions
 *
 * @author GoodforGod
 * @since 25.02.2018
 */
public class BasicDateUtils {

    private static final Logger logger = Logger.getLogger(BasicDateUtils.class.getName());

    private static final String ZONE_ID = "Europe/Moscow";

    private static Calendar genCalendar() {
        return Calendar.getInstance(TimeZone.getTimeZone(ZONE_ID));
    }

    public static Timestamp convertToTimestamp(final Date date) {
        if (date == null)
            return null;

        final Calendar cal = genCalendar();
        cal.setTime(date);
        cal.set(Calendar.MILLISECOND, 0);
        return new Timestamp(date.getTime());
    }

    public static Timestamp convertToTimestamp(final LocalDate localDate) {
        return (localDate != null)
                ? Timestamp.valueOf(localDate.atStartOfDay())
                : null;
    }

    public static Timestamp convertToTimestamp(final LocalTime localTime) {
        return (localTime != null)
                ? Timestamp.valueOf(LocalDateTime.of(LocalDate.of(1970, 1, 1), localTime))
                : null;
    }

    public static Timestamp convertToTimestamp(final LocalDateTime localDateTime) {
        return (localDateTime != null)
                ? Timestamp.valueOf(localDateTime)
                : null;
    }

    public static Date parseSimpleDateLong(final String date) {
        try {
            return new Date(Long.valueOf(date));
        } catch (NumberFormatException e) {
            logger.warning(e.getMessage());
            return null;
        }
    }

    public static LocalDateTime parseDateTime(final String dateTime) {
        try {
            return LocalDateTime.parse(dateTime);
        } catch (Exception e) {
            logger.warning("Can not parse date time: " + e.getMessage());
            return null;
        }
    }

    public static LocalTime parseTime(final String time) {
        try {
            return LocalTime.parse(time);
        } catch (Exception e) {
            logger.warning("Can not parse time: " + e.getMessage());
            return null;
        }
    }

    public static LocalDate parseDate(final String date) {
        try {
            return LocalDate.parse(date);
        } catch (Exception e) {
            logger.warning("Can not parse date: " + e.getMessage());
            return null;
        }
    }
}
