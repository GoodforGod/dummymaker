package io.dummymaker.generator;

import io.dummymaker.factory.GenType;
import io.dummymaker.factory.GenTypeBuilder;
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

    /**
     * @param fieldType   field type extension
     * @param typeBuilder builder that can be used to build type generics
     * @return generated value
     */
    @Nullable
    T get(@NotNull GenType fieldType, @NotNull GenTypeBuilder typeBuilder);
}
