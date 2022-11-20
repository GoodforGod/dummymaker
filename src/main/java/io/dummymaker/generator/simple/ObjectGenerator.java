package io.dummymaker.generator.simple;

import io.dummymaker.generator.Generator;
import io.dummymaker.util.CollectionUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Generates object as string like: object_1245155
 *
 * @author Anton Kurako (GoodforGod)
 * @since 26.04.2018
 */
public final class ObjectGenerator implements Generator<Object> {

    @Override
    public @NotNull Object get() {
        return "object_" + RandomUtils.random(Integer.MAX_VALUE);
    }
}
