package io.dummymaker.util;

import io.dummymaker.export.IExporter;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 02.09.2017
 */
public class NameStrategist implements INameStrategist {
    /**
     * Naming strategy for class and field names
     *
     * Used in all exporters
     *
     * @see IExporter
     */
    public enum NamingStrategy {
        /** Naming as is */
        DEFAULT,

        UPPER_CASE,
        LOW_CASE,

        /** Each upper letter separated with underscore symbol, and transform to low case
         *
         * EXCLUDE FIRST LETTER, first letter to low case
         *
         * Example: ( DummyList - dummy_list ) */
        UNDERSCORED_LOW_CASE,

        /** Each upper letter separated with underscore symbol, and transform to upper case
         *
         * EXCLUDE FIRST LETTER, first letter to low case
         *
         * Example: ( DummyList - DUMMY_LIST ) */
        UNDERSCORED_UPPER_CASE,

        /** First letter is low case, next letters are as is */
        INITIAL_LOW_CASE,
    }

    public String toNamingStrategy(final String value, final NamingStrategy strategy) {
        switch (strategy) {
            case LOW_CASE:
                return toLowCase(value);

            case UPPER_CASE:
                return toUpperCase(value);

            case INITIAL_LOW_CASE:
                return toInitialLowCase(value);

            case UNDERSCORED_UPPER_CASE:
                return toUnderscoredUpperCase(value);

            case UNDERSCORED_LOW_CASE:
                return toUnderscoredLowCase(value);

            case DEFAULT:
            default:
                    return value;
        }
    }

    public String toLowCase(final String value) {
        return value.toLowerCase();
    }

    public String toUpperCase(final String value) {
        return value.toUpperCase();
    }

    public String toUnderscoredLowCase(final String value) {
        return null;
    }

    public String toUnderscoredUpperCase(final String value) {
        return null;
    }

    public String toInitialLowCase(final String value) {
        return null;
    }
}
