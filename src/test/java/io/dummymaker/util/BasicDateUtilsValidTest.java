package io.dummymaker.util;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 27.02.2018
 */
public class BasicDateUtilsValidTest extends Assert {

    @Test
    public void convertToTimestampDateOldType() {
        final Timestamp t = BasicDateUtils.convertToTimestamp(new Date(10000));
        assertNotNull(t);
    }

    @Test
    public void convertToTimestampDate() {
        final Timestamp t = BasicDateUtils.convertToTimestamp(LocalDate.of(1970, 1, 1));
        assertNotNull(t);
    }

    @Test
    public void convertToTimestampTime() {
        final Timestamp t = BasicDateUtils.convertToTimestamp(LocalTime.of(10, 10, 10));
        assertNotNull(t);
    }

    @Test
    public void convertToTimestampDateTime() {
        final Timestamp t = BasicDateUtils.convertToTimestamp(LocalDateTime.MIN);
        assertNotNull(t);
    }

    @Test
    public void parseDateValid() {
        final String date = "1990-10-10";
        final LocalDate localDate = BasicDateUtils.parseDate(date);
        assertNotNull(localDate);
    }

    @Test
    public void parseDateOldTypeValid() {
        final String date = "1000000";
        final Date dateOld = BasicDateUtils.parseSimpleDateLong(date);
        assertNotNull(dateOld);
    }

    @Test
    public void parseTimeValid() {
        final String date = "10:10:10";
        final LocalTime localTime = BasicDateUtils.parseTime(date);
        assertNotNull(localTime);
    }

    @Test
    public void parseDateTimeValid() {
        final String date = "1990-10-10T10:10:10.111";
        final LocalDateTime localDateTime = BasicDateUtils.parseDateTime(date);
        assertNotNull(localDateTime);
    }
}
