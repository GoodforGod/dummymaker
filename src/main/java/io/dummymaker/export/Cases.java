package io.dummymaker.export;

import io.dummymaker.export.cases.*;
import org.jetbrains.annotations.NotNull;

/**
 * Naming strategy for class and field names
 * <p>
 * Used in all exporters
 *
 * @author Anton Kurako (GoodforGod)
 * @see Exporter
 * @since 21.02.2018
 */
public enum Cases {

    DEFAULT(new DefaultCase()),
    LOW_CASE(new LowCase()),
    UPPER_CASE(new UpperCase()),
    SNAKE_CASE(new SnakeCase()),
    UPPER_SNAKE_CASE(new UpperSnakeCase()),
    KEBAB_CASE(new KebabCase()),
    UPPER_KEBAB_CASE(new UpperKebabCase()),
    CAMEL_CASE(new CamelCase()),
    PASCAL_CASE(new PascalCase());

    private final Case value;

    Cases(final Case value) {
        this.value = value;
    }

    public Case value() {
        return value;
    }
}
