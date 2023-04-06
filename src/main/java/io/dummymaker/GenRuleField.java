package io.dummymaker;

import io.dummymaker.generator.Generator;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Gen rules for specific field
 *
 * @author Anton Kurako (GoodforGod)
 * @see GenRule
 * @since 21.11.2022
 */
final class GenRuleField {

    private final Class<?> fieldType;
    private final String fieldName;
    private final Supplier<Generator<?>> generatorSupplier;

    GenRuleField(Supplier<Generator<?>> generatorSupplier, String fieldName) {
        this.fieldName = fieldName;
        this.fieldType = null;
        this.generatorSupplier = generatorSupplier;
    }

    GenRuleField(Supplier<Generator<?>> generatorSupplier, Class<?> fieldType) {
        this.fieldName = null;
        this.fieldType = fieldType;
        this.generatorSupplier = generatorSupplier;
    }

    Class<?> getType() {
        return fieldType;
    }

    String getName() {
        return fieldName;
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
                Objects.equals(fieldName, that.fieldName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldType, fieldName);
    }

    @Override
    public String toString() {
        if (fieldType != null) {
            return "[type=" + fieldType + ']';
        } else {
            return "[name=" + fieldName + ']';
        }
    }
}
