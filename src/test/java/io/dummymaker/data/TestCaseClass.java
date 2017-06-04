package io.dummymaker.data;

import io.dummymaker.annotation.GenDouble;
import io.dummymaker.annotation.GenLocalDateTime;
import io.dummymaker.annotation.GenString;

import java.time.LocalDateTime;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 31.05.2017
 */
public class TestCaseClass {

    @GenString
    private String str;

    @GenDouble
    private Double aDouble;

    @GenLocalDateTime
    private LocalDateTime dateTime;

    public TestCaseClass() { }

    public TestCaseClass(String str, Double aDouble, LocalDateTime dateTime) {
        this.str = str;
        this.aDouble = aDouble;
        this.dateTime = dateTime;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public Double getaDouble() {
        return aDouble;
    }

    public void setaDouble(Double aDouble) {
        this.aDouble = aDouble;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
