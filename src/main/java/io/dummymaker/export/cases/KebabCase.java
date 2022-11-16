package io.dummymaker.export.cases;

import io.dummymaker.export.Case;
import org.jetbrains.annotations.NotNull;

/**
 * Each upper letter separated with underscore symbol, and transform to low case EXCLUDE FIRST
 * LETTER, first letter to low case Example: ( DummyList - dummy-list )
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.04.2018
 */
public final class KebabCase implements Case {

    @Override
    public @NotNull String apply(@NotNull String value) {
        final StringBuilder builder = new StringBuilder();

        for (final char letter : value.toCharArray()) {
            if (Character.isUpperCase(letter) && builder.length() != 0)
                builder.append("-");

            builder.append(Character.toLowerCase(letter));
        }

        return builder.toString();
    }
}
