package io.goodforgod.dummymaker.testdata;

import io.goodforgod.dummymaker.annotation.simple.number.GenSequence;

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
