package io.dummymaker.data;

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
public class DummyTime {

    public enum FieldNames {
        LOCAL_TIME("time"),
        LOCAL_DATE("date"),
        LOCAL_DATETIME("dateTime"),
        TIMESTAMP("timestamp"),
        DATE("dateOld"),
        LOCAL_DATETIME_STRING("dateTimeString"),
        LOCAL_DATETIME_OBJECT("dateTimeObject");


        private final String name;

        FieldNames(String name) {
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
