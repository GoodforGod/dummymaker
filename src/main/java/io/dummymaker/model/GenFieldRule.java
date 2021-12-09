package io.dummymaker.model;


import io.dummymaker.generator.IGenerator;
import java.util.*;


/**
 * Gen rules for specific field
 *
 * @author GoodforGod
 * @see GenRules
 * @since 01.08.2019
 */
public class GenFieldRule {

    private final Class<?> fieldType;
    private final Set<String> fieldNames;
    private final Class<? extends IGenerator> generator;
    private final IGenerator<?> generatorExample;

    GenFieldRule(Class<? extends IGenerator> generator, String... fieldNames) {
        this.fieldNames = new HashSet<>(Arrays.asList(fieldNames));
        this.fieldType = null;
        this.generator = generator;
        this.generatorExample = null;
    }

    GenFieldRule(Class<? extends IGenerator> generator, Class<?> fieldType) {
        this.fieldNames = Collections.emptySet();
        this.fieldType = fieldType;
        this.generator = generator;
        this.generatorExample = null;
    }

    GenFieldRule(IGenerator<?> generator, String... fieldNames) {
        this.fieldNames = new HashSet<>(Arrays.asList(fieldNames));
        this.fieldType = null;
        this.generator = null;
        this.generatorExample = generator;
    }

    GenFieldRule(IGenerator<?> generator, Class<?> fieldType) {
        this.fieldNames = Collections.emptySet();
        this.fieldType = fieldType;
        this.generator = null;
        this.generatorExample = generator;
    }

    public boolean haveExample() {
        return generatorExample != null;
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

    public IGenerator<?> getGeneratorExample() {
        return generatorExample;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof GenFieldRule))
            return false;
        GenFieldRule that = (GenFieldRule) o;
        return Objects.equals(fieldType, that.fieldType) &&
                Objects.equals(fieldNames, that.fieldNames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldType, fieldNames);
    }
}
