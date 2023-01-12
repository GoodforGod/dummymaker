package io.dummymaker.bundle;

import io.dummymaker.util.CollectionUtils;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * Prime bundle implementation
 *
 * @author Anton Kurako (GoodforGod)
 * @see Bundle
 * @since 31.05.2017
 */
abstract class AbstractBundle implements Bundle {

    private final List<String> data;

    AbstractBundle(List<String> data) {
        this.data = data;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public @NotNull String get(int index) {
        return (index > -1 && index < data.size() - 1)
                ? data.get(index)
                : CollectionUtils.random(data);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public @NotNull String random() {
        return CollectionUtils.random(data);
    }

    @Override
    public int size() {
        return data.size();
    }
}
