package io.dummymaker.factory;

import java.util.List;

/**
 * Populates objects via generators included
 *
 * @author GoodforGod
 * @since 30.05.2017
 */
public interface IPopulateFactory {

    /**
     * Populates dummy object fields
     *
     * @see io.dummymaker.annotation.PrimeGenAnnotation
     *
     * @param t object to populate
     * @return Populated Object
     */
    <T> T populate(final T t);

    /**
     * Populates dummy objects fields
     *
     * @see io.dummymaker.annotation.PrimeGenAnnotation
     *
     * @param t list of object to populate
     * @return Populated list of objects
     */
    <T> List<T> populate(final List<T> t);
}
