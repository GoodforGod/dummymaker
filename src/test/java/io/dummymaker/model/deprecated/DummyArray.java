package io.dummymaker.model.deprecated;

import io.dummymaker.annotation.complex.GenArray2D;
import io.dummymaker.annotation.special.GenAuto;

import java.util.List;
import java.util.Map;

/**
 * ! NO DESCRIPTION !
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
@GenAuto
public class DummyArray {

    private Map mapa;
    private Float[] floatsff;
    private List<Float> floatList;

    @GenArray2D
    private byte[][] bytes2;
    @GenArray2D
    private short[][] shorts2;
    @GenArray2D
    private int[][] ints2;
    @GenArray2D
    private long[][] longs2;
    @GenArray2D
    private float[][] floats2;
    @GenArray2D
    private double[][] doubles2;

    private byte[] bytes;
    private short[] shorts;
    private int[] ints;
    private long[] longs;
    private float[] floats;
    private double[] doubles;

    private byte aByte;
    private short aShort;
    private int anInt;
    private long aLong;
    private float aFloat;
    private double aDouble;

    private Byte aByteObj;
    private Short aShortObj;
    private Integer anIntObj;
    private Long aLongObj;
    private Float aFloatObj;
    private Double aDoubleObj;

    private DummyArray dummyArray;

    public byte[][] getBytes2() {
        return bytes2;
    }

    public short[][] getShorts2() {
        return shorts2;
    }

    public int[][] getInts2() {
        return ints2;
    }

    public long[][] getLongs2() {
        return longs2;
    }

    public float[][] getFloats2() {
        return floats2;
    }

    public double[][] getDoubles2() {
        return doubles2;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public short[] getShorts() {
        return shorts;
    }

    public int[] getInts() {
        return ints;
    }

    public long[] getLongs() {
        return longs;
    }

    public float[] getFloats() {
        return floats;
    }

    public double[] getDoubles() {
        return doubles;
    }

    public byte getaByte() {
        return aByte;
    }

    public short getaShort() {
        return aShort;
    }

    public int getAnInt() {
        return anInt;
    }

    public long getaLong() {
        return aLong;
    }

    public float getaFloat() {
        return aFloat;
    }

    public double getaDouble() {
        return aDouble;
    }

    public Byte getaByteObj() {
        return aByteObj;
    }

    public Short getaShortObj() {
        return aShortObj;
    }

    public Integer getAnIntObj() {
        return anIntObj;
    }

    public Long getaLongObj() {
        return aLongObj;
    }

    public Float getaFloatObj() {
        return aFloatObj;
    }

    public Double getaDoubleObj() {
        return aDoubleObj;
    }

    public DummyArray getDummyArray() {
        return dummyArray;
    }
}
