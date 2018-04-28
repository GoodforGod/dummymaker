package io.dummymaker.bundle.impl;

import io.dummymaker.bundle.IBundle;

import java.util.Arrays;
import java.util.List;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * Prime bundle implementation
 *
 * @see IBundle
 *
 * @author GoodforGod
 * @since 31.05.2017
 */
public abstract class BasicBundle<T> implements IBundle<T> {

    /**
     * Bundle value collection
     */
    private final List<T> preset;

    public BasicBundle(List<T> preset) {
        this.preset = preset;
    }

    @SafeVarargs
    public BasicBundle(T ... values) {
        this.preset = Arrays.asList(values);
    }

    @Override
    public T get(int index) {
        return (index > 0 && index < preset.size() - 1)
                ? preset.get(index)
                : preset.get(0);
    }

    @Override
    public List<T> getAll() {
        return preset;
    }

    @Override
    public T getRandom() {
        return preset.get(current().nextInt(0, preset.size() - 1));
    }

    @Override
    public int size() {
        return preset.size();
    }
}
