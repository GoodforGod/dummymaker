package io.dummymaker.data;

import io.dummymaker.annotation.complex.GenMap;
import io.dummymaker.annotation.simple.string.GenName;
import io.dummymaker.annotation.special.GenAuto;
import io.dummymaker.annotation.special.GenExportName;
import io.dummymaker.annotation.special.GenSequential;

import java.util.List;
import java.util.Map;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 28.04.2018
 */
@GenAuto
public class DummyAutoHalf {

    @GenSequential
    private int anInt;

    @GenName
    private String name;

    @GenExportName("superlist")
    private List list;

    @GenMap(fixed = 6)
    private Map<Integer, String> map;

    private DummyAuto dummyAuto;

    public DummyAuto getDummyAuto() {
        return dummyAuto;
    }

    public void setAnInt(int anInt) {
        this.anInt = anInt;
    }

    public int getAnInt() {
        return anInt;
    }

    public String getName() {
        return name;
    }

    public List getList() {
        return list;
    }

    public Map<Integer, String> getMap() {
        return map;
    }
}
