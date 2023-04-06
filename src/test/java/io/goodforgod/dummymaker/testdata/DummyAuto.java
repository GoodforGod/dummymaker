package io.goodforgod.dummymaker.testdata;

import io.goodforgod.dummymaker.annotation.GenAuto;
import io.goodforgod.dummymaker.annotation.GenDepth;
import io.goodforgod.dummymaker.annotation.export.GenExportIgnore;
import io.goodforgod.dummymaker.annotation.parameterized.GenList;
import io.goodforgod.dummymaker.annotation.parameterized.GenMap;
import io.goodforgod.dummymaker.annotation.simple.number.GenLong;
import java.util.List;
import java.util.Map;

/**
 * @author GoodforGod
 * @since 26.04.2018
 */
@GenDepth(2)
@GenAuto
public class DummyAuto {

    private int anInt;

    @GenLong
    private Long aLong;

    @GenList
    @GenExportIgnore
    private List list;

    @GenMap
    @GenExportIgnore
    private Map<Integer, String> map;

    private DummyAuto dummyAuto;

    public DummyAuto getDummyAuto() {
        return dummyAuto;
    }

    public int getAnInt() {
        return anInt;
    }

    public Long getaLong() {
        return aLong;
    }

    public List getList() {
        return list;
    }

    public Map<Integer, String> getMap() {
        return map;
    }
}
