package io.dummymaker.util;

/**
 * Contains basic string util methods
 *
 * @author GoodforGod
 * @since 25.02.2018
 */
public class BasicStringUtils {

    public static boolean isEmpty(String value) {
        return (value == null || value.isEmpty());
    }

    public static boolean isBlank(String value) {
        return (isEmpty(value) || value.trim().isEmpty());
    }

    public static boolean isNotBlank(String value) {
        return !isBlank(value);
    }
}
