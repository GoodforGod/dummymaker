package io.dummymaker.cases;

import io.dummymaker.export.Exporter;

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
    LOWER_CASE(new LowerCase()),
    UPPER_CASE(new UpperCase()),
    SNAKE_LOWER_CASE(new SnakeLowerCase()),
    SNAKE_UPPER_CASE(new SnakeUpperCase()),
    KEBAB_LOWER_CASE(new KebabLowerCase()),
    KEBAB_UPPER_CASE(new KebabUpperCase()),
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
