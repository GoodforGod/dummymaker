package io.dummymaker.export;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Allow to export objects in desired format
 *
 * @author GoodforGod
 * @see Format
 * @since 26.05.2017
 */
public interface IExporter {

    /**
     * Allow to export single object
     *
     * @param t   object to export
     * @param <T> object type
     * @return indicates was export successful
     */
    <T> boolean export(final T t);

    /**
     * Allow to export list of objects
     *
     * @param collection objects to export
     * @param <T>        object type
     * @return indicates was export successful
     */
    <T> boolean export(final Collection<T> collection);

    /**
     * Allow to export single object as a single string value
     *
     * @param t   object to export
     * @param <T> object type
     * @return object string representation
     */
    @NotNull
    <T> String convert(final T t);

    /**
     * Allow to export list of objects as single a string value
     *
     * @param <T>        object type
     * @param collection objects to export
     * @return objects string representation
     */
    @NotNull
    <T> String convert(final Collection<T> collection);

    @NotNull
    IExporter withAppend();
}
