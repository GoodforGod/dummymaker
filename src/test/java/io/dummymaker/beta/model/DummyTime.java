package io.dummymaker.beta.model;

import io.dummymaker.annotation.complex.GenTime;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 07.03.2018
 */
public class DummyTime {

    public enum Patterns {
        LOCAL_TIME(Pattern.compile("\\d{1,2}:\\d{1,2}(:\\d{1,2})?(\\.\\d{1,10})?")),
        LOCAL_DATE(Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}")),
        LOCAL_DATETIME(Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}[A-Z]\\d{1,2}:\\d{1,2}(:\\d{1,2}(\\.\\d+)?)?")),
        DATE(Pattern.compile("[A-Za-z]{3} [A-Za-z]{3} \\d{2} \\d{1,2}:\\d{1,2}:\\d{1,2} [A-Za-z]{3,5} \\d{4}")),
        TIMESTAMP(Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}(\\.\\d{1,10})?")),
        DATE_SQL(Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}")),
        TIME(Pattern.compile("\\d{1,2}:\\d{1,2}:\\d{1,2}"));

        private final Pattern pattern;

        Patterns(Pattern pattern) {
            this.pattern = pattern;
        }

        public Pattern getPattern() {
            return pattern;
        }
    }

    public enum Fields {
        LOCAL_TIME("time"),
        LOCAL_DATE("date"),
        LOCAL_DATETIME("dateTime"),
        TIMESTAMP("timestamp"),
        DATE("dateOld"),
        DATE_COVERAGE("dateOldCoverage"),
        LOCAL_DATETIME_STRING("dateTimeString"),
        LOCAL_DATETIME_OBJECT("dateTimeObject");

        private final String name;

        Fields(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    @GenTime
    private LocalTime time;

    @GenTime(from = 1000, to = 999)
    private LocalDate date;

    @GenTime(from = -100)
    private LocalDateTime dateTime;

    @GenTime(from = 1)
    private Timestamp timestamp;

    @GenTime(to = GenTime.MAX + 1000)
    private Date dateOld;

    @GenTime(from = -100, to = GenTime.MAX + 1000)
    private Date dateOldCoverage;

    @GenTime
    private String dateTimeString;

    @GenTime
    private Object dateTimeObject;

    public LocalTime getTime() {
        return time;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Date getDateOld() {
        return dateOld;
    }

    public String getDateTimeString() {
        return dateTimeString;
    }

    public Object getDateTimeObject() {
        return dateTimeObject;
    }
}
