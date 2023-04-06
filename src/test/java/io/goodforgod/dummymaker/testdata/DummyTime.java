package io.goodforgod.dummymaker.testdata;

import static java.util.regex.Pattern.compile;

import io.goodforgod.dummymaker.annotation.parameterized.GenTime;
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
public class DummyTime {

    public static final String ISO_TIME_PATTERN = "[0-9]{2}:[0-9]{2}:[0-9]{2}(\\.[0-9]{0,3})?";
    public static final String ISO_DATE_PATTERN = "[1-9][0-9]{3}-[0-9]{2}-[0-9]{2}";
    public static final String ISO_DATE_TIME_PATTERN = ISO_DATE_PATTERN + "T" + ISO_TIME_PATTERN;

    public static final String ISO_OFFSET_TIME_PATTERN = "[0-9]{2}:[0-9]{2}:[0-9]{2}(\\.[0-9]{0,3})?(([+-][0-9]{2}:[0-9]{2})|Z)";
    public static final String ISO_OFFSET_DATE_TIME_PATTERN = ISO_DATE_PATTERN + "T" + ISO_OFFSET_TIME_PATTERN;

    public enum Patterns {

        LOCAL_TIME(compile(ISO_TIME_PATTERN), compile("\\d{1,2}:\\d{1,2}(:\\d{1,2})?(\\.\\d{1,10})?")),
        LOCAL_DATE(compile(ISO_DATE_PATTERN), compile("\\d{4}-\\d{1,2}-\\d{1,2}")),
        LOCAL_DATETIME(compile(ISO_DATE_TIME_PATTERN),
                compile("\\d{4}-\\d{1,2}-\\d{1,2}[A-Z]\\d{1,2}:\\d{1,2}(:\\d{1,2}(\\.\\d+)?)?")),
        OFFSET_TIME(compile(ISO_OFFSET_TIME_PATTERN), compile("\\d{1,2}:\\d{1,2}(:\\d{1,2})?(\\.\\d{1,10})?[+-]\\d\\d:\\d\\d")),
        OFFSET_DATETIME(compile(ISO_OFFSET_DATE_TIME_PATTERN),
                compile("\\d{4}-\\d{1,2}-\\d{1,2}[A-Z]\\d{1,2}:\\d{1,2}(:\\d{1,2}(\\.\\d+)?)?(([+-][0-9]{2}:[0-9]{2})|Z)")),
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
        OFFSET_TIME("offsetTime"),
        OFFSET_DATETIME("offsetDateTime"),
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

    @GenTime
    private OffsetTime offsetTime;

    @GenTime
    private OffsetDateTime offsetDateTime;

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
