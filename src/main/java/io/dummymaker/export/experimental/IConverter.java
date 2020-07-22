package io.dummymaker.export.experimental;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 22.7.2020
 */
public interface IConverter {

    <T> String convert(T t);

    default <T> String wrap(T t) {
        return "";
    }

    default <T> String prefix(T t) {
        return "";
    }

    default <T> String suffix(T t) {
        return "";
    }
}
