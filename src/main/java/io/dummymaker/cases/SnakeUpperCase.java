package io.dummymaker.cases;

import org.jetbrains.annotations.NotNull;

/**
 * Each upper letter separated with underscore symbol, and transform to upper case EXCLUDING FIRST
 * LETTER, first letter to low case Example: ( DummyList - DUMMY_LIST )
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.02.2018
 */
public final class SnakeUpperCase implements Case {

    SnakeUpperCase() {}

    @Override
    public @NotNull String apply(@NotNull String value) {
        final StringBuilder builder = new StringBuilder();

        for (final char letter : value.toCharArray()) {
            if (Character.isUpperCase(letter) && builder.length() != 0) {
                builder.append("_");
            }

            builder.append(Character.toUpperCase(letter));
        }

        return builder.toString();
    }
}
