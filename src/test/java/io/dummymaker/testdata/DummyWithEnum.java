package io.dummymaker.testdata;

import io.dummymaker.annotation.complex.GenEnum;

/**
 * @author GoodforGod
 * @since 15.09.2019
 */
public class DummyWithEnum {

    public enum DummyType {
        SIMPLE,
        COMPLEX,
        UNKNOWN
    }

    @GenEnum
    private DummyType type;
    @GenEnum(exclude = "SIMPLE")
    private DummyType typeExcluded;
    private DummyType typeAuto;

    public DummyType getType() {
        return type;
    }

    public DummyType getTypeExcluded() {
        return typeExcluded;
    }

    public DummyType getTypeAuto() {
        return typeAuto;
    }
}
