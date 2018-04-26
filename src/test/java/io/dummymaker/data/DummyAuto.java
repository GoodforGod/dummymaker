package io.dummymaker.data;

import io.dummymaker.annotation.special.GenAuto;

import java.util.List;
import java.util.Map;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 26.04.2018
 */
@GenAuto
public class DummyAuto {

    private int anInt;

    private Long aLong;

    private List list;

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
