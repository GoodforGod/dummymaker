package io.dummymaker.export;

/**
 * Naming strategy for class and field names
 *
 * Used in all exporters
 *
 * @see io.dummymaker.export.IExporter
 *
 * @author GoodforGod
 * @since 21.02.2018
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
     * EXCLUDING FIRST LETTER, first letter to low case
     *
     * Example: ( DummyList - DUMMY_LIST ) */
    UNDERSCORED_UPPER_CASE,

    /** First letter is low case, next letters are as is */
    INITIAL_LOW_CASE;

    public String toStrategy(final String value) {
        switch (this) {
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

    private String toLowCase(final String value) {
        return value.toLowerCase();
    }

    private String toUpperCase(final String value) {
        return value.toUpperCase();
    }

    private String toUnderscoredLowCase(final String value) {
        final StringBuilder underscored = new StringBuilder();

        for(final char letter : value.toCharArray()) {
            if (Character.isUpperCase(letter) && underscored.length() != 0)
                underscored.append("_");

            underscored.append(Character.toLowerCase(letter));
        }

        return underscored.toString();
    }

    private String toUnderscoredUpperCase(final String value) {
        final StringBuilder underscored = new StringBuilder();

        for(final char letter : value.toCharArray()) {
            if (Character.isUpperCase(letter) && underscored.length() != 0)
                underscored.append("_");

            underscored.append(Character.toUpperCase(letter));
        }

        return underscored.toString();
    }

    private String toInitialLowCase(final String value) {
        return value.substring(0, 1).toLowerCase() + value.substring(1, value.length());
    }
}
