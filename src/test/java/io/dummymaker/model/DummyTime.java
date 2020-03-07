package io.dummymaker.model;

import io.dummymaker.annotation.complex.GenTime;
import io.dummymaker.annotation.export.GenExportName;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 07.03.2018
 */
@GenExportName("TimeDummyClass")
public class DummyTime {

    public static final String ISO_TIME_PATTERN = "[0-9]{2}:[0-9]{2}:[0-9]{2}(\\.[0-9]{0,3})?";
    public static final String ISO_DATE_PATTERN = "[1-9][0-9]{3}-[0-9]{2}-[0-9]{2}";
    public static final String ISO_DATE_TIME_PATTERN = ISO_DATE_PATTERN + "T" + ISO_TIME_PATTERN;

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

    @GenTime
    private LocalTime time;

    @GenTime(minUnix = 1000, maxUnix = 999)
    private LocalDate date;

    @GenTime(minUnix = -100)
    private LocalDateTime dateTime;

    @GenTime(minUnix = 1)
    private Timestamp timestamp;

    @GenTime(maxUnix = GenTime.MAX_UNIX + 1000)
    private Date dateOld;

    @GenTime(minUnix = -100, maxUnix = GenTime.MAX_UNIX + 1000)
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
