package io.dummymaker.model;

import io.dummymaker.annotation.complex.GenList;
import io.dummymaker.annotation.special.GenAuto;
import io.dummymaker.generator.simple.EmbeddedGenerator;

import java.util.List;

/**
 * ! NO DESCRIPTION !
 *
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
