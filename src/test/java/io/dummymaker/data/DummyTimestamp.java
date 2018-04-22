package io.dummymaker.data;

import io.dummymaker.annotation.simple.time.*;
import io.dummymaker.annotation.special.GenRenameExport;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 03.03.2018
 */
@GenRenameExport("TimeDummyClass")
public class DummyTimestamp {

    public enum FieldNames {
        LOCAL_TIME("time"),
        LOCAL_DATE("date"),
        LOCAL_DATETIME("dateTime"),
        TIMESTAMP("timestamp"),
        DATE("dateOld");

        private final String name;

        FieldNames(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    @GenLocalTime
    private LocalTime time;

    @GenLocalDate
    private LocalDate date;

    @GenLocalDateTime
    private LocalDateTime dateTime;

    @GenTimestamp
    private Timestamp timestamp;

    @GenDate
    private Date dateOld;

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
}
