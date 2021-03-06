package io.dummymaker.model;

import io.dummymaker.annotation.complex.GenEnum;

/**
 * ! NO DESCRIPTION !
 *
 * @author GoodforGod
 * @since 15.09.2019
 */
public class DummyEnum {

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
