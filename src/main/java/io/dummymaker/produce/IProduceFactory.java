package io.dummymaker.produce;

import java.util.List;

/**
 * Default Comment
 *
 * @author GoodforGod
 * @since 28.05.2017
 */
public interface IProduceFactory<T> {
    T produce();

    List<T> produce(int amount);
}
