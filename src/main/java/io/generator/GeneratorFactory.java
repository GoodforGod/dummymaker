package io.generator;

import java.util.List;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 26.05.2017
 */
public class GeneratorFactory<T> implements IGeneratorFactory<T> {

    @Override
    public T produce(T t) {
        return null;
    }

    @Override
    public List<T> produce(T t, int amount) {
        return null;
    }
}
