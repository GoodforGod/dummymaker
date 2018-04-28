package io.dummymaker.export.naming.impl;

import io.dummymaker.export.naming.ICase;

/**
 * Each upper letter separated with underscore symbol, and transform to upper case
 *
 * EXCLUDING FIRST LETTER, first letter to low case
 *
 * Example: ( DummyList - DUMMY_LIST )
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class UpperSnakeCase implements ICase {

    @Override
    public String format(String value) {
        final StringBuilder builder = new StringBuilder();

        for(final char letter : value.toCharArray()) {
            if (Character.isUpperCase(letter) && builder.length() != 0)
                builder.append("_");

            builder.append(Character.toUpperCase(letter));
        }

        return builder.toString();
    }
}
