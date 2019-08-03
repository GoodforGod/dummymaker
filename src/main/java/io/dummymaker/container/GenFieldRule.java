package io.dummymaker.container;

import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.util.StringUtils;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 01.08.2019
 */
public class GenFieldRule {

    private final Class<?> fieldType;
    private final String fieldName;
    private final Class<? extends IGenerator> generator;

    GenFieldRule(String fieldName, Class<? extends IGenerator> generator) {
        this.fieldName = fieldName;
        this.fieldType = null;
        this.generator = generator;
    }

    GenFieldRule(Class<?> fieldType, Class<? extends IGenerator> generator) {
        this.fieldName = null;
        this.fieldType = fieldType;
        this.generator = generator;
    }

    public boolean isGeneral() {
        return !isTyped() && !isNamed();
    }

    public boolean isTyped() {
        return fieldType != null;
    }

    public boolean isNamed() {
        return !StringUtils.isBlank(fieldName);
    }

    public Class<?> getType() {
        return fieldType;
    }

    public String getName() {
        return fieldName;
    }

    public Class<? extends IGenerator> getGenerator() {
        return generator;
    }
}