package io.dummymaker.beta.model;

import io.dummymaker.annotation.complex.GenArray2D;
import io.dummymaker.annotation.special.GenAuto;

/**
 * ! NO DESCRIPTION !
 *
 * @author GoodforGod
 * @since 15.09.2019
 */
@GenAuto
public class DummyArray {

    private byte[] byteSimple;
    private byte[][] byteDouble;

    @GenArray2D
    private short[] shortSimple;
    private short[][] shortDouble;

    private int[] intSimple;
    private int[][] intDouble;

    @GenArray2D
    private long[] longSimple;
    private long[][] longDouble;

    private float[] floatSimple;
    private float[][] floatDouble;

    private Byte[] ByteSimple;
    private Byte[][] ByteDouble;

    private Short[] ShortSimple;
    private Short[][] ShortDouble;

    @GenArray2D
    private Integer[] IntegerSimple;
    private Integer[][] IntegerDouble;

    private Long[] LongSimple;
    private Long[][] LongDouble;

    private Float[] FloatSimple;
    private Float[][] FloatDouble;

    @GenArray2D
    private Double[] DoubleSimple;
    private Double[][] DoubleDouble;

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

    public Integer[] getIntegerSimple() {
        return IntegerSimple;
    }

    public Integer[][] getIntegerDouble() {
        return IntegerDouble;
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

    public Double[] getDoubleSimple() {
        return DoubleSimple;
    }

    public Double[][] getDoubleDouble() {
        return DoubleDouble;
    }
}
