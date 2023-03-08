package io.dummymaker.testdata;

import io.dummymaker.annotation.GenAuto;
import io.dummymaker.annotation.GenIgnore;
import io.dummymaker.annotation.complex.GenList;
import io.dummymaker.annotation.complex.GenMap;
import io.dummymaker.annotation.complex.GenSet;
import io.dummymaker.generator.simple.EmbeddedGenerator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author GoodforGod
 * @since 27.02.2018
 */
@GenAuto
public class DummyNoZeroConstructor {

    @GenIgnore
    private Integer amount;
    private Dummy dummy;

    @GenMap(key = EmbeddedGenerator.class, value = EmbeddedGenerator.class)
    private Map<String, Object> map;

    @GenList(value = EmbeddedGenerator.class, fixed = 4)
    private List<String> objectsFix;

    @GenSet(fixed = 1, value = EmbeddedGenerator.class)
    private Set<DummyNoZeroConstructor> stringsFix;

    public DummyNoZeroConstructor(Integer amount) {
        this.amount = amount;
    }
}
