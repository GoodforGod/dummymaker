package io.dummymaker.export.naming.impl;

import io.dummymaker.export.naming.ICase;

/**
 * Each upper letter separated with underscore symbol, and transform to low case
 *
 * EXCLUDE FIRST LETTER, first letter to low case
 *
 * Example: ( DummyList - dummy-list )
 *
 * @author GoodforGod
 * @since 21.04.2018
 */
public class KebabCase implements ICase {

    @Override
    public String format(String value) {
        final StringBuilder builder = new StringBuilder();

        for(final char letter : value.toCharArray()) {
            if (Character.isUpperCase(letter) && builder.length() != 0)
                builder.append("-");

            builder.append(Character.toLowerCase(letter));
        }

        return builder.toString();
    }
}
