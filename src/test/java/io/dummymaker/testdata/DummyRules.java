package io.dummymaker.testdata;

import io.dummymaker.annotation.GenAuto;
import io.dummymaker.annotation.parameterized.GenList;
import io.dummymaker.generator.simple.EmbeddedGenerator;
import java.util.List;

/**
 * @author GoodforGod
 * @since 15.09.2019
 */
@GenAuto
public class DummyRules {

    private Long number;

    private String code;
    private String name;

    @GenList(EmbeddedGenerator.class)
    private List<DummyRules> ignored;

    public Long getNumber() {
        return number;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public List<DummyRules> getIgnored() {
        return ignored;
    }
}
