package io.goodforgod.dummymaker.testdata;

import io.goodforgod.dummymaker.annotation.parameterized.GenEnum;

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
