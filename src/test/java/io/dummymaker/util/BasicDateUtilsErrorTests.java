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
 * @since 04.03.2018
 */
public class BasicDateUtilsErrorTests extends Assert {

    @Test
    public void convertNullToTimestampDateOldType() {
        Date date = null;
        final Timestamp t = BasicDateUtils.convertToTimestamp(date);
        assertNull(t);
    }

    @Test
    public void convertNullToTimestampDate() {
        LocalDate localDate = null;
        final Timestamp t = BasicDateUtils.convertToTimestamp(localDate);
        assertNull(t);
    }

    @Test
    public void convertNullToTimestampTime() {
        LocalTime localTime = null;
        final Timestamp t = BasicDateUtils.convertToTimestamp(localTime);
        assertNull(t);
    }

    @Test
    public void convertNullToTimestampDateTime() {
        LocalDateTime localDateTime = null;
        final Timestamp t = BasicDateUtils.convertToTimestamp(localDateTime);
        assertNull(t);
    }

    @Test
    public void parseDateNull() {
        final LocalDateTime result = BasicDateUtils.parseDateTime(null);
        assertNull(result);

    }

    @Test
    public void parseDateEmpty() {
        final LocalDateTime result2 = BasicDateUtils.parseDateTime(" 5125  ");
        assertNull(result2);
    }

    @Test
    public void parseDateOldTypeNull() {
        final LocalTime result = BasicDateUtils.parseTime(null);
        assertNull(result);
    }

    @Test
    public void parseDateOldEmpty() {
        final LocalTime result2 = BasicDateUtils.parseTime("  124  ");
        assertNull(result2);
    }

    @Test
    public void parseTimeNull() {
        final LocalDate result = BasicDateUtils.parseDate(null);
        assertNull(result);
    }

    @Test
    public void parseTimeEmpty() {
        final LocalDate result2 = BasicDateUtils.parseDate("  61361  ");
        assertNull(result2);
    }

    @Test
    public void parseDateTimeNull() {
        final Date result = BasicDateUtils.parseSimpleDateLong(null);
        assertNull(result);
    }

    @Test
    public void parseDateTimeEmpty() {
        final Date result2 = BasicDateUtils.parseSimpleDateLong("  61361  ");
        assertNull(result2);
    }

    @Test
    public void parseDateInvalidFormat() {
        final String asString = "10:101515125/";
        final LocalDateTime result = BasicDateUtils.parseDateTime(asString);
        assertNull(result);
    }

    @Test
    public void parseDateOldTypeInvalidFormat() {
        final String asString = "10:10112515/";
        final LocalTime result = BasicDateUtils.parseTime(asString);
        assertNull(result);
    }

    @Test
    public void parseTimeInvalidFormat() {
        final String asString = "10:101125125/";
        final LocalDate result = BasicDateUtils.parseDate(asString);
        assertNull(result);
    }

    @Test
    public void parseDateTimeInvalidFormat() {
        final String asString = "10:101512512/";
        final Date result = BasicDateUtils.parseSimpleDateLong(asString);
        assertNull(result);
    }
}
