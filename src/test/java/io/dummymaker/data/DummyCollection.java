package io.dummymaker.data;

import io.dummymaker.annotation.collection.GenList;
import io.dummymaker.annotation.collection.GenMap;
import io.dummymaker.annotation.collection.GenSet;
import io.dummymaker.generator.impl.BooleanGenerator;

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

    @GenList(generator = BooleanGenerator.class)
    private List<String> objects;

    @GenSet(generator = BooleanGenerator.class)
    private Set<String> strings;

    @GenMap
    private Map<String, Object> map;

    @GenList(generator = BooleanGenerator.class, fixed = 4)
    private List<String> objectsFix;

    @GenSet(fixed = 5)
    private Set<String> stringsFix;

    @GenMap(fixed = 3)
    private Map<String, Object> mapFix;

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
