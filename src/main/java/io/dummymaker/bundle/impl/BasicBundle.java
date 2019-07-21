package io.dummymaker.bundle.impl;

import io.dummymaker.bundle.IBundle;

import java.util.concurrent.ThreadLocalRandom;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * Prime bundle implementation
 *
 * @author GoodforGod
 * @see IBundle
 * @since 31.05.2017
 */
public abstract class BasicBundle<T> implements IBundle<T> {

    /**
     * Bundle value collection
     */
    private final T[] preset;

    @SuppressWarnings("unchecked")
    @SafeVarargs
    public BasicBundle(T... values) {
        this.preset = (values != null) ? values : (T[]) new Object[0];
    }

    @Override
    public T get(int index) {
        return (index > 0 && index < preset.length - 1)
                ? preset[index]
                : preset[ThreadLocalRandom.current().nextInt(0, preset.length - 1)];
    }

    @Override
    public T[] getAll() {
        return preset;
    }

    @Override
    public T getRandom() {
        return preset[current().nextInt(0, preset.length - 1)];
    }

    @Override
    public int size() {
        return preset.length;
    }
}
