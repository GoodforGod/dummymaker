package io.dummymaker.export.cases;

import io.dummymaker.export.ICase;
import org.jetbrains.annotations.NotNull;

/**
 * Each upper letter separated with underscore symbol, and transform to upper case EXCLUDING FIRST
 * LETTER, first letter to low case Example: ( DummyList - DUMMY_LIST )
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class UpperSnakeCase implements ICase {

    @Override
    public @NotNull String format(@NotNull String value) {
        final StringBuilder builder = new StringBuilder();

        for (final char letter : value.toCharArray()) {
            if (Character.isUpperCase(letter) && builder.length() != 0)
                builder.append("_");

            builder.append(Character.toUpperCase(letter));
        }

        return builder.toString();
    }
}
