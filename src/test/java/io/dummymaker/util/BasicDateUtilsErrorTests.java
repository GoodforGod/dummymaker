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
        final Timestamp t = BasicDateUtils.convertToTimestamp((Date) null);
        assertNull(t);
    }

    @Test
    public void convertNullToTimestampDate() {
        final Timestamp t = BasicDateUtils.convertToTimestamp((LocalDate) null);
        assertNull(t);
    }

    @Test
    public void convertNullToTimestampTime() {
        final Timestamp t = BasicDateUtils.convertToTimestamp((LocalTime) null);
        assertNull(t);
    }

    @Test
    public void convertNullToTimestampDateTime() {
        final Timestamp t = BasicDateUtils.convertToTimestamp((LocalDateTime) null);
        assertNull(t);
    }

    @Test
    public void parseDateNull() {
        final LocalDateTime result = BasicDateUtils.parseDateTime(null);
        assertNull(result);

        final LocalDateTime result2 = BasicDateUtils.parseDateTime("   ");
        assertNull(result2);
    }

    @Test
    public void parseDateOldTypeNull() {
        final LocalTime result = BasicDateUtils.parseTime(null);
        assertNull(result);

        final LocalTime result2 = BasicDateUtils.parseTime("    ");
        assertNull(result2);
    }

    @Test
    public void parseTimeNull() {
        final LocalDate result = BasicDateUtils.parseDate(null);
        assertNull(result);

        final LocalDate result2 = BasicDateUtils.parseDate("   ");
        assertNull(result2);
    }

    @Test
    public void parseDateTimeNull() {
        final Date result = BasicDateUtils.parseSimpleDateLong(null);
        assertNull(result);

        final Date result2 = BasicDateUtils.parseSimpleDateLong("  ");
        assertNull(result2);
    }

    @Test
    public void parseDateInvalidFormat() {
        final String asString = "10:101/";
        final LocalDateTime result = BasicDateUtils.parseDateTime(asString);
        assertNull(result);
    }

    @Test
    public void parseDateOldTypeInvalidFormat() {
        final String asString = "10:101/";
        final LocalTime result = BasicDateUtils.parseTime(asString);
        assertNull(result);
    }

    @Test
    public void parseTimeInvalidFormat() {
        final String asString = "10:101/";
        final LocalDate result = BasicDateUtils.parseDate(asString);
        assertNull(result);
    }

    @Test
    public void parseDateTimeInvalidFormat() {
        final String asString = "10:101/";
        final Date result = BasicDateUtils.parseSimpleDateLong(asString);
        assertNull(result);
    }
}
