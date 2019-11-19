package io.dummymaker.model;

import io.dummymaker.annotation.simple.number.GenInt;
import io.dummymaker.annotation.simple.number.GenLong;
import io.dummymaker.annotation.simple.number.GenShort;

/**
 * ! NO DESCRIPTION !
 *
 * @author GoodforGod
 * @since 05.10.2019
 */
public class DummyCast {

    @GenShort
    private Long asByte;
    @GenShort
    private Long asShort;
    @GenInt
    private Long asInt;
    @GenLong
    private Long asLong;

    public Long getAsByte() {
        return asByte;
    }

    public Long getAsShort() {
        return asShort;
    }

    public Long getAsInt() {
        return asInt;
    }

    public Long getAsLong() {
        return asLong;
    }
}
