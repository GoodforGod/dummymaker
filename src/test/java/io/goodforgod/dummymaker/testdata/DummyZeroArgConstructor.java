package io.goodforgod.dummymaker.testdata;

import io.goodforgod.dummymaker.annotation.GenAuto;
import io.goodforgod.dummymaker.annotation.parameterized.GenList;
import io.goodforgod.dummymaker.annotation.parameterized.GenMap;
import io.goodforgod.dummymaker.annotation.parameterized.GenSet;
import io.goodforgod.dummymaker.generator.simple.EmbeddedGenerator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@GenAuto
public class DummyZeroArgConstructor {

    private Integer amount;
    private Dummy dummy;

    @GenMap(key = EmbeddedGenerator.class, value = EmbeddedGenerator.class)
    private Map<String, Object> map;

    @GenList(value = EmbeddedGenerator.class, fixed = 4)
    private List<String> objectsFix;

    @GenSet(fixed = 1, value = EmbeddedGenerator.class)
    private Set<DummyZeroArgConstructor> stringsFix;

    public Integer getAmount() {
        return amount;
    }

    public Dummy getDummy() {
        return dummy;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public List<String> getObjectsFix() {
        return objectsFix;
    }

    public Set<DummyZeroArgConstructor> getStringsFix() {
        return stringsFix;
    }
}
