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
     * @param tClass class to produce
     * @param <T>    object type
     * @return Populated dummy object
     * @see IPopulateFactory
     */
    <T> T produce(final Class<T> tClass);

    /**
     * Produce chosen amount of class instances and populate their fields
     *
     * @param amount amount of dummies to produce and populate
     * @param tClass class to produce
     * @param <T>    object type
     * @return List of populated dummies
     * @see IPopulateFactory
     */
    <T> List<T> produce(final Class<T> tClass, final int amount);
}
