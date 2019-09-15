package io.dummymaker.beta.model;

import io.dummymaker.annotation.special.GenAuto;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ! NO DESCRIPTION !
 *
 * @author GoodforGod
 * @since 15.09.2019
 */
@GenAuto
public class DummyComplex {

    private List<String> randomList;
    private List<String> names;

    private Set<Integer> numbers;
    private Set<DummyComplex> dummies;
    private Map<String, String> nameMap;

    private LocalDateTime localDateTime;
    private LocalDate localDate;
    private LocalTime localTime;
    private Date date;
    private java.sql.Date dateSql;
    private Timestamp timestamp;

    public List<String> getRandomList() {
        return randomList;
    }

    public List<String> getNames() {
        return names;
    }

    public Set<Integer> getNumbers() {
        return numbers;
    }

    public Set<DummyComplex> getDummies() {
        return dummies;
    }

    public Map<String, String> getNameMap() {
        return nameMap;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public Date getDate() {
        return date;
    }

    public java.sql.Date getDateSql() {
        return dateSql;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
