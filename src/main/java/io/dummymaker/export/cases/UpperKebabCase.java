package io.dummymaker.export.cases;

import io.dummymaker.export.Case;
import org.jetbrains.annotations.NotNull;

/**
 * Example: ( DummyList - DUMMY-LIST )
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.04.2018
 */
public final class UpperKebabCase implements Case {

    @Override
    public @NotNull String apply(@NotNull String value) {
        final StringBuilder builder = new StringBuilder();

        for (final char letter : value.toCharArray()) {
            if (Character.isUpperCase(letter) && builder.length() != 0)
                builder.append("-");

            builder.append(Character.toUpperCase(letter));
        }

        return builder.toString();
    }
}
