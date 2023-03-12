package io.dummymaker.testdata;

import io.dummymaker.annotation.GenAuto;
import io.dummymaker.annotation.parameterized.GenList;
import io.dummymaker.annotation.parameterized.GenMap;
import io.dummymaker.annotation.parameterized.GenSet;
import io.dummymaker.generator.simple.BooleanGenerator;
import io.dummymaker.generator.simple.string.HexDataGenerator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author GoodforGod
 * @since 05.03.2018
 */
public class DummyCollection {

    @GenList(value = BooleanGenerator.class)
    private List<String> objects;

    @GenSet(value = BooleanGenerator.class)
    private Set<String> strings;

    private Set<String> stringsAuto;

    @GenMap
    private Map<String, Object> map;

    private Map<String, Object> mapAuto;

    @GenList(fixed = 4, value = BooleanGenerator.class)
    private List<String> objectsFix;

    @GenSet(fixed = 5, value = HexDataGenerator.class)
    private Set<String> stringsFix;

    @GenMap(fixed = 3, key = HexDataGenerator.class)
    private Map<String, Object> mapFix;

    @GenAuto
    private DummyCollection dummyCollection;

    public List<String> getObjects() {
        return objects;
    }

    public Set<String> getStrings() {
        return strings;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public List<String> getObjectsFix() {
        return objectsFix;
    }

    public Set<String> getStringsFix() {
        return stringsFix;
    }

    public Map<String, Object> getMapFix() {
        return mapFix;
    }
}
