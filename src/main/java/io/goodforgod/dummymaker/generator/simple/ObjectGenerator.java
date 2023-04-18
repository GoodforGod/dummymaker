package io.goodforgod.dummymaker.generator.simple;

import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.util.RandomUtils;
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
