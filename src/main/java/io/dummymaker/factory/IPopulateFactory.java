package io.dummymaker.factory;

import io.dummymaker.annotation.core.PrimeGen;

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
     * @param t   object to populate
     * @param <T> object type
     * @return Populated Object
     * @see PrimeGen
     */
    <T> T populate(final T t);

    /**
     * Populates dummy objects fields
     *
     * @param t   list of object to populate
     * @param <T> object type
     * @return Populated list of objects
     * @see PrimeGen
     */
    <T> List<T> populate(final List<T> t);
}
