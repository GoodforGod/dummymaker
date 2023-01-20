package io.dummymaker.model;

import io.dummymaker.annotation.complex.GenUnixTime;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;

/**
 * @author GoodforGod
 * @since 07.03.2018
 */
public class DummyUnixTime {

    public enum Fields {

        SHORT_PRIM("shortPrim"),
        INT_PRIM("integerPrim"),
        LONG_PRIM("longPrim"),
        SHORT_BOX("shortBox"),
        INT_BOX("integerBox"),
        LONG_BOX("longBox"),
        BIG_INT("bigInteger"),
        BIT_DECIMAL("bigDecimal"),
        STRING("string"),
        OBJECT("object");

        private final String name;

        Fields(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    @GenUnixTime
    private short shortPrim;

    @GenUnixTime
    private int integerPrim;

    @GenUnixTime
    private long longPrim;

    @GenUnixTime
    private Short shortBox;

    @GenUnixTime
    private Integer integerBox;

    @GenUnixTime
    private Long longBox;

    @GenUnixTime
    private BigInteger bigInteger;

    @GenUnixTime
    private BigDecimal bigDecimal;

    @GenUnixTime
    private String string;

    @GenUnixTime
    private Object object;

    public short getShortPrim() {
        return shortPrim;
    }

    public int getIntegerPrim() {
        return integerPrim;
    }

    public long getLongPrim() {
        return longPrim;
    }

    public Short getShortBox() {
        return shortBox;
    }

    public Integer getIntegerBox() {
        return integerBox;
    }

    public Long getLongBox() {
        return longBox;
    }

    public BigInteger getBigInteger() {
        return bigInteger;
    }

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    public String getString() {
        return string;
    }

    public Object getObject() {
        return object;
    }
}
