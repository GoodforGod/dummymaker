package io.dummymaker.model.deprecated;

import io.dummymaker.annotation.complex.GenList;
import io.dummymaker.annotation.complex.GenMap;
import io.dummymaker.annotation.complex.GenSet;
import io.dummymaker.annotation.special.GenEmbedded;
import io.dummymaker.generator.simple.BooleanGenerator;
import io.dummymaker.generator.simple.string.HexDataGenerator;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 05.03.2018
 */
public class DummyCollection {

    @GenList(value = BooleanGenerator.class)
    private List<String> objects;

    @GenSet(value = BooleanGenerator.class)
    private Set<String> strings;

    @GenMap
    private Map<String, Object> map;

    @GenList(value = BooleanGenerator.class, fixed = 4)
    private List<String> objectsFix;

    @GenSet(fixed = 5, value = HexDataGenerator.class)
    private Set<String> stringsFix;

    @GenMap(fixed = 3, key = HexDataGenerator.class)
    private Map<String, Object> mapFix;

    @GenEmbedded
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
