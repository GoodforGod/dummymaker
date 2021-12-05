package io.dummymaker.model.deprecated;

import io.dummymaker.annotation.complex.GenList;
import io.dummymaker.annotation.complex.GenMap;
import io.dummymaker.annotation.simple.number.GenLong;
import io.dummymaker.annotation.special.GenAuto;
import java.util.List;
import java.util.Map;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 26.04.2018
 */
@GenAuto(depth = 2)
public class DummyAuto {

    private int anInt;

    @GenLong
    private Long aLong;

    @GenList
    private List list;

    @GenMap
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
