package io.dummymaker.export.naming.impl;

import io.dummymaker.export.naming.IStrategy;

/**
 * Each upper letter separated with underscore symbol, and transform to low case
 *
 * EXCLUDE FIRST LETTER, first letter to low case
 *
 * Example: ( DummyList - dummy_list )
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class UnderscoredLowCaseStrategy implements IStrategy {

    @Override
    public String toStrategy(String value) {
        final StringBuilder builder = new StringBuilder();

        for(final char letter : value.toCharArray()) {
            if (Character.isUpperCase(letter) && builder.length() != 0)
                builder.append("_");

            builder.append(Character.toLowerCase(letter));
        }

        return builder.toString();
    }
}
