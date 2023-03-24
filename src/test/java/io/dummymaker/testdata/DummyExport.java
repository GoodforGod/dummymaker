package io.dummymaker.testdata;

import io.dummymaker.annotation.GenAuto;
import io.dummymaker.annotation.parameterized.GenList;
import io.dummymaker.annotation.parameterized.GenMap;
import io.dummymaker.annotation.parameterized.GenSet;
import io.dummymaker.generator.simple.EmbeddedGenerator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author GoodforGod
 * @since 27.02.2018
 */
@GenAuto
public class DummyExport {

    private Integer amount;
    private Dummy dummy;

    @GenMap(key = EmbeddedGenerator.class, value = EmbeddedGenerator.class)
    private Map<String, Object> map;

    @GenList(value = EmbeddedGenerator.class, fixed = 4)
    private List<String> objectsFix;

    @GenSet(fixed = 1, value = EmbeddedGenerator.class)
    private Set<DummyExport> stringsFix;
}
