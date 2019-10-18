package io.dummymaker.beta.model;

import io.dummymaker.annotation.complex.GenArray;
import io.dummymaker.annotation.complex.GenArray2D;

/**
 * ! NO DESCRIPTION !
 *
 * @author GoodforGod
 * @since 15.09.2019
 */
public class DummyArray {

    private byte[] byteSimple;
    private byte[][] byteDouble;

    @GenArray
    private short[] shortSimple;
    private short[][] shortDouble;

    private int[] intSimple;
    private int[][] intDouble;

    @GenArray
    private long[] longSimple;
    private long[][] longDouble;

    private float[] floatSimple;
    private float[][] floatDouble;

    private Byte[] ByteObjSimple;
    private Byte[][] ByteObjDouble;

    private Short[] ShortObjSimple;
    private Short[][] ShortObjDouble;

    private Integer[] IntegerObjSimple;
    @GenArray2D
    private Integer[][] IntegerObjDouble;

    private Long[] LongObjSimple;
    private Long[][] LongObjDouble;

    private Float[] FloatObjSimple;
    private Float[][] FloatObjDouble;

    private Double[] DoubleObjSimple;
    @GenArray2D
    private Double[][] DoubleObjDouble;

    private DummyEnum[] dummies;

    public DummyEnum[] getDummies() {
        return dummies;
    }

    public byte[] getByteSimple() {
        return byteSimple;
    }

    public byte[][] getByteDouble() {
        return byteDouble;
    }

    public short[] getShortSimple() {
        return shortSimple;
    }

    public short[][] getShortDouble() {
        return shortDouble;
    }

    public int[] getIntSimple() {
        return intSimple;
    }

    public int[][] getIntDouble() {
        return intDouble;
    }

    public long[] getLongSimple() {
        return longSimple;
    }

    public long[][] getLongDouble() {
        return longDouble;
    }

    public float[] getFloatSimple() {
        return floatSimple;
    }

    public float[][] getFloatDouble() {
        return floatDouble;
    }

    public Byte[] getByteObjSimple() {
        return ByteObjSimple;
    }

    public Byte[][] getByteObjDouble() {
        return ByteObjDouble;
    }

    public Short[] getShortObjSimple() {
        return ShortObjSimple;
    }

    public Short[][] getShortObjDouble() {
        return ShortObjDouble;
    }

    public Integer[] getIntegerObjSimple() {
        return IntegerObjSimple;
    }

    public Integer[][] getIntegerObjDouble() {
        return IntegerObjDouble;
    }

    public Long[] getLongObjSimple() {
        return LongObjSimple;
    }

    public Long[][] getLongObjDouble() {
        return LongObjDouble;
    }

    public Float[] getFloatObjSimple() {
        return FloatObjSimple;
    }

    public Float[][] getFloatObjDouble() {
        return FloatObjDouble;
    }

    public Double[] getDoubleObjSimple() {
        return DoubleObjSimple;
    }

    public Double[][] getDoubleObjDouble() {
        return DoubleObjDouble;
    }
}
