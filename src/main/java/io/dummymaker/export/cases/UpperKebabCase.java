package io.dummymaker.export.cases;

import io.dummymaker.export.ICase;

/**
 * Example: ( DummyList - DUMMY-LIST )
 *
 * @author GoodforGod
 * @since 21.04.2018
 */
public class UpperKebabCase implements ICase {

    @Override
    public String format(String value) {
        final StringBuilder builder = new StringBuilder();

        for (final char letter : value.toCharArray()) {
            if (Character.isUpperCase(letter) && builder.length() != 0) {
                builder.append("-");
            }

            builder.append(Character.toUpperCase(letter));
        }

        return builder.toString();
    }
}
