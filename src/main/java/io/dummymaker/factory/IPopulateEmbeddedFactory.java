package io.dummymaker.factory;

import io.dummymaker.annotation.PrimeGen;

/**
 * Allow to set initial depth to generate from
 * May lead to recursion if not used properly (if you build extensions)
 *
 * @see IPopulateFactory
 *
 * @author GoodforGod
 * @since 05.11.2018
 */
public interface IPopulateEmbeddedFactory extends IPopulateFactory {

    /**
     * Populates dummy object fields
     *
     * @see PrimeGen
     *
     * @param t object to populate
     * @param fromDepth initial embedded depth
     * @param <T> object type
     * @return Populated Object
     */
    <T> T populate(T t, int fromDepth);
}
