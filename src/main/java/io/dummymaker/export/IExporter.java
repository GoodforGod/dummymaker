package io.dummymaker.export;

import java.util.List;

/**
 * Allow to export objects in desired format
 *
 * @see Format
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public interface IExporter {

    /**
     * Allow to export single object
     * @param t object to export
     * @param <T> object type
     * @return indicates was export successful
     */
    <T> boolean export(final T t);

    /**
     * Allow to export list of objects
     * @param tList objects to export
     * @param <T> object type
     * @return indicates was export successful
     */
    <T> boolean export(final List<T> tList);

    /**
     * Allow to export single object as a single string value
     * @param t object to export
     * @param <T> object type
     * @return object string representation
     */
    <T> String exportAsString(final T t);

    /**
     * Allow to export list of objects as single a string value
     * @param t objects to export
     * @param <T> object type
     * @return objects string representation
     */
    <T> String exportAsString(final List<T> t);
}
