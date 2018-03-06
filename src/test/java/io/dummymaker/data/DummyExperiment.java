package io.dummymaker.data;

import io.dummymaker.annotation.collection.GenList;
import io.dummymaker.annotation.collection.GenMap;
import io.dummymaker.annotation.collection.GenSet;
import io.dummymaker.annotation.time.GenTime;
import io.dummymaker.generator.impl.BooleanGenerator;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 05.03.2018
 */
public class DummyExperiment {

    @GenTime
    private Timestamp timestamp;

    @GenList(generator = BooleanGenerator.class)
    private List<String> objects;

    @GenSet(generator = BooleanGenerator.class)
    private Set<String> strings;

    @GenMap
    private Map<String, Object> map;

    public List<String> getObjects() {
        return objects;
    }
}
