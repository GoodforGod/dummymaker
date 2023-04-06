package io.goodforgod.dummymaker.generator;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Complex Generator is used when have attributes or value generates for multiple field types
 *
 * @author Anton Kurako (GoodforGod)
 * @since 19.11.2022
 */
public interface ParameterizedGenerator<T> extends Generator<T> {

    /**
     * @param parameters applied to generator
     * @return generated value
     */
    @Nullable
    T get(@NotNull GenParameters parameters);
}
