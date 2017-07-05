package io.dummymaker.export;

import java.util.List;

/**
 * Allow to export objects in desired format
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public interface IExporter<T> {

    /**
     * Allow to export single object
     * @param t object to export
     */
    boolean export(final T t);

    /**
     * Allow to export list of objects
     * @param tList objects to export
     */
    boolean export(final List<T> tList);

    /**
     * Allow to export single object as a single string value
     * @param t object to export
     * @return object string representation
     */
    String exportAsString(final T t);

    /**
     * Allow to export list of objects as single a string value
     * @param t objects to export
     * @return objects string representation
     */
    String exportAsString(final List<T> t);
}
