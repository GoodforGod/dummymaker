package io.goodforgod.dummymaker;

import io.goodforgod.dummymaker.generator.Generator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Gen rules for specific field
 *
 * @author Anton Kurako (GoodforGod)
 * @see GenRule
 * @since 27.03.2023
 */
final class GenRuleFieldContext {

    private final Class<?> type;
    private final String name;
    private final Generator<?> generator;

    GenRuleFieldContext(GenRuleField ruleField, Map<Supplier<Generator<?>>, Generator<?>> generatorMap) {
        this.type = ruleField.getType();
        this.name = ruleField.getName();
        this.generator = generatorMap.computeIfAbsent(ruleField.getGeneratorSupplier(), Supplier::get);
    }

    boolean isTyped() {
        return type != null;
    }

    Class<?> getType() {
        return type;
    }

    String getName() {
        return name;
    }

    Generator<?> getGenerator() {
        return generator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof GenRuleFieldContext))
            return false;
        GenRuleFieldContext that = (GenRuleFieldContext) o;
        return Objects.equals(type, that.type) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name);
    }

    @Override
    public String toString() {
        if (type != null) {
            return "[type=" + type + ']';
        } else {
            return "[name=" + name + ']';
        }
    }
}
