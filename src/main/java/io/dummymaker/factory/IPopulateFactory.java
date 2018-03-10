package io.dummymaker.factory;

import io.dummymaker.annotation.PrimeGen;

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
     * @see PrimeGen
     *
     * @param t object to populate
     * @param <T> object type
     * @return Populated Object
     */
    <T> T populate(final T t);

    /**
     * Populates dummy objects fields
     *
     * @see PrimeGen
     *
     * @param t list of object to populate
     * @param <T> object type
     * @return Populated list of objects
     */
    <T> List<T> populate(final List<T> t);
}
