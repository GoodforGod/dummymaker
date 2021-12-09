package io.dummymaker.bundle.impl;


import io.dummymaker.bundle.IBundle;
import io.dummymaker.util.CollectionUtils;
import org.jetbrains.annotations.NotNull;


/**
 * Prime bundle implementation
 *
 * @author GoodforGod
 * @see IBundle
 * @since 31.05.2017
 */
public abstract class BasicBundle implements IBundle {

    /**
     * Bundle data
     */
    private final String[] data;

    @SafeVarargs
    public BasicBundle(String... data) {
        if (CollectionUtils.isEmpty(data))
            throw new IllegalArgumentException("Bundle data can not be empty!");

        this.data = data;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public @NotNull String get(int index) {
        return (index > -1 && index < data.length - 1)
                ? data[index]
                : CollectionUtils.random(data);
    }

    @Override
    public @NotNull String[] all() {
        return data;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public @NotNull String random() {
        return CollectionUtils.random(data);
    }

    @Override
    public int size() {
        return data.length;
    }
}
