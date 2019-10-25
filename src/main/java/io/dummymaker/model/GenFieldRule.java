package io.dummymaker.model;

import io.dummymaker.generator.IGenerator;

import java.util.*;

/**
 * Gen rules for specific field
 * @see GenRules
 *
 * @author GoodforGod
 * @since 01.08.2019
 */
public class GenFieldRule {

    private final Class<?> fieldType;
    private final Set<String> fieldNames;
    private final Class<? extends IGenerator> generator;

    GenFieldRule(Class<? extends IGenerator> generator, String ... fieldNames) {
        this.fieldNames = new HashSet<>(Arrays.asList(fieldNames));
        this.fieldType = null;
        this.generator = generator;
    }

    GenFieldRule(Class<? extends IGenerator> generator, Class<?> fieldType) {
        this.fieldNames = Collections.emptySet();
        this.fieldType = fieldType;
        this.generator = generator;
    }

    boolean isTyped() {
        return fieldType != null;
    }

    public Class<?> getType() {
        return fieldType;
    }

    public Set<String> getNames() {
        return fieldNames;
    }

    public Class<? extends IGenerator> getGenerator() {
        return generator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GenFieldRule)) return false;
        GenFieldRule that = (GenFieldRule) o;
        return Objects.equals(fieldType, that.fieldType) &&
                Objects.equals(fieldNames, that.fieldNames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldType, fieldNames);
    }
}
