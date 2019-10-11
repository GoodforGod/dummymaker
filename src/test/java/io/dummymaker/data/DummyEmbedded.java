package io.dummymaker.data;

import io.dummymaker.annotation.simple.number.GenDouble;
import io.dummymaker.annotation.simple.number.GenInt;
import io.dummymaker.annotation.simple.number.GenLong;
import io.dummymaker.annotation.special.GenEmbedded;

import java.util.List;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 23.04.2018
 */
public class DummyEmbedded {

    @GenInt
    private int anInt;

    @GenLong
    private long aLong;

    @GenDouble
    private double aDouble;

    @GenEmbedded(depth = 3)
    private DummyEmbedded embedded;

    @GenEmbedded
    private DummyEmbedded embeddedDefault;

    @GenEmbedded(depth = 100)
    private DummyEmbedded embeddedMax;

    private List<DummyEmbedded> embeddeds;

    public int getAnInt() {
        return anInt;
    }

    public long getaLong() {
        return aLong;
    }

    public double getaDouble() {
        return aDouble;
    }

    public DummyEmbedded getEmbedded() {
        return embedded;
    }

    public DummyEmbedded getEmbeddedDefault() {
        return embeddedDefault;
    }

    public DummyEmbedded getEmbeddedMax() {
        return embeddedMax;
    }

    public List<DummyEmbedded> getEmbeddeds() {
        return embeddeds;
    }
}
