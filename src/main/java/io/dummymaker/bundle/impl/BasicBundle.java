package io.dummymaker.bundle.impl;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.util.CollectionUtils;

/**
 * Prime bundle implementation
 *
 * @author GoodforGod
 * @see IBundle
 * @since 31.05.2017
 */
public abstract class BasicBundle<T> implements IBundle<T> {

    /**
     * Bundle data
     */
    private final T[] preset;

    @SuppressWarnings("unchecked")
    @SafeVarargs
    public BasicBundle(T... values) {
        this.preset = (values == null) ? (T[]) new Object[0] : values;
    }

    @Override
    public T get(int index) {
        return (index > -1 && index < preset.length - 1)
                ? preset[index]
                : CollectionUtils.random(preset);
    }

    @Override
    public T[] getAll() {
        return preset;
    }

    @Override
    public T getRandom() {
        return CollectionUtils.random(preset);
    }

    @Override
    public int size() {
        return preset.length;
    }
}
