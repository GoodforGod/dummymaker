package io.dummymaker.testdata;

import io.dummymaker.annotation.complex.GenSequence;

/**
 * @author GoodforGod
 * @since 15.09.2019
 */
public class DummySimple {

    @GenSequence
    private int number;
    private String name;
    private String female;

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getFemale() {
        return female;
    }
}
