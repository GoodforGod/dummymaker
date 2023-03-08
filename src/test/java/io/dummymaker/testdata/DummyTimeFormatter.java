package io.dummymaker.testdata;

import static java.util.regex.Pattern.compile;

import io.dummymaker.annotation.complex.GenTime;
import java.sql.Timestamp;
import java.time.*;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 07.03.2018
 */
public class DummyTimeFormatter {

    public static final String ISO_TIME_PATTERN = "[0-9]{2}:[0-9]{2}";
    public static final String ISO_DATE_PATTERN = "[1-9][0-9]{3}-[0-9]{2}";
    public static final String ISO_DATE_TIME_PATTERN = ISO_DATE_PATTERN + " " + ISO_TIME_PATTERN;

    public enum Patterns {

        LOCAL_TIME(compile(ISO_TIME_PATTERN), compile("\\d{1,2}:\\d{1,2}(:\\d{1,2})?(\\.\\d{1,10})?")),
        LOCAL_DATE(compile(ISO_DATE_PATTERN), compile("\\d{4}-\\d{1,2}-\\d{1,2}")),
        LOCAL_DATETIME(compile(ISO_DATE_TIME_PATTERN),
                compile("\\d{4}-\\d{1,2}-\\d{1,2}[A-Z]\\d{1,2}:\\d{1,2}(:\\d{1,2}(\\.\\d+)?)?")),
        DATE(compile(ISO_DATE_TIME_PATTERN),
                compile("[A-Za-z]{3} [A-Za-z]{3} \\d{2} \\d{1,2}:\\d{1,2}:\\d{1,2} [A-Za-z]{3,5} \\d{4}")),
        TIMESTAMP(compile(ISO_DATE_TIME_PATTERN), compile("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}(\\.\\d{1,10})?")),
        DATE_SQL(compile(ISO_DATE_TIME_PATTERN), compile("\\d{4}-\\d{1,2}-\\d{1,2}")),
        TIME(compile(ISO_TIME_PATTERN), compile("\\d{1,2}:\\d{1,2}:\\d{1,2}"));

        private final Pattern pattern;
        private final Pattern toStringPattern;

        Patterns(Pattern pattern, Pattern toStringPattern) {
            this.pattern = pattern;
            this.toStringPattern = toStringPattern;
        }

        public Pattern getPattern() {
            return pattern;
        }

        public Pattern getToStringPattern() {
            return toStringPattern;
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

    @GenTime(formatter = "HH:mm")
    private LocalTime time;

    @GenTime(formatter = "yyyy-MM")
    private LocalDate date;

    @GenTime(formatter = "yyyy-MM HH:mm")
    private LocalDateTime dateTime;

    @GenTime
    private OffsetTime offsetTime;

    @GenTime
    private OffsetDateTime offsetDateTime;

    @GenTime(formatter = "yyyy-MM HH:mm")
    private Timestamp timestamp;

    @GenTime(formatter = "yyyy-MM HH:mm")
    private Date dateOld;

    @GenTime(formatter = "yyyy-MM HH:mm")
    private Date dateOldCoverage;

    @GenTime(formatter = "yyyy-MM HH:mm")
    private String dateTimeString;

    @GenTime(formatter = "yyyy-MM HH:mm")
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

    public Date getDateOldCoverage() {
        return dateOldCoverage;
    }
}
