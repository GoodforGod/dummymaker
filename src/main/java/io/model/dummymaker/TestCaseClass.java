package io.model.dummymaker;

import io.model.dummymaker.annotation.GenDouble;
import io.model.dummymaker.annotation.GenString;

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
}
