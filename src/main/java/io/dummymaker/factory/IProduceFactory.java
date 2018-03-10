package io.dummymaker.factory;

import java.util.List;

/**
 * Factory to produce Dummy Objects
 *
 * @author GoodforGod
 * @since 28.05.2017
 */
public interface IProduceFactory {

    /**
     * Produce class instance and populate its fields
     *
     * @see IPopulateFactory
     *
     * @param tClass class to produce
     * @param <T> object type
     * @return Populated dummy object
     */
    <T> T produce(final Class<T> tClass);

    /**
     * Produce chosen amount of class instances and populate their fields
     *
     * @see IPopulateFactory
     *
     * @param amount amount of dummies to produce and populate
     * @param tClass class to produce
     * @param <T> object type
     * @return List of populated dummies
     */
    <T> List<T> produce(final Class<T> tClass, final int amount);
}
