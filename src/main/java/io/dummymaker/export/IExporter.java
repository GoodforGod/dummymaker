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
    void export(T t);

    /**
     * Allow to export list of objects
     * @param tList objects to export
     */
    void export(List<T> tList);
}
