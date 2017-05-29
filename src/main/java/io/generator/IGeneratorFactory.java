package io.generator;

import java.util.List;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 28.05.2017
 */
public interface IGeneratorFactory<T> {
    T produce(T t);

    List<T> produce(T t, int amount);
}
