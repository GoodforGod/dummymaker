package io.dummymaker.util;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 25.02.2018
 */
public class BasicStringUtils {

    public static boolean isEmpty(String value) {
        return (value == null || value.isEmpty());
    }

    public static boolean isNonEmpty(String value) {
        return !isEmpty(value);
    }

    public static boolean isBlank(String value) {
        return (value == null || value.trim().isEmpty());
    }

    public static boolean isNotBlank(String value) {
        return !isBlank(value);
    }
}
