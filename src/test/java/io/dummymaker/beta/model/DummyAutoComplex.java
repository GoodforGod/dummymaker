package io.dummymaker.beta.model;

import io.dummymaker.annotation.complex.GenList;
import io.dummymaker.annotation.special.GenAuto;
import io.dummymaker.generator.simple.EmbeddedGenerator;
import io.dummymaker.generator.simple.string.BtcAddressGenerator;

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
public class DummyAutoComplex {

    private List<String> randomList;
    private List<String> names;

    private Set<Integer> numbers;
    private Set<DummyAutoComplex> dummies;
    private Map<String, String> nameMap;

    private LocalDateTime localDateTime;
    private LocalDate localDate;
    private LocalTime localTime;
    private Date date;
    private java.sql.Date dateSql;
    private Timestamp timestamp;

    @GenList(BtcAddressGenerator.class)
    private List<String> customList;

    @GenList(EmbeddedGenerator.class)
    private List<DummyAutoComplex> complexList;
    private List<DummyAutoComplex> complexAutoList;

    public List<String> getCustomList() {
        return customList;
    }

    public List<DummyAutoComplex> getComplexList() {
        return complexList;
    }

    public List<DummyAutoComplex> getComplexAutoList() {
        return complexAutoList;
    }

    public List<String> getRandomList() {
        return randomList;
    }

    public List<String> getNames() {
        return names;
    }

    public Set<Integer> getNumbers() {
        return numbers;
    }

    public Set<DummyAutoComplex> getDummies() {
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
