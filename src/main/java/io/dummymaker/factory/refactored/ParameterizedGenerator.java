package io.dummymaker.factory.refactored;

import io.dummymaker.generator.Generator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Complex Generator used by ComplexGen annotation to populate fields When annotation have
 * attributes or value generates for multiple field types
 *
 * @author Anton Kurako (GoodforGod)
 * @since 19.11.2022
 */
public interface ParameterizedGenerator<T> extends Generator<T> {

    @Nullable
    T get(@NotNull GenType fieldType, @NotNull TypeBuilder typeBuilder);
}
