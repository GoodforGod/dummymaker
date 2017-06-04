package io.model.dummymaker;

import io.model.dummymaker.annotation.GenDouble;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 31.05.2017
 */
public class TestCaseClass {

    @GenDouble
    private String name;

    @GenDouble
    private Double value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
