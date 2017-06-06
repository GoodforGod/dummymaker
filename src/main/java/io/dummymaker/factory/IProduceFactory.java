package io.dummymaker.factory;

import java.util.List;

/**
 * Factory to produce Dummy Objects
 *
 * @author GoodforGod
 * @since 28.05.2017
 */
public interface IProduceFactory<T> {

    /**
     * Produce and populates one dummy object
     * @return Populated dummy object
     */
    T produce();

    /**
     *
     * @param amount amount of dummies to produce and populate
     * @return List of populated dummies
     */
    List<T> produce(int amount);
}
