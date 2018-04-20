package io.dummymaker.export.naming;

import io.dummymaker.export.naming.impl.*;

/**
 * Naming strategy for class and field names
 *
 * Used in all exporters
 *
 * @see io.dummymaker.export.IExporter
 *
 * @author GoodforGod
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

    private final ICase value;

    Cases(final ICase value) {
        this.value = value;
    }

    public ICase value() {
        return value;
    }
}
