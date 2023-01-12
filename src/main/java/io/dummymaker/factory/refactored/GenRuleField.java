package io.dummymaker.factory.refactored;

import io.dummymaker.generator.Generator;
import io.dummymaker.model.GenRules;
import java.util.*;
import java.util.function.Supplier;

/**
 * Gen rules for specific field
 *
 * @author Anton Kurako (GoodforGod)
 * @see GenRules
 * @since 21.11.2022
 */
final class GenRuleField {

    private final Class<?> fieldType;
    private final Set<String> fieldNames;
    private final Supplier<Generator<?>> generatorSupplier;

    GenRuleField(Supplier<Generator<?>> generatorSupplier, String... fieldNames) {
        this.fieldNames = new HashSet<>(Arrays.asList(fieldNames));
        this.fieldType = null;
        this.generatorSupplier = generatorSupplier;
    }

    GenRuleField(Supplier<Generator<?>> generatorSupplier, Class<?> fieldType) {
        this.fieldNames = Collections.emptySet();
        this.fieldType = fieldType;
        this.generatorSupplier = generatorSupplier;
    }

    boolean isTyped() {
        return fieldType != null;
    }

    Class<?> getType() {
        return fieldType;
    }

    Set<String> getNames() {
        return fieldNames;
    }

    Supplier<Generator<?>> getGeneratorSupplier() {
        return generatorSupplier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof GenRuleField))
            return false;
        GenRuleField that = (GenRuleField) o;
        return Objects.equals(fieldType, that.fieldType) &&
                Objects.equals(fieldNames, that.fieldNames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldType, fieldNames);
    }
}
