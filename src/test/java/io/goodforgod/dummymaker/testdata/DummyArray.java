package io.goodforgod.dummymaker.testdata;

import io.goodforgod.dummymaker.annotation.export.GenExportIgnore;
import io.goodforgod.dummymaker.annotation.parameterized.GenArray;
import io.goodforgod.dummymaker.annotation.parameterized.GenArray2D;

/**
 * @author GoodforGod
 * @since 15.09.2019
 */
public class DummyArray {

    @GenExportIgnore
    private byte[] byteSimple;
    @GenExportIgnore
    private byte[][] byteDouble;

    @GenArray(fixed = 15)
    private short[] shortSimple;
    @GenExportIgnore
    private short[][] shortDouble;

    @GenExportIgnore
    private int[] intSimple;
    @GenExportIgnore
    private int[][] intDouble;

    @GenArray(fixed = 15)
    private long[] longSimple;
    @GenExportIgnore
    private long[][] longDouble;

    @GenExportIgnore
    private float[] floatSimple;
    @GenExportIgnore
    private float[][] floatDouble;

    @GenExportIgnore
    private Byte[] ByteObjSimple;
    @GenExportIgnore
    private Byte[][] ByteObjDouble;

    @GenExportIgnore
    private Short[] ShortObjSimple;
    @GenExportIgnore
    private Short[][] ShortObjDouble;

    @GenExportIgnore
    private Integer[] IntegerObjSimple;
    @GenArray2D(fixedFirst = 15)
    private Integer[][] IntegerObjDouble;

    @GenExportIgnore
    private Long[] LongObjSimple;
    @GenExportIgnore
    private Long[][] LongObjDouble;

    @GenExportIgnore
    private Float[] FloatObjSimple;
    @GenExportIgnore
    private Float[][] FloatObjDouble;

    @GenExportIgnore
    private Double[] DoubleObjSimple;
    @GenArray2D(fixedFirst = 15)
    private Double[][] DoubleObjDouble;

    @GenExportIgnore
    private DummyWithEnum[] dummies;

    public DummyWithEnum[] getDummies() {
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
