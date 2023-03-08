package io.dummymaker.cases;

import io.dummymaker.export.Exporter;
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
public enum NamingCases implements NamingCase {

    DEFAULT(new DefaultCase()),
    LOWER_CASE(new LowerCase()),
    UPPER_CASE(new UpperCase()),
    SNAKE_LOWER_CASE(new SnakeLowerCase()),
    SNAKE_UPPER_CASE(new SnakeUpperCase()),
    KEBAB_LOWER_CASE(new KebabLowerCase()),
    KEBAB_UPPER_CASE(new KebabUpperCase()),
    CAMEL_CASE(new CamelCase()),
    CAMEL_WORD_CASE(new CamelWordCase()),
    PASCAL_CASE(new PascalCase()),
    PASCAL_WORD_CASE(new PascalWordCase());

    private final NamingCase namingCase;

    NamingCases(final NamingCase namingCase) {
        this.namingCase = namingCase;
    }

    @Override
    public @NotNull CharSequence apply(@NotNull CharSequence value) {
        return namingCase.apply(value);
    }
}
