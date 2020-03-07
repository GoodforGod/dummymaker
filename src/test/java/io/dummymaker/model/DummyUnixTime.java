package io.dummymaker.model;

import io.dummymaker.annotation.complex.GenTime;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 07.03.2018
 */
public class DummyUnixTime {

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

    @GenTime(exportAsUnixTime = true)
    private LocalTime time;

    @GenTime(exportAsUnixTime = true)
    private LocalDate date;

    @GenTime(exportAsUnixTime = true)
    private LocalDateTime dateTime;

    @GenTime(exportAsUnixTime = true)
    private Timestamp timestamp;

    @GenTime(exportAsUnixTime = true)
    private Date dateOld;

    @GenTime(exportAsUnixTime = true)
    private Date dateOldCoverage;

    @GenTime(exportAsUnixTime = true)
    private String dateTimeString;

    @GenTime(exportAsUnixTime = true)
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
